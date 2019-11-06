package com.android.mykotlinandroid.utils.image

import android.content.Context
import android.widget.ImageView
import com.youth.banner.loader.ImageLoader

/**
 * author : zf
 * date   : 2019/10/22
 * You are the best.
 */
class BannerImageLoader: ImageLoader() {
    override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
       GlideImageLoader.load(context,path as String,imageView)
    }
}