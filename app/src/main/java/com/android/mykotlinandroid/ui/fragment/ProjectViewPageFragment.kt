package com.android.mykotlinandroid.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.base.BaseMvpFragment
import com.android.mykotlinandroid.http.ACTION_DATA_ID_KEY
import com.android.mykotlinandroid.mvp.main.HomePresenter

/**
 * author : zf
 * date   : 2019/11/6
 * You are the best.
 *  项目 ----  viewpage中的fragment
 */
class ProjectViewPageFragment : BaseMvpFragment<HomePresenter>() {
    override fun initPresenter(): HomePresenter {
        return HomePresenter(activity)
    }

    var cid = 0
    var pageNum = 0

    companion object {
        fun getNewInstance(cid: Int): Fragment {
            return ProjectViewPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ACTION_DATA_ID_KEY, cid)
                }
            }
        }
    }

    override fun initView() {
        cid = arguments?.getInt(ACTION_DATA_ID_KEY)!!
    }

    override fun initData() {

    }

    override fun initLoad() {
        presenter.detailsCategory(activity, pageNum, cid)
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_project_viewpage_layout
    }
}