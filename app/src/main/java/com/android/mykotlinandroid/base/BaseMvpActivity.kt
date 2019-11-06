package com.android.mykotlinandroid.base

import android.app.Application
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

/**
 * author : zf
 * date   : 2019/10/21
 * You are the best.
 */
abstract class BaseMvpActivity<P : BasePresenter> : BaseActivity() {

    lateinit var presenter: P
    lateinit var xPopup: LoadingPopupView

    abstract fun initPresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = initPresenter()
        xPopup = XPopup.Builder(this).asLoading()
        super.onCreate(savedInstanceState)
    }
}