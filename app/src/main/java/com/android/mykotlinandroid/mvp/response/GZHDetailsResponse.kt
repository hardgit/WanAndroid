package com.android.mykotlinandroid.mvp.response

/**
 * author : zf
 * date   : 2019/11/5
 * You are the best.
 */
class GZHDetailsResponse (val over: Boolean = false,
                          val pageCount: Int = 0,
                          val total: Int = 0,
                          val curPage: Int = 0,
                          val offset: Int = 0,
                          val size: Int = 0,
                          val datas: List<DataX>)
