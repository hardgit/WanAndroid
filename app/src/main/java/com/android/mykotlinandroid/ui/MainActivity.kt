package com.android.mykotlinandroid.ui

import android.os.Handler
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.android.mykotlinandroid.MyApplication
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.adapter.MyFragmentPageAdapter
import com.android.mykotlinandroid.base.BaseActivity
import com.android.mykotlinandroid.ui.fragment.*
import com.android.mykotlinandroid.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

     private var isBackPressed = false
    //延时加载，表示一定不为空。
    lateinit var fragmentList:MutableList<Fragment>

    override fun getIayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        if(!SpUtil.getCookies().isEmpty()){
            MyApplication.ISLOGIN = SpUtil.getUserId()!=0
        }else{
            MyApplication.ISLOGIN = false
            SpUtil.removeUserId()
            SpUtil.getUsername()
        }

        mviewpage.offscreenPageLimit = 4
       fragmentList = ArrayList()
       fragmentList.add(HomeFragment.newInstance())
       fragmentList.add(ProjectFragment.newInstance())
       fragmentList.add(GPSFragment.newInstance())
       fragmentList.add(GZHFragment.newInstance())
       fragmentList.add(MyFragment.newInstance())

        var fragmentPagerAdapter = MyFragmentPageAdapter(getSupportFragmentManager(),fragmentList)
        mviewpage.adapter = fragmentPagerAdapter
        bottom_navigation_View.setupWithViewPager(mviewpage)
    }

    override fun onBackPressed() {

            if (isBackPressed) {
                super.onBackPressed()
                return
            }
            isBackPressed = true
            ToastUtil.show(this, R.string.app_exit)
            Handler().postDelayed({ isBackPressed = false }, 2000)

    }

    override fun initLoad() {

    }

    override fun initData() {

    }

}
