package com.android.mykotlinandroid.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.adapter.HomeListAdapter
import com.android.mykotlinandroid.adapter.ProjectViewpageFragmentItemAdapter
import com.android.mykotlinandroid.base.BaseActivity
import com.android.mykotlinandroid.base.BaseMvpFragment
import com.android.mykotlinandroid.http.ACTION_DATA_ID_KEY
import com.android.mykotlinandroid.mvp.main.HomePresenter
import com.android.mykotlinandroid.mvp.response.DataX
import com.android.mykotlinandroid.ui.activity.ArticleWebActivity
import com.android.mykotlinandroid.utils.itemdecoration.ItemDecoration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.base_fragment_layout.*
import kotlinx.android.synthetic.main.fragment_layout_home.*
import kotlinx.android.synthetic.main.fragment_project_viewpage_layout.*

/**
 * author : zf
 * date   : 2019/11/6
 * You are the best.
 *  项目 ----  viewpage中的fragment
 */
class ProjectViewPageFragment : BaseMvpFragment<HomePresenter>() {

    private var viewpageFragmentItemAdapter: ProjectViewpageFragmentItemAdapter? = null
    private var itemDataX = arrayListOf<DataX>()

    override fun initPresenter(): HomePresenter {
        return HomePresenter(activity)
    }

    var cid = 0
    var pageNum = 1

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
        wempty_view.show(true)
        cid = arguments?.getInt(ACTION_DATA_ID_KEY)!!
        frag_pro_recycler.layoutManager = LinearLayoutManager(activity)
        frag_pro_recycler.addItemDecoration(ItemDecoration(mContext))
        smart_refresh_layout_frag_pro.setEnableRefresh(false)//禁止刷新
    }

    override fun initData() {

        presenter.detailsCategoryResponse.observe(this, Observer {
            wempty_view.hide()
            if (viewpageFragmentItemAdapter == null) {
                itemDataX.clear()
                itemDataX.addAll(it.datas)//单独创建一个数据集避免点击事件index溢出
                viewpageFragmentItemAdapter =
                    ProjectViewpageFragmentItemAdapter(R.layout.item_viewpage_fragment_recycler, it.datas)
                viewpageFragmentItemAdapter?.openLoadAnimation()
                frag_pro_recycler.adapter = viewpageFragmentItemAdapter
            } else {
                itemDataX.addAll(it.datas) //单独创建一个数据集避免点击事件index溢出
                viewpageFragmentItemAdapter?.addData(it.datas)
                viewpageFragmentItemAdapter?.notifyDataSetChanged()
            }

            pageNum++

            viewpageFragmentItemAdapter?.run {
                /* item整体点击事件 */
                onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
                    activity?.let { it1 ->
                        ArticleWebActivity.actionStart(it1, itemDataX[position].title, itemDataX[position].link)
                    }
                }
                /* 收藏文章点击事件 */
                onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                    if (view.id == R.id.love_img) {
                        if (!itemDataX[position].collect) {
                            presenter.addCollect(position, itemDataX[position].id)
                        } else {
                            presenter.cancelCollect(position, itemDataX[position].id)
                        }

                    }
                }
            }

        })
        smart_refresh_layout_frag_pro.setOnLoadMoreListener {
            smart_refresh_layout_frag_pro.finishLoadMore(300)
            presenter.detailsCategory(activity, pageNum, cid)
        }
        collect()
        /*   请求banner   */
        presenter.getHomeBanner()
        /*  请求文章列表  */
        presenter.getHomeListData(pageNum)
    }

    override fun openLoading(): Boolean {
        return true
    }

    override fun openRootLayout(): Boolean {
        return true
    }

    override fun initLoad() {
        presenter.detailsCategory(activity, pageNum, cid)
    }

    /* 收藏与取消收藏 */
    fun collect() {
        presenter.addCollectLD.observe(this, Observer {
            itemDataX[it].collect = true
            viewpageFragmentItemAdapter?.notifyItemChanged(it)
        })
        presenter.cancelCollectLD.observe(this, Observer {
            itemDataX[it].collect = false
            viewpageFragmentItemAdapter?.notifyItemChanged(it)
        })

        /* 在 我的收藏 中 取消收藏 成功时的回调 */
        LiveEventBus.get("cancel").observe(this, Observer {

            var map = it as HashMap<String, Int>
            map.forEach {
                if (it.key.equals("id")) {
                    for (item in itemDataX) {
                        if (item.id == it.value) {
                            item.collect = false
                            viewpageFragmentItemAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }

        })

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_project_viewpage_layout
    }
}