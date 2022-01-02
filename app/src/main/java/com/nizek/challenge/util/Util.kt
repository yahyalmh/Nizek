package com.nizek.challenge.util

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View

/**
 * @author yaya (@yahyalmh)
 * @since 31th December 2021
 */

class Util {
    companion object {

        fun setBorder(
            context: Context,
            view: View,
            radius: Int = 7,
            strokeWidth: Int = 3,
            borderColor: Int = android.R.color.black
        ) {
            val shape = GradientDrawable()
            shape.cornerRadius = radius.toFloat()
            shape.setStroke(strokeWidth, context.resources.getColor(borderColor))
            view.background = shape
        }
    }
}