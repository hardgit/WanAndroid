package com.android.mykotlinandroid.ui.activity


import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.adapter.CollectListAdapter
import com.android.mykotlinandroid.base.BaseMvpActivity
import com.android.mykotlinandroid.http.ACTION_TITLE
import com.android.mykotlinandroid.http.ACTION_WEB_URL
import com.android.mykotlinandroid.mvp.main.MyPresenter
import com.android.mykotlinandroid.mvp.response.CollectData
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.activity_my_collect.*
import kotlinx.android.synthetic.main.base_activity_layout.*

class MyCollectActivity : BaseMvpActivity<MyPresenter>() {


    private var collectList = arrayListOf<CollectData>()
    private var collectionAdapter: CollectListAdapter? = null
    private var pageNum = 0
    private var flag = true

    override fun initPresenter(): MyPresenter {
        return MyPresenter(this)
    }


    override fun setTitleName(): String {
        return getString(R.string.my_collect)
    }

    override fun openLoading(): Boolean {
        return true
    }

    override fun openRootLayout(): Boolean {
        return true
    }

    override fun initView() {
        wempty_view.show(true)
        collect_recycler.layoutManager = LinearLayoutManager(this)
        collect_recycler.setHasFixedSize(true)
        /* 避免嵌套卡顿 */
        collect_recycler.setHasFixedSize(true)
        collect_recycler.isNestedScrollingEnabled = false

        smart_refresh_collect.run {
            /* 刷新 */
            setOnRefreshListener {
                flag = false
                presenter.collcetList(0)
                smart_refresh_collect.finishRefresh(300)
            }
            /* 加载 */
            setOnLoadMoreListener {
                this.finishLoadMore(300)
                presenter.collcetList(pageNum)
            }
        }
    }

    override fun initLoad() {
        presenter.collcetList(pageNum)
    }

    override fun initData() {
        presenter.collectResponse.observe(this, Observer {
            wempty_view.hide()
            if (flag && it.datas.isNotEmpty()) {
                pageNum++
            }
            if (collectionAdapter == null) {  //第一次加载
                collectList.clear()
                collectList.addAll(it.datas)
                collectionAdapter = CollectListAdapter(R.layout.home_bottom_list, it.datas)
                collect_recycler.adapter = collectionAdapter
            } else {
                /* 如果是刷新  就清空coolectList集合中的数据，重新添加第1页数据  */
                if(!flag){
                    pageNum = 1
                    collectList.clear()
                    collectList.addAll(it.datas)
                    collectionAdapter = CollectListAdapter(R.layout.home_bottom_list,it.datas)
                    collect_recycler.adapter = collectionAdapter
                }else{
                    collectList.addAll(it.datas)
                    collectionAdapter?.addData(it.datas)
                    collectionAdapter?.notifyDataSetChanged()
                }


            }

            collectionAdapter?.run {
                /* item整体点击事件 */
                onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
                    openLoadAnimation()
                    ArticleWebActivity.actionStart(
                        this@MyCollectActivity,
                        collectList[position].title,
                        collectList[position].link
                    )
                }
                /* 收藏文章点击事件 */
                onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                    if (view.id == R.id.love_img) {
                        presenter.cancelMyCollect(position, collectList[position].id, collectList[position].originId)
                    }
                }
            }
            flag = true
        })
        LiveEventBus.get("cancel").observe(this, Observer {
            var map = it as HashMap<String, Int>
            map.forEach {
                if (it.key.equals("position")) {
                    collectionAdapter?.remove(it.value)
                    collectionAdapter?.notifyItemChanged(it.value)
                }
            }
        })
    }

    override fun getIayoutId(): Int {
        return R.layout.activity_my_collect
    }

    companion object {
        /**
         * 界面入口
         *
         * @param context Context 对象
         * @param title 标题
         * @param url Web Url
         */
        fun actionStart(context: Activity, title: String, url: String) {
            context.startActivity(Intent(context, MyCollectActivity::class.java).apply {
                putExtra(ACTION_TITLE, title)
                putExtra(ACTION_WEB_URL, url)
                if (context !is Activity) {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            })
        }
    }

}
