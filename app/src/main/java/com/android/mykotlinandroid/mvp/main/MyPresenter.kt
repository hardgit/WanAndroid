package com.android.mykotlinandroid.mvp.main

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.android.mykotlinandroid.base.observer.BaseObserver
import com.android.mykotlinandroid.base.BasePresenter
import com.android.mykotlinandroid.base.net.exception.ResponseException
import com.android.mykotlinandroid.http.RetrofitServices
import com.android.mykotlinandroid.mvp.response.CollectResponse
import com.android.mykotlinandroid.mvp.response.UserResponse
import com.android.mykotlinandroid.utils.ToastUtil
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * author : zf
 * date   : 2019/10/28
 * You are the best.
 */
class MyPresenter(var activity: FragmentActivity?) : BasePresenter() {


    val collectResponse = MutableLiveData<CollectResponse>()

    var map: HashMap<String, Int> = HashMap()

    var cancelCollect = MutableLiveData<Int>()


   /* 用户登录 */
    fun userLogin(map: Map<String,String>) {
        RequestManager.execute(activity, RetrofitManager.create(RetrofitServices::class.java).getLogin(map), object :
            BaseObserver<UserResponse>() {
            override fun onSuccess(data: UserResponse) {
               SpUtil.setUsername(data.username)
               SpUtil.setUserId(data.id)
                LiveEventBus.get("userName").post(data.username)
                activity?.finish()
            }

            override fun onError(e: ResponseException) {

            }

        })
    }
    /* 用户注册 */
    fun userRegist(map: Map<String,String>) {
        RequestManager.execute(activity, RetrofitManager.create(RetrofitServices::class.java).getRegist(map), object :
            BaseObserver<UserResponse>() {
            override fun onSuccess(data: UserResponse) {
                ToastUtil.show(activity,"注册成功")
                activity?.finish()
            }

            override fun onError(e: ResponseException) {

            }

        })
    }

    /* 退出登录 */
    fun userExit(){
        RequestManager.execute(activity, RetrofitManager.create(RetrofitServices::class.java).getExit(), object :
            BaseObserver<Any>() {
            override fun onSuccess(data: Any) {
                ToastUtil.show(activity,"您已退出登录！")
                SpUtil.removeUserId()
                SpUtil.removeUserId()
                LiveEventBus.get("userExit").post("exit")
            }
            override fun onError(e: ResponseException) {

            }

        })
    }

    /* 我的收藏列表 */
    fun  collcetList(pageNum:Int){
        RequestManager.execute(activity,RetrofitManager.create(RetrofitServices::class.java).collectList(pageNum),object :
            BaseObserver<CollectResponse>(){
            override fun onSuccess(data: CollectResponse) {
                collectResponse.postValue(data)
            }

            override fun onError(e: ResponseException) {

            }

        })
    }

    /* 取消收藏 --我的收藏*/
    fun cancelMyCollect(position:Int,id:Int,originId:Int){
        RequestManager.execute(activity,RetrofitManager.create(RetrofitServices::class.java).cancelMyCollect(id,originId),object:
            BaseObserver<Any>(){
            override fun onSuccess(data: Any) {

                map?.put("position",position)
                map?.put("id",originId)
                LiveEventBus.get("cancel").post(map)
//               cancelCollect.postValue(position)
            }

            override fun onError(e: ResponseException) {
                println(e.getErrorMessage())
            }
        })
    }

}