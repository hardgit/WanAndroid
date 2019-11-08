package com.android.mykotlinandroid.base.observer

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.android.mykotlinandroid.base.BaseActivity
import com.android.mykotlinandroid.base.net.exception.ResponseException
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

/**
 * author : zf
 * date   : 2019/10/31
 * You are the best.
 */
abstract class LoadingObserver<E>(context: Context, var isloading :Boolean = true, showErrorTip: Boolean = true):BaseObserver<E>(showErrorTip) {
    private val mContext: WeakReference<Context> = WeakReference(context)
    private var xPopup:LoadingPopupView
    init {
        xPopup = XPopup.Builder(mContext.get()).asLoading()
    }

    override fun onSubscribe(d: Disposable) {
        if(isloading){
//            xPopup.show()
        }
        super.onSubscribe(d)
    }

    override fun onNext(data: E) {
       if(xPopup.isShow){
           xPopup.dismiss()
       }
        super.onNext(data)
    }

    override fun onError(e: ResponseException) {

    }

    override fun onError(e: Throwable) {
        super.onError(e)
        if(xPopup.isShow){
            xPopup.dismiss()
        }
    }


    override fun onComplete() {
        super.onComplete()
    }
}