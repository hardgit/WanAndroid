package com.android.mykotlinandroid.mvp.main

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.android.mykotlinandroid.base.BasePresenter
import com.android.mykotlinandroid.base.net.exception.ResponseException
import com.android.mykotlinandroid.base.observer.BaseObserver
import com.android.mykotlinandroid.http.RetrofitServices
import com.android.mykotlinandroid.mvp.response.NavResponse

/**
 * author : zf
 * date   : 2019/11/4
 * You are the best.
 */
class GpsPresenter(var activity: FragmentActivity? = null): BasePresenter(){

    /*  导航类型列表  */
    var hotTypeMutableList = MutableLiveData<List<NavResponse>>()

    /*  导航类型列表  */
    fun hotType(){
         RequestManager.execute(activity,RetrofitManager.create(RetrofitServices::class.java).hotTypeData()
             ,object : BaseObserver<List<NavResponse>>(){
             override fun onSuccess(data: List<NavResponse>) {
                 hotTypeMutableList.postValue(data)
             }

             override fun onError(e: ResponseException) {
             }

         })
    }

}