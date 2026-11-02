package ru.mtuci.evenmonkeycanrent.data.repository

import ru.mtuci.evenmonkeycanrent.data.local.AppPreferences
import ru.mtuci.evenmonkeycanrent.domain.repository.OnboardingRepository

class OnboardingRepositoryImpl(
    private val preferences: AppPreferences
) : OnboardingRepository {

    override fun isOnboardingCompleted(): Boolean = preferences.isOnboardingCompleted

    override fun setOnboardingCompleted() {
        preferences.isOnboardingCompleted = true
    }
}
