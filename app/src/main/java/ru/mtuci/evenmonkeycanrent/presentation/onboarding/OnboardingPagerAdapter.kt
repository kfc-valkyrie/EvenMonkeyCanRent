package ru.mtuci.evenmonkeycanrent.presentation.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/** Связывает ViewPager2 со страницами онбординга. */
class OnboardingPagerAdapter(
    activity: FragmentActivity,
    private val pageCount: Int
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = pageCount

    override fun createFragment(position: Int): Fragment =
        OnboardingPageFragment.newInstance(position)
}
