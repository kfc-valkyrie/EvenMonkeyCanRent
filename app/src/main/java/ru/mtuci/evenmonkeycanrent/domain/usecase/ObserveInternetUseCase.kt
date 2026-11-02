package ru.mtuci.evenmonkeycanrent.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mtuci.evenmonkeycanrent.domain.repository.NetworkMonitor

class ObserveInternetUseCase(private val networkMonitor: NetworkMonitor) {
    operator fun invoke(): Flow<Boolean> = networkMonitor.observeOnline()
}
