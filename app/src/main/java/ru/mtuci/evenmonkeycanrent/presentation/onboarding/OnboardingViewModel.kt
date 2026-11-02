package ru.mtuci.evenmonkeycanrent.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mtuci.evenmonkeycanrent.domain.usecase.CompleteOnboardingUseCase
import ru.mtuci.evenmonkeycanrent.presentation.common.appViewModelFactory

class OnboardingViewModel(
    private val completeOnboarding: CompleteOnboardingUseCase,
    pageCount: Int
) : ViewModel() {

    data class UiState(val currentPage: Int, val pageCount: Int) {
        val isLastPage: Boolean get() = currentPage == pageCount - 1
    }

    sealed interface Event {
        data object OpenAuth : Event
    }

    private val _state = MutableStateFlow(UiState(currentPage = 0, pageCount = pageCount))
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _events = Channel<Event>(Channel.BUFFERED)
    val events: Flow<Event> = _events.receiveAsFlow()

    private var isFinishing = false

    fun onPageSelected(position: Int) {
        _state.update { it.copy(currentPage = position) }
    }

    /** «Далее» листает к следующему слайду, на последнем слайде («Поехали») завершает онбординг. */
    fun onNextClicked() {
        val current = _state.value
        if (current.isLastPage) {
            finishOnboarding()
        } else {
            _state.update { it.copy(currentPage = it.currentPage + 1) }
        }
    }

    fun onSkipClicked() = finishOnboarding()

    private fun finishOnboarding() {
        if (isFinishing) return
        isFinishing = true
        viewModelScope.launch {
            // Даже если сохранить флаг не удалось, пользователь не должен застрять на онбординге
            runCatching { withContext(Dispatchers.IO) { completeOnboarding() } }
            _events.send(Event.OpenAuth)
        }
    }

    companion object {
        val Factory = appViewModelFactory {
            OnboardingViewModel(it.completeOnboardingUseCase, OnboardingPages.items.size)
        }
    }
}
