package com.android.mykotlinandroid.base

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.os.SystemClock
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import android.view.WindowManager
import android.os.Build
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import com.android.mykotlinandroid.R
import kotlinx.android.synthetic.main.base_activity_layout.*


/**
 * author : zf
 * date   : 2019/10/18
 * You are the best.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(getIayoutId(), null)

        var boolean = openRootLayout()
        if(!boolean){// false  正常加载
            setContentView(view)
        }else{//使用根布局
            setContentView(R.layout.base_activity_layout)
            base_layout.addView(view)
            if (openLoading()) {  //隐藏loading
                wempty_view.hide()
            }
        }

        initView()
        initLoad()
        initData()

        if (toolbar != null) {
            toolbar.back_img.setOnClickListener {
                finish()
            }
            if(!setTitleName().isEmpty()){
                toolbar.title_text.text =setTitleName()
            }

        }

    }
    abstract fun initView()
    abstract fun initLoad()
    abstract fun initData()
    abstract fun getIayoutId(): Int
    open protected fun setTitleName(): String {
        return ""
    }
    //是否启用根布局   默认不启用
    protected open fun openRootLayout(): Boolean {
        return false
    }
    //是否启用Loading  默认不启用
    protected open fun openLoading(): Boolean {
        return false
    }


    /* 处理activity多重跳转========start */
    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        if (startActivitySelfCheck(intent!!)) {
            // startActivity 最终会调用 startActivityForResult
            super.startActivityForResult(intent, requestCode)
        }
    }

    private var mStartActivityTag: String = ""
    protected var mStartActivityTime: Long = 0

    protected fun startActivitySelfCheck(intent: Intent): Boolean {
        var result = true
        var tag: String
        if (intent.component != null) { // 显式跳转
            tag = intent.component!!.className
        } else if (intent.action != null) {// 隐式跳转
            tag = intent.action!!
        } else {// 其他方式
            return true
        }
        if (tag == mStartActivityTag && mStartActivityTime >= SystemClock.uptimeMillis() - 500) {
            // 检查不通过
            result = false
        }
        mStartActivityTag = tag
        mStartActivityTime = SystemClock.uptimeMillis()
        return result
    }
    /* 处理activity多重跳转========stop */
}