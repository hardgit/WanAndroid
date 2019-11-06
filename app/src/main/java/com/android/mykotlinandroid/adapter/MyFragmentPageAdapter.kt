package com.android.mykotlinandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * author : zf
 * date   : 2019/10/21
 * You are the best.
 */
class MyFragmentPageAdapter(fm: FragmentManager,var fragments:MutableList<Fragment>): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
       return fragments[position]
    }

    override fun getCount(): Int {
       return fragments.size
    }

}