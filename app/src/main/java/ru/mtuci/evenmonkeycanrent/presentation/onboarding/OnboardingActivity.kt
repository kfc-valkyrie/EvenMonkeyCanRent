package ru.mtuci.evenmonkeycanrent.presentation.onboarding

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.launch
import ru.mtuci.evenmonkeycanrent.R
import ru.mtuci.evenmonkeycanrent.databinding.ActivityOnboardingBinding
import ru.mtuci.evenmonkeycanrent.domain.model.StartDestination
import ru.mtuci.evenmonkeycanrent.presentation.common.ConnectionAwareActivity
import ru.mtuci.evenmonkeycanrent.presentation.common.applySystemBarsPadding
import ru.mtuci.evenmonkeycanrent.presentation.common.toIntent

class OnboardingActivity : ConnectionAwareActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    private val viewModel: OnboardingViewModel by viewModels { OnboardingViewModel.Factory }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            viewModel.onPageSelected(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        val pageCount = viewModel.state.value.pageCount
        binding.viewPager.adapter = OnboardingPagerAdapter(this, pageCount)
        binding.viewPager.registerOnPageChangeCallback(pageChangeCallback)
        setupIndicator(pageCount)

        binding.btnNext.setOnClickListener { viewModel.onNextClicked() }
        binding.btnSkip.setOnClickListener { viewModel.onSkipClicked() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.state.collect { render(it) } }
                launch {
                    viewModel.events.collect { event ->
                        when (event) {
                            OnboardingViewModel.Event.OpenAuth -> openAuth()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        binding.viewPager.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onDestroy()
    }

    private fun render(state: OnboardingViewModel.UiState) {
        if (binding.viewPager.currentItem != state.currentPage) {
            binding.viewPager.setCurrentItem(state.currentPage, true)
        }
        binding.btnNext.setText(
            if (state.isLastPage) R.string.onboarding_start else R.string.onboarding_next
        )
        updateIndicator(state.currentPage)
    }

    private fun setupIndicator(pageCount: Int) {
        val container = binding.indicatorContainer
        container.layoutTransition = LayoutTransition().apply {
            enableTransitionType(LayoutTransition.CHANGING)
        }
        val gap = resources.getDimensionPixelSize(R.dimen.indicator_gap)
        val height = resources.getDimensionPixelSize(R.dimen.indicator_height)
        repeat(pageCount) { index ->
            val dot = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(0, height).apply {
                    if (index > 0) marginStart = gap
                }
            }
            container.addView(dot)
        }
    }

    private fun updateIndicator(selected: Int) {
        val activeWidth = resources.getDimensionPixelSize(R.dimen.indicator_width_active)
        val inactiveWidth = resources.getDimensionPixelSize(R.dimen.indicator_width_inactive)
        binding.indicatorContainer.children.forEachIndexed { index, dot ->
            val isActive = index == selected
            dot.setBackgroundResource(
                if (isActive) R.drawable.bg_indicator_active else R.drawable.bg_indicator_inactive
            )
            dot.layoutParams = dot.layoutParams.apply {
                width = if (isActive) activeWidth else inactiveWidth
            }
        }
    }

    private fun openAuth() {
        startActivity(StartDestination.AUTH.toIntent(this))
        finish()
    }
}
