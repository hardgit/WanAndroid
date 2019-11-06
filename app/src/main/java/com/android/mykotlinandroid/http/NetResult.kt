package com.android.mykotlinandroid.http

import com.android.mykotlinandroid.base.NET_CODE_SUCCESS

/**
 * author : zf
 * date   : 2019/10/21
 * You are the best.
 */
data class NetResult<T>(var errorCode: Int, var errorMsg: String, var data: T)


