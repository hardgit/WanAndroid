package com.android.mykotlinandroid.mvp.response

/**
 * author : zf
 * date   : 2019/10/21
 * You are the best.
 */
data class BannerResponse(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)