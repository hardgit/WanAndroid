package com.android.mykotlinandroid.ui.fragment

import android.os.Bundle
import android.view.View
import com.android.mykotlinandroid.MyApplication
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.adapter.FlowTagAdapter
import com.android.mykotlinandroid.base.BaseFragment
import com.android.mykotlinandroid.http.ACTION_FRAGMENT_KEY
import com.android.mykotlinandroid.mvp.response.Article
import com.android.mykotlinandroid.ui.activity.ArticleWebActivity
import com.android.mykotlinandroid.utils.tag.FlowTagLayout
import kotlinx.android.synthetic.main.fragment_layout_gps_type.*

/**
 * author : zf
 * date   : 2019/11/4
 * You are the best.
 */
class GPSTypeFragment : BaseFragment() {

    lateinit var articleList: ArrayList<Article>

    companion object {
        fun newInstance(articleList: ArrayList<Article>) =
            GPSTypeFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ACTION_FRAGMENT_KEY, articleList)
                }
            }

    }

    override fun initView() {
        articleList = arguments?.getParcelableArrayList<Article>(ACTION_FRAGMENT_KEY) as ArrayList<Article>

        val flowTagAdapter = FlowTagAdapter<Article>(mContext)
        flow_tag_lyout.adapter = flowTagAdapter
        flow_tag_lyout.setOnTagClickListener(object : FlowTagLayout.OnTagClickListener{
            override fun onItemClick(parent: FlowTagLayout?, view: View?, position: Int) {
               ArticleWebActivity.actionStart(mContext,articleList[position].title,articleList[position].link)
            }
        })
       flowTagAdapter.onlyAddAll(articleList)
    }

    override fun initData() {

    }

    override fun initLoad() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_layout_gps_type
    }


}