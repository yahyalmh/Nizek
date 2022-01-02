package com.nizek.challenge.ui.component

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.nizek.challenge.R
import com.nizek.helper.AndroidUtils
import com.nizek.helper.LayoutHelper
import java.util.*


open class CustomAlertDialog(context: Context) : AlertDialog(context) {

    private var withCancelBtn: Boolean = false
    private var optionCount: Int = 0
    private var iconId: Int = 0
    private var message: String? = null
    private var hint: String? = null

    private lateinit var firstOption: String
    private lateinit var firstOptionListener: OptionalDialogClickListener

    var secondOption: String? = null
    var secondOptionListener: OptionalDialogClickListener? = null

    var thirdOption: String? = null
    private var thirdOptionListener: OptionalDialogClickListener? = null

    private lateinit var dialogContentView: LinearLayout
    private lateinit var contentView: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initContentView()

        initDialogContentView()

        addIcon()

        addMessage()

        addHint()

        drawLine(length = 5f, colorId = R.color.white)
        drawLine()

        when (optionCount) {
            1 -> addOption(firstOption, firstOptionListener)
            2 -> addTwoOptions()
            else -> {
                addOption(firstOption, firstOptionListener)
                drawLine()

                addOption(secondOption, secondOptionListener)
                drawLine()

                addOption(thirdOption, thirdOptionListener)
            }
        }

        if (optionCount == 3) {
            // Locate the dialog at the bottom
            window!!.setGravity(Gravity.BOTTOM)
        }

        if (withCancelBtn) {
            addCancelButton()
        }

        // set window background transparent
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun initContentView() {
        contentView = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
        }

