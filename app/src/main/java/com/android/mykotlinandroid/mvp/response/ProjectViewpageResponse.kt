package com.android.mykotlinandroid.mvp.response

/**
 * author : zf
 * date   : 2019/11/6
 * You are the best.
 */
data class ProjectViewpageResponse(
    val curPage: Int,
    val datas: List<DataX>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)
