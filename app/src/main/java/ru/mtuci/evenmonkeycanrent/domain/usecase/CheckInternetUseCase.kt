package ru.mtuci.evenmonkeycanrent.domain.usecase

import ru.mtuci.evenmonkeycanrent.domain.repository.NetworkMonitor

class CheckInternetUseCase(private val networkMonitor: NetworkMonitor) {
    operator fun invoke(): Boolean = networkMonitor.isOnline()
}
