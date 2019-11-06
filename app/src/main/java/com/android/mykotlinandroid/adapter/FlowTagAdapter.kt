package com.android.mykotlinandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.mvp.response.Article
import com.android.mykotlinandroid.utils.tag.FlowTagLayout
import java.util.ArrayList

/**
 * author : zf
 * date   : 2019/11/4
 * You are the best.
 */
class FlowTagAdapter<T> : BaseAdapter,FlowTagLayout.OnInitSelectedPosition {

    private val mContext: Context
    private var mDataList: MutableList<T>

    constructor(context: Context){
        this.mContext = context
        mDataList = ArrayList<T>()
    }
    override fun isSelectedPosition(position: Int): Boolean {
        return position % 2 == 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(mContext).inflate(R.layout.flow_tag_layout, null)

        val textView = view.findViewById(R.id.tag_widget) as TextView
        val t = mDataList[position]

        if (t is String) {
            textView.text = t
        }
        if (t is Article) {
            val categoryBean = t as Article
            textView.setText(categoryBean.title)
        }
        return view
    }

    override fun getItem(position: Int): T {
        return mDataList.get(position)
     }

    override fun getItemId(position: Int): Long {
      return position.toLong()
    }
    fun onlyAddAll(datas: List<T>) {
        mDataList.addAll(datas)
        notifyDataSetChanged()
    }

    fun clearAndAddAll(datas: List<T>) {
        mDataList.clear()
        onlyAddAll(datas)
    }
    override fun getCount(): Int {
        return mDataList.size
    }
}