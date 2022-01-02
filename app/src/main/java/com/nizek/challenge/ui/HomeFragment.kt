package com.nizek.challenge.ui

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nizek.challenge.Config
import com.nizek.challenge.NotificationCenter
import com.nizek.challenge.R
import com.nizek.custom_button.CustomizableGenericButton
import com.nizek.helper.AndroidUtils
import com.nizek.helper.LayoutHelper

/**
 * @author yaya (@yahyalmh)
 * @since 31th December 2021
 */

class HomeFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        NotificationCenter.getInstance().postNotification(NotificationCenter.appStarted)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fullName = arguments?.getString(Config.FULL_NAME_ARGUMENT_KEY, null)
        val contentView = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
        }
        val welcomeTextView = TextView(context).apply {
            text = getString(R.string.welcomeMessage, fullName)
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER
            setTextColor(resources.getColor(R.color.black))
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f)
            setPadding(
                AndroidUtils.dp(requireContext(), 10f).toInt(),
                AndroidUtils.dp(requireContext(), 10f).toInt(),
                AndroidUtils.dp(requireContext(), 10f).toInt(),
                AndroidUtils.dp(requireContext(), 10f).toInt()
            )
        }

        contentView.addView(
            welcomeTextView,
            LayoutHelper.createLinear(
                requireActivity().applicationContext,
                LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT,
                2f,
                Gravity.START or Gravity.TOP,
                15f,
                0f,
                15f,
                5f
            )
        )

        val button = CustomizableGenericButton
            .Builder(requireContext())
            .setTitle(getString(R.string.logout))
            .build()

        button.setOnClickListener {
            (activity as MainActivity).showFragment(LoginFragment())
        }

        contentView.addView(
            button,
            LayoutHelper.createLinear(
                requireContext(),
                LayoutHelper.WRAP_CONTENT,
                LayoutHelper.WRAP_CONTENT,
                1f,
                Gravity.CENTER or Gravity.BOTTOM,
                0f,
                15f,
                0f,
                2f
            )
        )
        return contentView
    }
}