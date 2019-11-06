package com.android.mykotlinandroid.ui.fragment


import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.base.BaseFragment

/**
 * author : zf
 * date   : 2019/10/18
 * You are the best.
 */
class ProjectFragment : BaseFragment() {
    override fun initLoad() {

    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun getLayoutResId(): Int {
       return R.layout.fragment_layout_project
    }

    companion object{
        fun newInstance():ProjectFragment{
            return ProjectFragment()
        }
    }
}