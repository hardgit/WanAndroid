package com.android.mykotlinandroid.adapter

import android.text.Html
import android.widget.ImageView
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.mvp.response.CollectData
import com.android.mykotlinandroid.mvp.response.DataX
import com.android.mykotlinandroid.mvp.response.HomeListResponse
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


/**
 * author : zf
 * date   : 2019/10/23
 * You are the best.
 */
class CollectListAdapter(layoutResId: Int, data: List<CollectData>) :
    BaseQuickAdapter<CollectData, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder, item: CollectData?) {

        helper.run {
            if (item?.author?.isEmpty()!!)
                setText(R.id.item_author, item?.chapterName)
            else setText(R.id.item_author, item?.chapterName + "——${item?.author}")

            getView<ImageView>(R.id.love_img).run {

                setImageResource(R.id.love_img, R.mipmap.love_star)

            }

            setText(R.id.item_title, Html.fromHtml(item?.title))
            setText(R.id.item_time, item?.niceDate)
            addOnClickListener(R.id.love_img)
        }


    }

}