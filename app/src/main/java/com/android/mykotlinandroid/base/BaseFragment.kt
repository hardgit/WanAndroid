package com.android.mykotlinandroid.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * author : zf
 * date   : 2019/10/18
 * You are the best.
 */
abstract class BaseFragment:Fragment(){

    var rootView: View? = null
    lateinit var mContext: BaseActivity

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
        initLoad()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as BaseActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == rootView) {
            // 初始化 DataBinding
            rootView = inflater.inflate(getLayoutResId(), container, false)
          return rootView
        } else {
            (rootView?.parent as? ViewGroup?)?.removeView(rootView)
        }

        return rootView
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun initLoad()
    abstract fun getLayoutResId():Int

}