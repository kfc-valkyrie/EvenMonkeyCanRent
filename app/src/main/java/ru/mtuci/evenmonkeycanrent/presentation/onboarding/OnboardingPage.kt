package ru.mtuci.evenmonkeycanrent.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.mtuci.evenmonkeycanrent.R

data class OnboardingPage(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
)

object OnboardingPages {
    val items = listOf(
        OnboardingPage(
            R.drawable.img_onboarding_1,
            R.string.onboarding_title_1,
            R.string.onboarding_description_1
        ),
        OnboardingPage(
            R.drawable.img_onboarding_2,
            R.string.onboarding_title_2,
            R.string.onboarding_description_2
        ),
        OnboardingPage(
            R.drawable.img_onboarding_3,
            R.string.onboarding_title_3,
            R.string.onboarding_description_3
        )
    )
}
