package com.android.mykotlinandroid.base.observer

import android.content.Context
import android.text.TextUtils
import com.android.mykotlinandroid.MyApplication
import com.android.mykotlinandroid.utils.ToastUtil
import com.android.mykotlinandroid.base.net.exception.ResponseException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

abstract class BaseObserver<E>(private val showErrorTip: Boolean = true) : Observer<E> {
    private val wrContext: WeakReference<Context> = WeakReference(MyApplication.getApplication())

    private lateinit var disposable: Disposable

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onNext(data: E) {
        onSuccess(data)
    }

    override fun onError(e: Throwable) {

        val responseException: ResponseException = e as ResponseException
        val errorMessage = responseException.getErrorMessage()
        if (showErrorTip && !TextUtils.isEmpty(errorMessage)) {
            ToastUtil.show(wrContext.get(), errorMessage)
        }
        onError(responseException)

    }

    override fun onComplete() {

    }

    fun getDisposable() = disposable

    abstract fun onSuccess(data: E)

    abstract fun onError(e: ResponseException)
}