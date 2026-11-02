package ru.mtuci.evenmonkeycanrent.domain.repository

interface SessionRepository {
    /** Сохранён ли access токен и не истёк ли его срок действия. */
    fun hasValidAccessToken(): Boolean
}
