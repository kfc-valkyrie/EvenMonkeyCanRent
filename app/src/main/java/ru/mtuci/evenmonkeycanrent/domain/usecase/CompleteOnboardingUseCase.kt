package ru.mtuci.evenmonkeycanrent.domain.usecase

import ru.mtuci.evenmonkeycanrent.domain.repository.OnboardingRepository

/** Запоминает, что пользователь прошёл онбординг, чтобы не показывать его повторно. */
class CompleteOnboardingUseCase(private val repository: OnboardingRepository) {
    operator fun invoke() = repository.setOnboardingCompleted()
}
