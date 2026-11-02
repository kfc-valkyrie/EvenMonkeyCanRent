package ru.mtuci.evenmonkeycanrent.presentation.noconnection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.mtuci.evenmonkeycanrent.R
import ru.mtuci.evenmonkeycanrent.databinding.ActivityNoConnectionBinding
import ru.mtuci.evenmonkeycanrent.presentation.common.applySystemBarsPadding

class NoConnectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoConnectionBinding

    private val viewModel: NoConnectionViewModel by viewModels { NoConnectionViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityNoConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        binding.btnRetry.setOnClickListener { viewModel.onRetryClicked() }

        // Без интернета продолжить работу нельзя: «Назад» сворачивает приложение
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveTaskToBack(true)
            }
        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { render(it) }
            }
        }
    }

    private fun render(state: NoConnectionViewModel.UiState) {
        val isChecking = state == NoConnectionViewModel.UiState.Checking
        binding.progress.visibility = if (isChecking) View.VISIBLE else View.INVISIBLE
        binding.btnRetry.isEnabled = !isChecking

        when (state) {
            NoConnectionViewModel.UiState.StillOffline -> {
                Snackbar.make(binding.root, R.string.no_connection_still_offline, Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.btnRetry)
                    .show()
                viewModel.onMessageShown()
            }

            NoConnectionViewModel.UiState.Connected -> {
                setResult(RESULT_OK)
                finish()
            }

            else -> Unit
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, NoConnectionActivity::class.java)
    }
}
