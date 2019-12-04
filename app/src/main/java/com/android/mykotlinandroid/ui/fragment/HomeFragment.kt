package com.android.mykotlinandroid.ui.fragment

import android.text.Html
import android.view.View
import android.view.ViewGroup
import com.android.mykotlinandroid.base.BaseMvpFragment
import com.android.mykotlinandroid.mvp.main.HomePresenter
import com.android.mykotlinandroid.ui.view.Contract
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.adapter.HomeListAdapter
import com.android.mykotlinandroid.mvp.response.BannerResponse
import com.android.mykotlinandroid.mvp.response.DataX
import com.android.mykotlinandroid.ui.activity.ArticleWebActivity
import com.android.mykotlinandroid.utils.image.BannerImageLoader
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jeremyliao.liveeventbus.LiveEventBus
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.banner_layout.view.*
import kotlinx.android.synthetic.main.base_fragment_layout.*
import kotlinx.android.synthetic.main.fragment_layout_home.*


/**
 * author : zf
 * date   : 2019/10/18
 * You are the best.
 */
class HomeFragment : BaseMvpFragment<HomePresenter>(), Contract.View {


    private var homeListAdapter: HomeListAdapter? = null
    private var itemDataX = arrayListOf<DataX>()
    private lateinit var banners: List<BannerResponse>
    private var pageNum = 0
    private lateinit var headerView: View
    override fun initPresenter(): HomePresenter {
        return HomePresenter(activity)
    }

    override fun initView() {
        wempty_view.show(true)
        headerView = getHeaderBannerView()
        /*  初始化banner  */
        headerView.homeBanner.run {
            setImageLoader(BannerImageLoader())
            setDelayTime(3000)
            setIndicatorGravity(BannerConfig.RIGHT)
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            setOnBannerListener {
                activity?.let { it1 -> ArticleWebActivity.actionStart(it1, banners[it].title, banners[it].url) }
            }
        }
        home_recycler.layoutManager = LinearLayoutManager(activity)
        home_recycler.setHasFixedSize(true)
        /* 避免嵌套卡顿 */
        home_recycler.setHasFixedSize(true)
        home_recycler.isNestedScrollingEnabled = false
        /* 禁止刷新 */
        smart_refresh_layout.setEnableRefresh(false)

    }
    override fun openLoading(): Boolean {
        return true
    }

    override fun openRootLayout(): Boolean {
        return true
    }

    override fun initLoad() {

    }

    override fun initData() {

        /*  banner数据集回调  */
        presenter.bannerList.observe(this, Observer {
            banners = it
            var imgs = arrayListOf<String>()
            var imgTexts = arrayListOf<String>()
            for (banners in it) {
                imgs.add(banners.imagePath)
                imgTexts.add(Html.fromHtml(banners.title).toString())
            }
            headerView.homeBanner.run {
                setImages(imgs)
                setBannerTitles(imgTexts)
                start()
            }
        })
        /* 首页文章列表 */
        presenter.homeListData.observe(this, Observer {
            wempty_view.hide()
            if (homeListAdapter == null) {
                itemDataX.clear()
                itemDataX.addAll(it.datas)//单独创建一个数据集避免点击事件index溢出
                homeListAdapter = HomeListAdapter(R.layout.home_bottom_list, it.datas)
                homeListAdapter?.addHeaderView(headerView)
                homeListAdapter?.openLoadAnimation()
                home_recycler.adapter = homeListAdapter
            } else {
                itemDataX.addAll(it.datas) //单独创建一个数据集避免点击事件index溢出
                homeListAdapter?.addData(it.datas)
                homeListAdapter?.notifyDataSetChanged()
            }

            pageNum++

            homeListAdapter?.run {
                /* item整体点击事件 */
                onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
                    activity?.let { it1 ->
                        ArticleWebActivity.actionStart(it1, itemDataX[position].title, itemDataX[position].link)
                    }
                }
                /* 收藏文章点击事件 */
                onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
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
        smart_refresh_layout.setOnLoadMoreListener {
            smart_refresh_layout.finishLoadMore(300)
            presenter.getHomeListData(pageNum)
        }
        collect()
        /*   请求banner   */
        presenter.getHomeBanner()
        /*  请求文章列表  */
        presenter.getHomeListData(pageNum)
    }


    /* 收藏与取消收藏 */
    fun collect() {
        presenter.addCollectLD.observe(this, Observer {
            itemDataX[it].collect = true
            homeListAdapter?.notifyItemChanged(it + 1)
        })
        presenter.cancelCollectLD.observe(this, Observer {
            itemDataX[it].collect = false
            homeListAdapter?.notifyItemChanged(it + 1)
        })

        /* 在 我的收藏 中 取消收藏 成功时的回调 */
        LiveEventBus.get("cancel").observe(this, Observer {

            var map = it as HashMap<String, Int>
            map.forEach {
                if (it.key.equals("id")) {
                    for (item in itemDataX) {
                        if (item.id == it.value) {
                            item.collect = false
                            homeListAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }

        })

    }



    private fun getHeaderBannerView(): View {
        return layoutInflater.inflate(R.layout.banner_layout, home_recycler.parent as ViewGroup, false)
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_layout_home
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

}