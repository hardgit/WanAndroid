package com.android.mykotlinandroid.mvp.response

/**
 * author : zf
 * date   : 2019/11/5
 * You are the best.
 */
data class GZHResponse(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)