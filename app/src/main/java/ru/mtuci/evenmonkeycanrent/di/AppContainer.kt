package ru.mtuci.evenmonkeycanrent.di

import android.content.Context
import ru.mtuci.evenmonkeycanrent.data.local.AppPreferences
import ru.mtuci.evenmonkeycanrent.data.network.ConnectivityNetworkMonitor
import ru.mtuci.evenmonkeycanrent.data.repository.OnboardingRepositoryImpl
import ru.mtuci.evenmonkeycanrent.data.repository.SessionRepositoryImpl
import ru.mtuci.evenmonkeycanrent.domain.repository.NetworkMonitor
import ru.mtuci.evenmonkeycanrent.domain.repository.OnboardingRepository
import ru.mtuci.evenmonkeycanrent.domain.repository.SessionRepository
import ru.mtuci.evenmonkeycanrent.domain.usecase.CheckInternetUseCase
import ru.mtuci.evenmonkeycanrent.domain.usecase.CompleteOnboardingUseCase
import ru.mtuci.evenmonkeycanrent.domain.usecase.GetStartDestinationUseCase
import ru.mtuci.evenmonkeycanrent.domain.usecase.ObserveInternetUseCase

/**
 * Ручное внедрение зависимостей: создаёт объекты слоя данных
 * и use case'ы, которые получают вьюмодели.
 */
class AppContainer(context: Context) {

    private val appContext = context.applicationContext

    private val preferences by lazy { AppPreferences(appContext) }

    private val networkMonitor: NetworkMonitor by lazy { ConnectivityNetworkMonitor(appContext) }
    private val onboardingRepository: OnboardingRepository by lazy { OnboardingRepositoryImpl(preferences) }
    private val sessionRepository: SessionRepository by lazy { SessionRepositoryImpl(preferences) }

    val checkInternetUseCase by lazy { CheckInternetUseCase(networkMonitor) }
    val observeInternetUseCase by lazy { ObserveInternetUseCase(networkMonitor) }
    val completeOnboardingUseCase by lazy { CompleteOnboardingUseCase(onboardingRepository) }
    val getStartDestinationUseCase by lazy {
        GetStartDestinationUseCase(onboardingRepository, sessionRepository)
    }
}
