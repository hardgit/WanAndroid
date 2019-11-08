package com.android.mykotlinandroid.ui.activity

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.adapter.GZHDetailsAdapter
import com.android.mykotlinandroid.base.BaseActivity
import com.android.mykotlinandroid.base.BaseMvpActivity
import com.android.mykotlinandroid.http.ACTION_DATA_ID_KEY
import com.android.mykotlinandroid.http.ACTION_TITLE
import com.android.mykotlinandroid.mvp.main.HomePresenter
import com.android.mykotlinandroid.mvp.response.DataX
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.activity_gzhdetails.*
import kotlinx.android.synthetic.main.base_activity_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.toolbar
import kotlinx.android.synthetic.main.toolbar_layout.view.*


class GZHDetailsActivity : BaseMvpActivity<HomePresenter>() {

    private var gzh_details_adapter: GZHDetailsAdapter? = null
    private var itemDataX = arrayListOf<DataX>()
    private var pageNum = 0
    private var id = 0

    companion object {

        fun actionStart(context: BaseActivity, title: String, id: Int) {
            val intent = Intent(context, GZHDetailsActivity::class.java)
            intent.putExtra(ACTION_TITLE, title)
            intent.putExtra(ACTION_DATA_ID_KEY, id)
            context.startActivity(intent)
        }

    }

    override fun openLoading(): Boolean {
        return true
    }

    override fun openRootLayout(): Boolean {
        return true
    }

    override fun initPresenter(): HomePresenter {
        return HomePresenter(this)
    }

    override fun initView() {
        wempty_view.show(true)
        toolbar.title_text.text = intent.getStringExtra(ACTION_TITLE)
        id = intent.getIntExtra(ACTION_DATA_ID_KEY, 0)

        gzh_details_recycler.layoutManager = LinearLayoutManager(this)
        gzh_details_recycler.setHasFixedSize(true)
        /* 避免嵌套卡顿 */
        gzh_details_recycler.setHasFixedSize(true)
        gzh_details_recycler.isNestedScrollingEnabled = false
        /* 禁止刷新 */
        smart_refresh_layout_detalis.setEnableRefresh(false)
    }

    override fun initLoad() {
        presenter.gzhDetails(this, id, 1)
    }

    override fun initData() {
        presenter.gzhDetailsResponse.observe(this, Observer {
           wempty_view.hide()
            if (gzh_details_adapter == null) {
                itemDataX.clear()
                itemDataX.addAll(it.datas)//单独创建一个数据集避免点击事件index溢出
                gzh_details_adapter = GZHDetailsAdapter(R.layout.home_bottom_list, it.datas)
                gzh_details_adapter?.openLoadAnimation()
                gzh_details_recycler.adapter = gzh_details_adapter
            } else {
                itemDataX.addAll(it.datas) //单独创建一个数据集避免点击事件index溢出
                gzh_details_adapter?.addData(it.datas)
                gzh_details_adapter?.notifyDataSetChanged()
            }

            pageNum++

            gzh_details_adapter?.run {
                /* item整体点击事件 */
                onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->

                    ArticleWebActivity.actionStart(
                        this@GZHDetailsActivity,
                        itemDataX[position].title,
                        itemDataX[position].link
                    )

                }
                /* 收藏文章点击事件 */
                onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                    if (view.id == R.id.love_img) {
                        if (!itemDataX[position].collect) {
                            println("123")
                            presenter.addCollect(position, itemDataX[position].id)
                        } else {
                            println("456")
                            presenter.cancelCollect(position, itemDataX[position].id)
                        }

                    }
                }
            }
        })
        smart_refresh_layout_detalis.setOnLoadMoreListener {
            smart_refresh_layout_detalis.finishLoadMore(300)
            presenter.gzhDetails(this,id,pageNum)
        }
        collect()//收藏与取消收藏
    }

    /* 收藏与取消收藏 */
    fun collect() {
        presenter.addCollectLD.observe(this, Observer {
            itemDataX[it].collect = true
            gzh_details_adapter?.notifyItemChanged(it)
        })
        presenter.cancelCollectLD.observe(this, Observer {
            itemDataX[it].collect = false
            gzh_details_adapter?.notifyItemChanged(it)
        })

        /* 在 我的收藏 中 取消收藏 成功时的回调 */
        LiveEventBus.get("cancel").observe(this, Observer {

            var map = it as HashMap<String, Int>
            map.forEach {
                if (it.key.equals("id")) {
                    for (item in itemDataX) {
                        if (item.id == it.value) {
                            item.collect = false
                            gzh_details_adapter?.notifyDataSetChanged()
                        }
                    }
                }
            }

        })

    }
    override fun getIayoutId(): Int {
        return R.layout.activity_gzhdetails
    }

}
