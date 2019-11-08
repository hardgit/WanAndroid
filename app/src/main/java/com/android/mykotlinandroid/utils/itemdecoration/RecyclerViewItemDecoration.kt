package com.android.mykotlinandroid.utils.itemdecoration

import androidx.recyclerview.widget.RecyclerView
import android.graphics.Rect
import android.view.View


/**
 * author : zf
 * date   : 2019/11/7
 * You are the best.
 */
class RecyclerViewItemDecoration : RecyclerView.ItemDecoration() {

    private var space = 0
    private var pos: Int = 0

    fun RecyclerViewItemDecoration(space: Int){
        this.space = space
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        outRect.top = space

        //该View在整个RecyclerView中位置。
        pos = parent.getChildAdapterPosition(view)

        //取模

        //两列的左边一列
        if (pos % 2 == 0) {
            outRect.left = space
            outRect.right = space / 2
        }

        //两列的右边一列
        if (pos % 2 == 1) {
            outRect.left = space / 2
            outRect.right = space
        }
    }

}