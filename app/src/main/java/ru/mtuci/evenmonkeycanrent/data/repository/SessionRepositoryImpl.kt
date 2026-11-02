package ru.mtuci.evenmonkeycanrent.data.repository

import ru.mtuci.evenmonkeycanrent.data.local.AppPreferences
import ru.mtuci.evenmonkeycanrent.domain.repository.SessionRepository

class SessionRepositoryImpl(
    private val preferences: AppPreferences
) : SessionRepository {

    override fun hasValidAccessToken(): Boolean {
        val token = preferences.accessToken
        return !token.isNullOrBlank() && preferences.accessTokenExpiresAt > System.currentTimeMillis()
    }
}
