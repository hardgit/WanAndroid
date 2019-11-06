package com.android.mykotlinandroid.mvp.response

/**
 * author : zf
 * date   : 2019/10/30
 * You are the best.
 */
data class  CollectResponse(
val curPage: Int,
val datas: List<CollectData>,
val offset: Int,
val over: Boolean,
val pageCount: Int,
val size: Int,
val total: Int
)
