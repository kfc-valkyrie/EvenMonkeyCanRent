package ru.mtuci.evenmonkeycanrent.data.local

import android.content.Context
import androidx.core.content.edit

/** Локальное хранилище простых настроек приложения на основе SharedPreferences. */
class AppPreferences(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var isOnboardingCompleted: Boolean
        get() = prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
        set(value) = prefs.edit { putBoolean(KEY_ONBOARDING_COMPLETED, value) }

    var accessToken: String?
        get() = prefs.getString(KEY_ACCESS_TOKEN, null)
        set(value) = prefs.edit { putString(KEY_ACCESS_TOKEN, value) }

    /** Момент истечения access токена в миллисекундах (Unix time). */
    var accessTokenExpiresAt: Long
        get() = prefs.getLong(KEY_ACCESS_TOKEN_EXPIRES_AT, 0L)
        set(value) = prefs.edit { putLong(KEY_ACCESS_TOKEN_EXPIRES_AT, value) }

    private companion object {
        const val PREFS_NAME = "evenmonkeycanrent_prefs"
        const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_ACCESS_TOKEN_EXPIRES_AT = "access_token_expires_at"
    }
}
