package ru.mtuci.evenmonkeycanrent.presentation.common

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.mtuci.evenmonkeycanrent.presentation.noconnection.NoConnectionActivity

/**
 * Базовая Activity: следит за подключением к интернету и при его потере
 * открывает экран «Нет подключения к интернету».
 */
abstract class ConnectionAwareActivity : AppCompatActivity() {

    private val connectivityViewModel: ConnectivityViewModel by viewModels {
        ConnectivityViewModel.Factory
    }

    private var isNoConnectionShown = false

    private val noConnectionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            isNoConnectionShown = false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        isNoConnectionShown = savedInstanceState?.getBoolean(KEY_NO_CONNECTION_SHOWN) ?: false

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                connectivityViewModel.isOnline.collect { online ->
                    if (!online) openNoConnectionScreen()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_NO_CONNECTION_SHOWN, isNoConnectionShown)
    }

    private fun openNoConnectionScreen() {
        if (isNoConnectionShown) return
        isNoConnectionShown = true
        noConnectionLauncher.launch(NoConnectionActivity.newIntent(this))
    }

    private companion object {
        const val KEY_NO_CONNECTION_SHOWN = "no_connection_shown"
    }
}
