package com.android.mykotlinandroid.ui.fragment


import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.base.BaseFragment
import com.android.mykotlinandroid.base.BaseMvpFragment
import com.android.mykotlinandroid.mvp.main.GpsPresenter
import com.android.mykotlinandroid.mvp.response.Article
import com.android.mykotlinandroid.utils.tablayout.VerticalTabLayout
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.fragment_layout_gps.*

/**
 * author : zf
 * date   : 2019/10/18
 * You are the best.
 */
class GPSFragment : BaseMvpFragment<GpsPresenter>() {

    private val fragments: ArrayList<BaseFragment> = arrayListOf()

    override fun initPresenter(): GpsPresenter {
        return GpsPresenter(activity)
    }

    override fun initLoad() {
        presenter.hotType()
    }

    override fun initData() {

        vertical_tablayout.setOnTabClickListener(object : VerticalTabLayout.OnTabClickListener {
            override fun onTabClick(oldTabIndex: Int, newTabIndex: Int) {
                fragmentManager?.beginTransaction()
                    ?.hide(fragments[oldTabIndex])
                    ?.show(fragments[newTabIndex])
                    ?.commit()
            }
        })

        presenter.hotTypeMutableList.observe(this, Observer {
            var tabs = arrayListOf<String>()
            for (datas in it) {
                tabs.add(datas.name)//类型数据集
                fragments.add(GPSTypeFragment.newInstance(datas.articles as ArrayList<Article>))
            }
            vertical_tablayout.addTabs(tabs)
            initFrameLayout()
        })

    }

    fun initFrameLayout() {
        val transaction = fragmentManager?.beginTransaction()
        /* 遍历fragments 并隐藏*/
        for (frgs in fragments) {
            transaction?.add(R.id.gps_frame_layout, frgs)
            transaction?.hide(frgs)
        }
        transaction?.show(fragments[0])
        transaction?.commit()
    }

    override fun initView() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_layout_gps
    }

    companion object {
        fun newInstance(): GPSFragment {
            return GPSFragment()
        }
    }

}