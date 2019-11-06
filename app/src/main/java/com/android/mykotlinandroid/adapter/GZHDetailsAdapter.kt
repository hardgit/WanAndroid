package com.android.mykotlinandroid.adapter

import android.text.Html
import android.widget.ImageView
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.mvp.response.DataX
import com.android.mykotlinandroid.mvp.response.HomeListResponse
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder



/**
 * author : zf
 * date   : 2019/10/23
 * You are the best.
 */
class GZHDetailsAdapter(layoutResId: Int, data: List<DataX>) : BaseQuickAdapter<DataX, BaseViewHolder>(layoutResId,data) {


    override fun convert(helper: BaseViewHolder, item: DataX?) {

        helper.run{
            if(item?.author?.isEmpty()!!)
                setText(R.id.item_author,item?.superChapterName)
            else  setText(R.id.item_author,item?.superChapterName+"——${item?.author}")

            getView<ImageView>(R.id.love_img).run {
                if(item.collect){
                    setImageResource(R.id.love_img,R.mipmap.love_star)
                }else{
                    setImageResource(R.id.love_img,R.mipmap.love)
                }
            }
            println(item?.chapterName)
            setText(R.id.item_title,Html.fromHtml(item?.title))
            setText(R.id.item_time,item?.niceDate)
            addOnClickListener(R.id.love_img)
        }


    }

}