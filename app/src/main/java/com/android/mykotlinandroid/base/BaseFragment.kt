package com.android.mykotlinandroid.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.mykotlinandroid.R
import kotlinx.android.synthetic.main.base_fragment_layout.view.*

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
            val view = layoutInflater.inflate(getLayoutResId(), null)
            if(!openRootLayout()){ // 默认false不使用根布局
                rootView = view
                return rootView
            } else{//使用根布局
                rootView = layoutInflater.inflate(R.layout.base_fragment_layout, null).apply {

                    base_layout.addView(view)
                    if (openLoading()) {  //隐藏loading
                        wempty_view.hide()
                        println("我走了啊啦啦l")
                    }
                }
                return rootView
            }

        } else {
            (rootView?.parent as? ViewGroup?)?.removeView(rootView)
        }

        return rootView
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun initLoad()
    abstract fun getLayoutResId():Int


    //是否启用根布局   默认不启用
    protected open fun openRootLayout(): Boolean {
        return false
    }
    //是否启用Loading  默认不启用
    protected open fun openLoading(): Boolean {
        return false
    }

}