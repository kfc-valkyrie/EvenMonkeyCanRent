package ru.mtuci.evenmonkeycanrent.domain.repository

interface OnboardingRepository {
    fun isOnboardingCompleted(): Boolean
    fun setOnboardingCompleted()
}
