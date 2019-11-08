package com.android.mykotlinandroid.utils.itemdecoration

import androidx.recyclerview.widget.RecyclerView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import com.android.mykotlinandroid.R


/**
 * author : zf
 * date   : 2019/11/7
 * You are the best.
 */
class ItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val mydevider: Int
    private val dividerPaint: Paint


    init {
        dividerPaint = Paint()
        //设置分割线颜色
        dividerPaint.color = Color.parseColor("#66008577")
        //设置分割线宽度
            mydevider = context.resources.getDimensionPixelSize(R.dimen.divider_bottom)

    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mydevider
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        for (i in 0 until childCount - 1) {
            val view = parent.getChildAt(i)
            val top = view.bottom
            val bottom = view.bottom + mydevider
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), dividerPaint)
        }
    }


}