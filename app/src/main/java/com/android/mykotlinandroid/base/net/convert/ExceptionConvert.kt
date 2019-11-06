package com.android.mykotlinandroid.base.net.convert

import com.android.mykotlinandroid.base.net.exception.ExceptionHandler
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function

class ExceptionConvert<E> : Function<Throwable, ObservableSource<out E>> {
    override fun apply(throwable: Throwable): ObservableSource<out E> {
        return Observable.error(ExceptionHandler.handle(throwable))
    }
}