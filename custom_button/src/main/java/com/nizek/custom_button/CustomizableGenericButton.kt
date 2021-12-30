package com.nizek.custom_button

import android.R
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.nizek.helper.AndroidUtils
import com.nizek.helper.LayoutHelper


/**
 * @author yaya (@yahyalmh)
 * @since 30th December 2021
 */

class CustomizableGenericButton private constructor(context: Context, params: ButtonParams) :
    LinearLayout(context) {
    private var icon: ImageView? = null
    private var iconId: Int = 0

    private var title: String? = null
    private var titleTxtView: TextView? = null

    private var subTitle: String? = null
    private var subTitleTextView: TextView? = null

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        setPadding(
            AndroidUtils.dp(context, 5f).toInt(),
            AndroidUtils.dp(context, 1f).toInt(),
            AndroidUtils.dp(context, 5f).toInt(),
            AndroidUtils.dp(context, 1f).toInt()
        )
        setBorder()

        params.apply(this)
        addIcon()
        addTitleAndSubTitle()

    }

    private fun setBorder() {
        val radius = 5 //radius will be 5px

        val strokeWidth = 2
        val shape = GradientDrawable()
        shape.cornerRadius = radius.toFloat()
        shape.setStroke(strokeWidth, resources.getColor(R.color.black))
        background = shape
    }

    private fun addTitleAndSubTitle() {
        val titleContentView = LinearLayout(context).apply {
            orientation = VERTICAL
            gravity = Gravity.CENTER
            setPadding(
                AndroidUtils.dp(context, 2f).toInt(),
                AndroidUtils.dp(context, 1f).toInt(),
                AndroidUtils.dp(context, 2f).toInt(),
                AndroidUtils.dp(context, 1f).toInt()
            )
        }

        addTitle(context, titleContentView)
        if (!subTitle.isNullOrEmpty()) {
            addSubTitle(context, titleContentView)
        }

        addView(
            titleContentView,
            LayoutHelper.createFrame(
                context,
                LayoutHelper.WRAP_CONTENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER,
                0f,
                0f,
                0f,
                0f
            )
        )
    }

    private fun addTitle(context: Context, titleContentView: LinearLayout) {
        titleTxtView = TextView(context).apply {
            text = title
            setTypeface(null, Typeface.BOLD)
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
        }

        titleContentView.addView(
            titleTxtView,
            LayoutHelper.createFrame(
                context,
                LayoutHelper.WRAP_CONTENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER,
                0f,
                0f,
                0f,
                0f
            )
        )
    }

    private fun addSubTitle(context: Context, titleContentView: LinearLayout) {
        subTitleTextView = TextView(context).apply {
            text = subTitle
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
            isSingleLine = true
        }
        titleContentView.addView(
            subTitleTextView,
            LayoutHelper.createFrame(
                context,
                LayoutHelper.WRAP_CONTENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER,
                0f,
                AndroidUtils.dp(context, 2f),
                0f,
                0f
            )
        )
    }


    private fun addIcon() {
        if (iconId == 0) {
            return
        }

        icon = ImageView(context).apply {
            setImageResource(iconId)
            imageTintList =
                AppCompatResources.getColorStateList(context, android.R.color.holo_green_light)
        }
        addView(
            icon,
            LayoutHelper.createLinear(
                context,
                AndroidUtils.dp(context, 20f),
                AndroidUtils.dp(context, 20f),
                Gravity.CENTER,
                AndroidUtils.dp(context, 2f),
                AndroidUtils.dp(context, 2f),
                AndroidUtils.dp(context, 5f),
                AndroidUtils.dp(context, 2f)
            )
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(
                AndroidUtils.dp(context, 130f).toInt(),
                MeasureSpec.EXACTLY
            ),
            MeasureSpec.makeMeasureSpec(
                AndroidUtils.dp(context, 70f).toInt(),
                MeasureSpec.EXACTLY
            )
        )
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onInterceptHoverEvent(event)
        val animatorSet = AnimatorSet()
        val translationX: ObjectAnimator = ObjectAnimator.ofFloat(this, "scaleX", 0.8f, 1f)
        val translationY: ObjectAnimator = ObjectAnimator.ofFloat(this, "scaleY", 0.8f, 1f)
        animatorSet.playTogether(translationX, translationY)
        animatorSet.duration = 260
        animatorSet.interpolator = AccelerateInterpolator()
        animatorSet.start()
        return super.onTouchEvent(event)
    }


    class Builder(private val context: Context) {
        private val params = ButtonParams()

        fun setIcon(iconId: Int): Builder {
            params.mIconId = iconId
            return this
        }

        fun setTitle(title: String): Builder {
            params.mTitle = title
            return this
        }

        fun setSubTitle(subTitle: String): Builder {
            params.mSubTitle = subTitle
            return this
        }

//        fun setFirstOption(text: String, listener: OptionalDialogClickListener): Builder {
//            params.mFirstOption = text
//            params.mFirstOptionListener = listener
//            return this
//        }

        fun build(): CustomizableGenericButton {
            return CustomizableGenericButton(context, params)
        }
    }

    class ButtonParams {
        var mIconId: Int = 0
        var mTitle: String? = null
        var mSubTitle: String? = null

        fun apply(button: CustomizableGenericButton) {
            button.iconId = mIconId
            button.title = mTitle
            button.subTitle = mSubTitle

//            dialog.firstOptionListener = mFirstOptionListener
        }
    }
}