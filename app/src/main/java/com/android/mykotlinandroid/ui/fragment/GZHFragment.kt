package com.android.mykotlinandroid.ui.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.adapter.GZHAdapter
import com.android.mykotlinandroid.base.BaseActivity
import com.android.mykotlinandroid.base.BaseMvpFragment
import com.android.mykotlinandroid.mvp.main.GZhPresenter
import com.android.mykotlinandroid.mvp.response.GZHResponse
import com.android.mykotlinandroid.ui.activity.GZHDetailsActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.base_fragment_layout.*
import kotlinx.android.synthetic.main.fragment_layout_gzh.*

/**
 * author : zf
 * date   : 2019/10/18
 * You are the best.
 */
class GZHFragment : BaseMvpFragment<GZhPresenter>() {

    lateinit var adapter : GZHAdapter
    lateinit var result: List<GZHResponse>
    override fun initPresenter(): GZhPresenter {
        return GZhPresenter(activity)
    }

    override fun openLoading(): Boolean {
        return true
    }

    override fun openRootLayout(): Boolean {
        return true
    }

    override fun initLoad() {
      presenter.gzhList()
    }

    override fun initData() {

      presenter.response.observe(this,Observer {
          wempty_view.hide()
          this.result = it
        adapter.setNewData(it)
      })

      adapter.run {
          onItemClickListener = BaseQuickAdapter.OnItemClickListener{_,_,positon->
              GZHDetailsActivity.actionStart(activity as BaseActivity,result[positon].name,result[positon].id)
        }
      }
    }

    override fun initView() {
        wempty_view.show(true)
        gzh_recycler.layoutManager = LinearLayoutManager(activity)
        var linearSnapHelper = PagerSnapHelper() //每次滑动一个滑动  类似于viewPager 多行滑动-- LinearSnapHelper
        linearSnapHelper.attachToRecyclerView(gzh_recycler)
         adapter = GZHAdapter(R.layout.item_fragment_gzh, arrayListOf())
        gzh_recycler.adapter = adapter
    }

    override fun getLayoutResId(): Int {
       return R.layout.fragment_layout_gzh
    }

    companion object{
        fun newInstance():GZHFragment{
            return GZHFragment()
        }
    }

}