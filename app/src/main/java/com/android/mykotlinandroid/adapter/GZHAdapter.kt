package com.android.mykotlinandroid.adapter
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.mvp.response.GZHResponse
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_fragment_gzh.view.*

/**
 * author : zf
 * date   : 2019/11/5
 * You are the best.
 */
class GZHAdapter(layoutResId: Int, data: List<GZHResponse>) :
    BaseQuickAdapter<GZHResponse, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: GZHResponse?) {
       helper.setText(R.id.gzh_item_content,item?.name)
    }
}