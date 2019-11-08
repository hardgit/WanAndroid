package com.android.mykotlinandroid.mvp.main

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.android.mykotlinandroid.base.BaseActivity
import com.android.mykotlinandroid.base.BaseFragment
import com.android.mykotlinandroid.base.observer.BaseObserver
import com.android.mykotlinandroid.base.BasePresenter
import com.android.mykotlinandroid.base.net.exception.ResponseException
import com.android.mykotlinandroid.base.observer.LoadingObserver
import com.android.mykotlinandroid.http.RetrofitServices
import com.android.mykotlinandroid.mvp.response.BannerResponse
import com.android.mykotlinandroid.mvp.response.GZHDetailsResponse
import com.android.mykotlinandroid.mvp.response.HomeListResponse
import com.android.mykotlinandroid.mvp.response.ProjectViewpageResponse
import com.android.mykotlinandroid.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.base_fragment_layout.*

/**
 * author : zf
 * date   : 2019/10/21
 * You are the best.
 */

class HomePresenter(var activity: FragmentActivity? = null):BasePresenter(){

    /* 首页Banner数据集 */
    val bannerList = MutableLiveData<List<BannerResponse>>()
    /* 首页文章列表集 */
    val homeListData = MutableLiveData<HomeListResponse>()

    /* 收藏或取消文章 */
    val addCollectLD = MutableLiveData<Int>()

    /* 取消文章添加 */
    val cancelCollectLD = MutableLiveData<Int>()

    /* 公众号详情列表 */
    val gzhDetailsResponse = MutableLiveData<GZHDetailsResponse>()

    /* 公众号详情列表 */
    val detailsCategoryResponse = MutableLiveData<ProjectViewpageResponse>()

    /* 首页Banner数据集 */
    fun getHomeBanner(){
        activity?.let {
            RequestManager.execute(activity,RetrofitManager.create(RetrofitServices::class.java).getHomeBannerList(),object :
                LoadingObserver<List<BannerResponse>>(it){
                override fun onSuccess(data: List<BannerResponse>) {
                    bannerList.postValue(data)
                }

                override fun onError(e: ResponseException) {
                    println(e.getErrorMessage())
                }
            })
        }
    }

    /* 首页文章列表集 */
    fun getHomeListData(pageNum:Int){
        RequestManager.execute(activity,RetrofitManager.create(RetrofitServices::class.java).getHomeListData(pageNum),object:
            BaseObserver<HomeListResponse>(){
            override fun onSuccess(data: HomeListResponse) {
                homeListData.postValue(data)
            }

            override fun onError(e: ResponseException) {

            }
        })
    }

    /* 添加文章 */
    fun addCollect(position:Int,id:Int){

            RequestManager.execute(activity,RetrofitManager.create(RetrofitServices::class.java).addCollect(id),object:
                BaseObserver<Any>(){
                override fun onSuccess(data: Any) {
                    println( Thread.currentThread().name)

                    addCollectLD.postValue(position)
                }

                override fun onError(e: ResponseException) {
                    println(e.getErrorMessage())
                }
            })

    }

    /* 取消添加 --文字列表*/
    fun cancelCollect(position:Int,id:Int){
        RequestManager.execute(activity,RetrofitManager.create(RetrofitServices::class.java).cancelCollect(id),object:
            BaseObserver<Any>(){
            override fun onSuccess(data: Any) {
               cancelCollectLD.postValue(position)
            }

            override fun onError(e: ResponseException) {
                println(e.getErrorMessage())
            }
        })
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


    /* 项目类型列表详情 */
    fun detailsCategory(activity: FragmentActivity?,pageNum:Int,cid:Int) {
        activity?.let {
            RequestManager.execute(activity, RetrofitManager.create(RetrofitServices::class.java).detailsCategory(pageNum,cid), object :
                LoadingObserver<ProjectViewpageResponse>(it) {
                override fun onSuccess(data:ProjectViewpageResponse) {
                    detailsCategoryResponse.postValue(data)
                }

                override fun onError(e: ResponseException) {

                }

            })
        }
    }

}