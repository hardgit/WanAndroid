package com.android.mykotlinandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

/**
 * author : zf
 * date   : 2019/11/6
 * You are the best.
 */
class ProjectFragmentAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    lateinit var fragments:List<Fragment>
    lateinit var titles:List<String>

    fun addData(dataList:List<Fragment>,titles:List<String>){
      this.fragments = dataList
      this.titles = titles
    }


    override fun getItem(position: Int): Fragment {
       return fragments.get(position)
    }

    override fun getCount(): Int {
      return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles.get(position)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}