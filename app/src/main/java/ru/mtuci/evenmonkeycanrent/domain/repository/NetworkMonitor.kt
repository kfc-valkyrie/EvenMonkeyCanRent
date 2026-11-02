package ru.mtuci.evenmonkeycanrent.domain.repository

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    /** Есть ли доступ в интернет прямо сейчас. */
    fun isOnline(): Boolean

    /** Поток изменений доступности интернета. */
    fun observeOnline(): Flow<Boolean>
}
