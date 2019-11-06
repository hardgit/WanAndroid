package com.android.mykotlinandroid.base

import android.os.Bundle
import android.view.View
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

/**
 * author : zf
 * date   : 2019/10/21
 * You are the best.
 */
abstract class BaseMvpFragment<P : BasePresenter> : BaseFragment() {
    lateinit var presenter: P
    lateinit var xPopup: LoadingPopupView

    abstract fun initPresenter(): P

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        presenter = initPresenter()
        xPopup = XPopup.Builder(activity).asLoading()

        super.onActivityCreated(savedInstanceState)

    }

}