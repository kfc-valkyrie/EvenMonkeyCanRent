package ru.mtuci.evenmonkeycanrent.presentation.splash

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mtuci.evenmonkeycanrent.domain.model.StartDestination
import ru.mtuci.evenmonkeycanrent.domain.usecase.CheckInternetUseCase
import ru.mtuci.evenmonkeycanrent.domain.usecase.GetStartDestinationUseCase
import ru.mtuci.evenmonkeycanrent.presentation.common.appViewModelFactory

class SplashViewModel(
    private val checkInternet: CheckInternetUseCase,
    private val getStartDestination: GetStartDestinationUseCase
) : ViewModel() {

    sealed interface UiState {
        /** Идёт инициализация, показывается экран загрузки. */
        data object Loading : UiState

        /** Событие уже обработано экраном. */
        data object Idle : UiState

        data object NoConnection : UiState

        data class Navigate(val destination: StartDestination) : UiState
    }

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        initialize(SPLASH_DURATION_MS)
    }

    /** Вызывается, когда пользователь восстановил подключение на экране «Нет подключения». */
    fun onConnectionRestored() = initialize(minDisplayMillis = 0L)

    fun onEventHandled() {
        _state.value = UiState.Idle
    }

    private fun initialize(minDisplayMillis: Long) {
        _state.value = UiState.Loading
        viewModelScope.launch {
            val startedAt = SystemClock.elapsedRealtime()

            val result = runCatching {
                withContext(Dispatchers.IO) {
                    if (checkInternet()) getStartDestination() else null
                }
            }

            // Экран загрузки показывается не меньше заданного времени
            val remaining = minDisplayMillis - (SystemClock.elapsedRealtime() - startedAt)
            if (remaining > 0) delay(remaining)

            _state.value = result.fold(
                onSuccess = { destination ->
                    destination?.let { UiState.Navigate(it) } ?: UiState.NoConnection
                },
                // Не удалось прочитать локальные данные — безопасно отправляем на вход
                onFailure = { UiState.Navigate(StartDestination.AUTH) }
            )
        }
    }

    companion object {
        private const val SPLASH_DURATION_MS = 2_000L

        val Factory = appViewModelFactory {
            SplashViewModel(it.checkInternetUseCase, it.getStartDestinationUseCase)
        }
    }
}
