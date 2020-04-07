package com.p4r4d0x.edmgenreclassifier.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.p4r4d0x.edmgenreclassifier.fragments.StarterAboutFragment
import com.p4r4d0x.edmgenreclassifier.fragments.StarterInfoFragment
import com.p4r4d0x.edmgenreclassifier.fragments.StarterLoginFragment

/**
 * A page adapter that make appear the login fragments
 */
class StarterScreenSlidePagerAdapter constructor(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                StarterLoginFragment()
            }
            1 -> {
                StarterInfoFragment()
            }
            2 -> {
                StarterAboutFragment()

            }
            else -> {
                StarterLoginFragment()

            }
        }
    }

    override fun getItemCount() = 3


}