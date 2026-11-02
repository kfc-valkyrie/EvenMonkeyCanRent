package ru.mtuci.evenmonkeycanrent.presentation.splash

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.mtuci.evenmonkeycanrent.databinding.ActivitySplashBinding
import ru.mtuci.evenmonkeycanrent.presentation.common.applySystemBarsPadding
import ru.mtuci.evenmonkeycanrent.presentation.common.toIntent
import ru.mtuci.evenmonkeycanrent.presentation.noconnection.NoConnectionActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val viewModel: SplashViewModel by viewModels { SplashViewModel.Factory }

    private val noConnectionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) viewModel.onConnectionRestored() else finish()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        // SplashScreen инициализируется до super.onCreate и установки интерфейса
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { render(it) }
            }
        }
    }

    private fun render(state: SplashViewModel.UiState) {
        when (state) {
            SplashViewModel.UiState.Loading,
            SplashViewModel.UiState.Idle -> Unit

            SplashViewModel.UiState.NoConnection -> {
                viewModel.onEventHandled()
                noConnectionLauncher.launch(NoConnectionActivity.newIntent(this))
            }

            is SplashViewModel.UiState.Navigate -> {
                viewModel.onEventHandled()
                startActivity(state.destination.toIntent(this))
                finish()
            }
        }
    }
}
