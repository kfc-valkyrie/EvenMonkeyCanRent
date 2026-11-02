package ru.mtuci.evenmonkeycanrent.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import ru.mtuci.evenmonkeycanrent.domain.usecase.ObserveInternetUseCase

class ConnectivityViewModel(
    observeInternet: ObserveInternetUseCase
) : ViewModel() {

    /**
     * Доступность интернета. Небольшая задержка отсекает кратковременные
     * «провалы» при переключении между Wi-Fi и мобильной сетью.
     */
    @OptIn(FlowPreview::class)
    val isOnline: Flow<Boolean> = observeInternet()
        .debounce(NETWORK_DEBOUNCE_MS)
        .catch { emit(true) }

    companion object {
        private const val NETWORK_DEBOUNCE_MS = 500L

        val Factory = appViewModelFactory { ConnectivityViewModel(it.observeInternetUseCase) }
    }
}
