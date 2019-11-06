package com.android.mykotlinandroid.utils.image

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * author : zf
 * date   : 2019/10/22
 * You are the best.
 */
class GlideImageLoader {

    companion object{
        fun load(context: Context,url: String,iv: ImageView){
            Glide.with(context)
                .load(url)
                .apply(RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(iv)
        }
    }

}