package com.android.mykotlinandroid

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.android.mykotlinandroid.utils.error.CrashHandler
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator
import com.scwang.smartrefresh.layout.footer.BallPulseFooter


/**
 * author : zf
 * date   : 2019/10/21
 * You are the best.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        CrashHandler.getInstance().init(this)
        // Dex 分包
        MultiDex.install(this)
        // 初始化LiveDataBus
        LiveEventBus
            .config()
            .lifecycleObserverAlwaysActive(true)
            .autoClear(false)

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(object : DefaultRefreshFooterCreator {
            override fun createRefreshFooter(context: Context, layout: RefreshLayout): RefreshFooter {
                //指定为经典Footer，默认是 BallPulseFooter
                return BallPulseFooter(context)
            }
        })
    }

    companion object {
        private lateinit var instance: MyApplication
        var ISLOGIN = false
        fun getApplication(): MyApplication {
            return instance
        }
    }


}