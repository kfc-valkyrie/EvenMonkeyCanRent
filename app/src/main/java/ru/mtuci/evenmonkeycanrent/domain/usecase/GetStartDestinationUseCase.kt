package ru.mtuci.evenmonkeycanrent.domain.usecase

import ru.mtuci.evenmonkeycanrent.domain.model.StartDestination
import ru.mtuci.evenmonkeycanrent.domain.repository.OnboardingRepository
import ru.mtuci.evenmonkeycanrent.domain.repository.SessionRepository

/**
 * Определяет экран после загрузки:
 * первый запуск — онбординг, нет действительного токена — вход, иначе — главный экран.
 */
class GetStartDestinationUseCase(
    private val onboardingRepository: OnboardingRepository,
    private val sessionRepository: SessionRepository
) {
    operator fun invoke(): StartDestination = when {
        !onboardingRepository.isOnboardingCompleted() -> StartDestination.ONBOARDING
        !sessionRepository.hasValidAccessToken() -> StartDestination.AUTH
        else -> StartDestination.HOME
    }
}
