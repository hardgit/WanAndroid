package com.android.mykotlinandroid.mvp.response

/**
 * author : zf
 * date   : 2019/10/28
 * You are the best.
 */
data class UserResponse(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val collectIds: List<Any>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)