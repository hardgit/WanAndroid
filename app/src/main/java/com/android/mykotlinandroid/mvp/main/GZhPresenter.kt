package com.android.mykotlinandroid.mvp.main

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.android.mykotlinandroid.base.BasePresenter
import com.android.mykotlinandroid.base.net.exception.ResponseException
import com.android.mykotlinandroid.base.observer.LoadingObserver
import com.android.mykotlinandroid.http.RetrofitServices
import com.android.mykotlinandroid.mvp.response.GZHDetailsResponse
import com.android.mykotlinandroid.mvp.response.GZHResponse

/**
 * author : zf
 * date   : 2019/10/28
 * You are the best.
 */
class GZhPresenter(var activity: FragmentActivity?) : BasePresenter() {


    val response = MutableLiveData<List<GZHResponse>>()
    val gzhDetailsResponse = MutableLiveData<GZHDetailsResponse>()



   /* 公众号列表 */
    fun gzhList() {
       activity?.let {
           RequestManager.execute(activity, RetrofitManager.create(RetrofitServices::class.java).gzhList(), object :
               LoadingObserver<List<GZHResponse>>(it) {
               override fun onSuccess(data: List<GZHResponse>) {
                   response.postValue(data)
               }

               override fun onError(e: ResponseException) {

               }

           })
       }
    }
    /* 公众号列表详情 */
    fun gzhDetails(activity: FragmentActivity?,id:Int,pageNum:Int) {
        activity?.let {
            RequestManager.execute(activity, RetrofitManager.create(RetrofitServices::class.java).gzhDetails(id,pageNum), object :
                LoadingObserver<GZHDetailsResponse>(it) {
                override fun onSuccess(data: GZHDetailsResponse) {
                    gzhDetailsResponse.postValue(data)
                }

                override fun onError(e: ResponseException) {

                }

            })
        }
    }

}