        addContentView(
            contentView, LayoutHelper.createLinear(
                context,
                LayoutHelper.MATCH_PARENT,
                LayoutHelper.MATCH_PARENT,
                Gravity.CENTER,
                AndroidUtils.dp(context, 10f),
                0f,
                AndroidUtils.dp(context, 10f),
                0f

            )
        )
    }

    private fun initDialogContentView() {
        dialogContentView = LinearLayout(context).apply {
            val shape = GradientDrawable()
            shape.cornerRadius = AndroidUtils.dp(context, 40f)
            shape.shape = GradientDrawable.RECTANGLE
            shape.color = AppCompatResources.getColorStateList(context, R.color.white)
            background = shape
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
        }

        contentView.addView(
            dialogContentView, LayoutHelper.createLinear(
                context,
                LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER
            )
        )
    }

    private fun addIcon() {
        if (iconId == 0) {
            return
        }

        val icon = ImageView(context).apply {
            setImageResource(iconId)
            imageTintList = AppCompatResources.getColorStateList(context, R.color.purple_700)
        }

        dialogContentView.addView(
            icon,
            LayoutHelper.createLinear(
                context,
                AndroidUtils.dp(context, 20f),
                AndroidUtils.dp(context, 20f),
                Gravity.CENTER,
                0f,
                AndroidUtils.dp(context, 7f),
                0f,
                AndroidUtils.dp(context, 2f)
            )
        )
    }

    private fun addMessage() {
        if (message.isNullOrEmpty()) {
            return
        }
        val messageTxtView = TextView(context).apply {
            setTextColor(ContextCompat.getColor(context, R.color.black))
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f)
            maxLines = 3
            isSingleLine = false
            pivotX = 0f
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            text = message
        }

        dialogContentView.addView(
            messageTxtView,
            LayoutHelper.createLinear(
                context,
                LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER,
                AndroidUtils.dp(context, 7f),
                AndroidUtils.dp(context, 4f),
                AndroidUtils.dp(context, 7f),
                AndroidUtils.dp(context, 1f)
            )
        )
    }

    private fun addHint() {
        if (hint.isNullOrEmpty()) {
            return
        }

        val hintTextView = TextView(context).apply {
            setTextColor(ContextCompat.getColor(context, R.color.black))
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
            maxLines = 3
            isSingleLine = false
            pivotX = 0f
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            text = hint
        }

        dialogContentView.addView(
            hintTextView,
            LayoutHelper.createLinear(
                context,
                LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER,
                AndroidUtils.dp(context, 10f),
                AndroidUtils.dp(context, 1f),
                AndroidUtils.dp(context, 10f),
                AndroidUtils.dp(context, 2f)
            )
        )
    }

    private fun addOption(optionText: String?, listener: OptionalDialogClickListener?) {
        if (optionText.isNullOrEmpty()) {
            return
        }
        val option = TextView(context).apply {
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f)
            maxLines = 1
            isSingleLine = true
            pivotX = 0f
            gravity = Gravity.CENTER
            isClickable = true
            val outValue = TypedValue()
            context.theme.resolveAttribute(
                android.R.attr.selectableItemBackground, outValue, true
            )
            setBackgroundResource(outValue.resourceId)
            setTextColor(ContextCompat.getColor(context, R.color.purple_500))
            setPadding(
                0,
                AndroidUtils.dp(context, 18f).toInt(),
                0,
                AndroidUtils.dp(context, 18f).toInt()
            )
            text = optionText
        }

        val linearView = LayoutHelper.createLinear(
            context,
            LayoutHelper.MATCH_PARENT,
            LayoutHelper.WRAP_CONTENT,
            Gravity.CENTER
        )

        option.setOnClickListener { listener?.onClick(this) }
        dialogContentView.addView(
            option,
            linearView
        )
    }

    private fun addTwoOptions() {
        val options = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        val outValue = TypedValue()
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackground, outValue, true
        )

        val firstOptionTxtView = TextView(context).apply {
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f)
            maxLines = 1
            isSingleLine = true
            pivotX = 0f
            gravity = Gravity.CENTER
            isClickable = true
            setBackgroundResource(outValue.resourceId)
            setTextColor(ContextCompat.getColor(context, R.color.purple_700))
            setPadding(
                0,
                AndroidUtils.dp(context, 18f).toInt(),
                0,
                AndroidUtils.dp(context, 18f).toInt()
            )
            text = firstOption
            setOnClickListener { firstOptionListener.onClick(this@CustomAlertDialog) }
        }

        val linearView = LayoutHelper.createLinear(
            context,
            LayoutHelper.MATCH_PARENT,
            LayoutHelper.WRAP_CONTENT,
            1f,
            Gravity.CENTER
        )

        options.addView(
            firstOptionTxtView,
            linearView
        )

        drawLine(view = options, orientation = LinearLayout.VERTICAL)

        val secondOptionTxtView = TextView(context).apply {
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f)
            maxLines = 1
            isSingleLine = true
            pivotX = 0f
            gravity = Gravity.CENTER
            isClickable = true
            setBackgroundResource(outValue.resourceId)
            setTextColor(ContextCompat.getColor(context, R.color.purple_700))
            setPadding(
                0,
                AndroidUtils.dp(context, 18f).toInt(),
                0,
                AndroidUtils.dp(context, 18f).toInt()
            )
            setOnClickListener { secondOptionListener?.onClick(this@CustomAlertDialog) }
            text = secondOption
        }

        options.addView(
            secondOptionTxtView,
            linearView
        )

        dialogContentView.addView(
            options, LayoutHelper.createLinear(
                context,
                LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER,
            )
        )
    }

    private fun addCancelButton() {
        val parentLayout = LinearLayout(context).apply {
            val shape = GradientDrawable()
            shape.cornerRadius = AndroidUtils.dp(context, 15f)
            shape.shape = GradientDrawable.RECTANGLE
            shape.color = AppCompatResources.getColorStateList(context, R.color.white)
            background = shape
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
        }


        val cancelTxtView = TextView(context).apply {
            setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
            isSingleLine = true
            pivotX = 0f
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            setPadding(
                0,
                AndroidUtils.dp(context, 16f).toInt(), 0, AndroidUtils.dp(context, 16f).toInt()
            )
            text = context.getString(R.string.cancel, Locale.US)
        }

        parentLayout.addView(
            cancelTxtView,
            LayoutHelper.createLinear(
                context,
                LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER
            )
        )

        contentView.addView(
            parentLayout,
            LayoutHelper.createLinear(
                context,
                LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER,
                AndroidUtils.dp(context, 0f),
                AndroidUtils.dp(context, 4f),
                AndroidUtils.dp(context, 0f),
                AndroidUtils.dp(context, 4f)
            )
        )
        parentLayout.setOnClickListener {
            dismiss()
        }
    }

    private fun drawLine(
        view: LinearLayout = dialogContentView,
        orientation: Int = LinearLayout.HORIZONTAL,
        length: Float = .3f,
        colorId: Int = android.R.color.darker_gray
    ) {
        val line = LinearLayout(context)
        line.setBackgroundColor(ContextCompat.getColor(context, colorId))
        val width: Float
        val height: Float
        when (orientation) {
            LinearLayout.HORIZONTAL -> {
                width = LayoutHelper.MATCH_PARENT
                height = AndroidUtils.dp(context, length)
            }
            else -> {
                width = AndroidUtils.dp(context, length)
                height = LayoutHelper.MATCH_PARENT
            }
        }
        view.addView(
            line,
            LayoutHelper.createLinear(
                context,
                width,
                height,
                Gravity.CENTER
            )
        )
    }

    class Builder(private val context: Context) {
        private val params = DialogParams()

        fun setIcon(iconId: Int): Builder {
            params.mIconId = iconId
            return this
        }

        fun setMessage(message: String): Builder {
            params.mMessage = message
            return this
        }

        fun setHint(hint: String): Builder {
            params.mHint = hint
            return this
        }

        fun setFirstOption(text: String, listener: OptionalDialogClickListener): Builder {
            params.mFirstOption = text
            params.mFirstOptionListener = listener
            return this
        }

        fun setSecondOption(text: String, listener: OptionalDialogClickListener): Builder {
            params.mSecondOption = text
            params.mSecondOptionListener = listener
            return this
        }

        fun setThirdOption(text: String, listener: OptionalDialogClickListener?): Builder {
            params.mThirdOption = text
            params.mThirdOptionListener = listener!!
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            params.mCancelable = cancelable
            return this
        }

        fun withCancelBtn(): Builder {
            params.withCancelBtn = true
            return this
        }

        fun create(): CustomAlertDialog {
            val dialog = CustomAlertDialog(context)
            params.apply(dialog)
            return dialog
        }

        fun show(): CustomAlertDialog {
            val dialog: CustomAlertDialog = create()
            dialog.show()
            return dialog
        }

    }

    class DialogParams {
        var mCancelable = true
        var withCancelBtn = false

        var mIconId: Int = 0
        var mMessage: String? = null
        var mHint: String? = null

        lateinit var mFirstOption: String
        lateinit var mFirstOptionListener: OptionalDialogClickListener

        var mSecondOption: String? = null
        var mSecondOptionListener: OptionalDialogClickListener? = null

        var mThirdOption: String? = null
        var mThirdOptionListener: OptionalDialogClickListener? = null


        fun apply(dialog: CustomAlertDialog) {
            dialog.iconId = mIconId
            dialog.message = mMessage
            dialog.hint = mHint

            dialog.firstOption = mFirstOption
            dialog.firstOptionListener = mFirstOptionListener

            dialog.secondOption = mSecondOption
            dialog.secondOptionListener = mSecondOptionListener

            dialog.thirdOption = mThirdOption
            dialog.thirdOptionListener = mThirdOptionListener
            dialog.withCancelBtn = withCancelBtn


            dialog.setCancelable(mCancelable)
            var optionCount = 3
            if (mFirstOption.isEmpty()) {
                optionCount -= 1
            }
            if (mSecondOption.isNullOrEmpty()) {
                optionCount -= 1
            }
            if (mThirdOption.isNullOrEmpty()) {
                optionCount -= 1
            }
            dialog.optionCount = optionCount

        }
    }

    interface OptionalDialogClickListener {
        fun onClick(dialog: CustomAlertDialog)
    }
}