package com.nizek.helper

import android.content.Context
import android.widget.FrameLayout
import android.widget.LinearLayout


class LayoutHelper {
    companion object {
        const val MATCH_PARENT = -1f
        const val WRAP_CONTENT = -2f

        private fun getSize(context: Context, size: Float): Int {
            return (if (size < 0) size else AndroidUtils.dp(context, size)).toInt()
        }

        fun createFrame(
            context: Context,
            width: Float,
            height: Float,
            gravity: Int
        ): FrameLayout.LayoutParams {
            return FrameLayout.LayoutParams(
                getSize(context, width),
                getSize(context, height),
                gravity
            )
        }

        fun createFrame(
            context: Context,
            width: Float,
            height: Float,
            gravity: Int,
            leftMargin: Float,
            topMargin: Float,
            rightMargin: Float,
            bottomMargin: Float
        ): FrameLayout.LayoutParams {
            val layoutParams =
                FrameLayout.LayoutParams(
                    getSize(context, width),
                    getSize(context, height),
                    gravity
                )
            layoutParams.setMargins(
                AndroidUtils.dp(context, leftMargin).toInt(),
                AndroidUtils.dp(context, topMargin).toInt(),
                AndroidUtils.dp(context, rightMargin).toInt(),
                AndroidUtils.dp(context, bottomMargin).toInt()
            )
            return layoutParams
        }


        fun createLinear(
            context: Context,
            width: Float,
            height: Float,
            gravity: Int,
            leftMargin: Float,
            topMargin: Float,
            rightMargin: Float,
            bottomMargin: Float
        ): LinearLayout.LayoutParams {
            val layoutParams = LinearLayout.LayoutParams(
                getSize(context, width),
                getSize(context, height),
            )
            layoutParams.setMargins(
                AndroidUtils.dp(context, leftMargin).toInt(),
                AndroidUtils.dp(context, topMargin).toInt(),
                AndroidUtils.dp(context, rightMargin).toInt(),
                AndroidUtils.dp(context, bottomMargin).toInt()
            )
            layoutParams.gravity = gravity
            return layoutParams
        }

        fun createLinear(
            context: Context,
            width: Float,
            height: Float,
            weight: Float,
            gravity: Int,
            leftMargin: Float,
            topMargin: Float,
            rightMargin: Float,
            bottomMargin: Float
        ): LinearLayout.LayoutParams {
            val layoutParams = LinearLayout.LayoutParams(
                getSize(context, width),
                getSize(context, height),
                weight
            )
            layoutParams.setMargins(
                AndroidUtils.dp(context, leftMargin).toInt(),
                AndroidUtils.dp(context, topMargin).toInt(),
                AndroidUtils.dp(context, rightMargin).toInt(),
                AndroidUtils.dp(context, bottomMargin).toInt()
            )
            layoutParams.gravity = gravity
            return layoutParams
        }

        fun createLinear(context: Context, width: Int, height: Int): LinearLayout.LayoutParams {
            return LinearLayout.LayoutParams(
                getSize(context, width.toFloat()),
                getSize(context, height.toFloat())
            )
        }


        fun createLinear(
            context: Context,
            width: Float,
            height: Float,
            gravity: Int
        ): LinearLayout.LayoutParams {
            val layoutParams = LinearLayout.LayoutParams(
                getSize(context, width),
                getSize(context, height)
            )
            layoutParams.gravity = gravity
            return layoutParams
        }

        fun createLinear(
            context: Context,
            width: Float,
            height: Float,
            weight: Float,
            gravity: Int
        ): LinearLayout.LayoutParams {
            val layoutParams = LinearLayout.LayoutParams(
                getSize(context, width),
                getSize(context, height), weight
            )
            layoutParams.gravity = gravity
            return layoutParams
        }
    }
}