package com.nizek.helper

import android.content.Context
import kotlin.math.ceil


class AndroidUtils {

    companion object {
        fun dp(context: Context, value: Float): Float {
            val density = context.resources.displayMetrics.density;

            return if (value == 0f) {
                0f
            } else ceil(density * value)
        }
    }
}