package com.diordna.miru.home.calender

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CalenderPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        return CalenderDateFragment.getInstance(position)
    }
}