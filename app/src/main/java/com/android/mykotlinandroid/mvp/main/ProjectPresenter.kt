package com.android.mykotlinandroid.mvp.main

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.android.mykotlinandroid.base.observer.BaseObserver
import com.android.mykotlinandroid.base.BasePresenter
import com.android.mykotlinandroid.base.net.exception.ResponseException
import com.android.mykotlinandroid.http.RetrofitServices
import com.android.mykotlinandroid.mvp.response.ProjectCategoryResponse

/**
 * author : zf
 * date   : 2019/10/28
 * You are the best.
 */
class ProjectPresenter(var activity: FragmentActivity?) : BasePresenter() {


    val projectCategoryResponse = MutableLiveData<List<ProjectCategoryResponse>>()

   /* 项目类型列表 */
    fun projectCategory() {
        RequestManager.execute(activity, RetrofitManager.create(RetrofitServices::class.java).projectCategory(), object :
            BaseObserver<List<ProjectCategoryResponse>>() {
            override fun onSuccess(data: List<ProjectCategoryResponse>) {
                projectCategoryResponse.postValue(data)
            }

            override fun onError(e: ResponseException) {

            }

        })
    }


}