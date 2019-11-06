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


/**
 * author : zf
 * date   : 2019/10/18
 * You are the best.
 */
abstract class BaseActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStatusBarTransparent()
        setContentView(getIayoutId())

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

//    /**
//     * 设置透明状态栏
//     */
//    private fun setStatusBarTransparent() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val window = window
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            window.decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.statusBarColor = Color.TRANSPARENT
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        }
//
//    }


    abstract fun initView()
    abstract fun initLoad()
    abstract fun initData()
    abstract fun getIayoutId(): Int
    open protected fun setTitleName(): String {
        return ""
    }

    protected fun initToolbar(@StringRes titleId: Int) {
        initToolbar(getString(titleId))
    }

    protected fun initToolbar(titleStr: String) {
        toolbar.run {
            title = titleStr
            setSupportActionBar(this)
            setNavigationOnClickListener {
                finish()
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    /* 处理activity多重跳转 */
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
}