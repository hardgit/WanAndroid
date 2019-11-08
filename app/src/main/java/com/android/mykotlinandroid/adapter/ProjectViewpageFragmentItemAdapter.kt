package com.android.mykotlinandroid.adapter

import android.text.Html
import android.widget.ImageView
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.mvp.response.DataX
import com.android.mykotlinandroid.mvp.response.HomeListResponse
import com.android.mykotlinandroid.utils.image.GlideImageLoader
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder



/**
 * author : zf
 * date   : 2019/10/23
 * You are the best.
 */
class ProjectViewpageFragmentItemAdapter(layoutResId: Int, data: List<DataX>) : BaseQuickAdapter<DataX, BaseViewHolder>(layoutResId,data) {


    override fun convert(helper: BaseViewHolder, item: DataX?) {

        helper.run{
            if(item?.author?.isEmpty()!!)
                setText(R.id.item_author,item?.superChapterName)
            else  setText(R.id.item_author,item?.author)

            getView<ImageView>(R.id.love_img).run {
                if(item.collect){
                    setImageResource(R.id.love_img,R.mipmap.love_star)
                }else{
                    setImageResource(R.id.love_img,R.mipmap.love)
                }
            }
            item?.envelopePic?.let { GlideImageLoader.load(mContext, it,getView(R.id.item_img)) }
            setText(R.id.item_title,Html.fromHtml(item?.title))
            setText(R.id.item_time,item?.niceDate)
            setText(R.id.item_content,Html.fromHtml(item?.desc))
            addOnClickListener(R.id.love_img)
        }


    }

}