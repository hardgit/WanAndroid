package com.android.mykotlinandroid.utils

import android.content.Context
import android.widget.Toast

class ToastUtil {
    companion object {
        fun show(context: Context?, content: String?) {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
        }

        fun show(context: Context, content: Int) {
            Toast.makeText(context, context.getString(content), Toast.LENGTH_SHORT).show()
        }
    }
}