package ru.mtuci.evenmonkeycanrent.presentation.noconnection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mtuci.evenmonkeycanrent.domain.usecase.CheckInternetUseCase
import ru.mtuci.evenmonkeycanrent.presentation.common.appViewModelFactory

class NoConnectionViewModel(
    private val checkInternet: CheckInternetUseCase
) : ViewModel() {

    sealed interface UiState {
        data object Idle : UiState
        data object Checking : UiState
        data object StillOffline : UiState
        data object Connected : UiState
    }

    private val _state = MutableStateFlow<UiState>(UiState.Idle)
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onRetryClicked() {
        if (_state.value == UiState.Checking) return
        _state.value = UiState.Checking
        viewModelScope.launch {
            val online = runCatching {
                withContext(Dispatchers.IO) {
                    // Короткая пауза, чтобы пользователь увидел индикацию проверки
                    delay(MIN_CHECK_DURATION_MS)
                    checkInternet()
                }
            }.getOrDefault(false)
            _state.value = if (online) UiState.Connected else UiState.StillOffline
        }
    }

    fun onMessageShown() {
        _state.value = UiState.Idle
    }

    companion object {
        private const val MIN_CHECK_DURATION_MS = 600L

        val Factory = appViewModelFactory { NoConnectionViewModel(it.checkInternetUseCase) }
    }
}
