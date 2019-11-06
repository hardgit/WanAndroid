package com.android.mykotlinandroid.mvp.response

/**
 * author : zf
 * date   : 2019/10/23
 * You are the best.
 */
data class HomeListResponse(
    val curPage: Int,
    val datas: List<DataX>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)



