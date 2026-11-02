package ru.mtuci.evenmonkeycanrent.presentation.common

import android.content.Context
import android.content.Intent
import ru.mtuci.evenmonkeycanrent.domain.model.StartDestination
import ru.mtuci.evenmonkeycanrent.presentation.auth.AuthActivity
import ru.mtuci.evenmonkeycanrent.presentation.home.HomeActivity
import ru.mtuci.evenmonkeycanrent.presentation.onboarding.OnboardingActivity

fun StartDestination.toIntent(context: Context): Intent {
    val target = when (this) {
        StartDestination.ONBOARDING -> OnboardingActivity::class.java
        StartDestination.AUTH -> AuthActivity::class.java
        StartDestination.HOME -> HomeActivity::class.java
    }
    return Intent(context, target)
}
