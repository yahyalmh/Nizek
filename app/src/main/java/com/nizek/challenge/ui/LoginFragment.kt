package com.nizek.challenge.ui

import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.nizek.challenge.Config
import com.nizek.challenge.R
import com.nizek.challenge.UserConfig
import com.nizek.challenge.util.Util
import com.nizek.custom_button.CustomizableGenericButton
import com.nizek.helper.AndroidUtils
import com.nizek.helper.LayoutHelper

/**
 * @author yaya (@yahyalmh)
 * @since 31th December 2021
 */

class LoginFragment : Fragment() {

    private lateinit var usernameTxtView: EditText
    private lateinit var passwordTxtView: EditText
    private lateinit var contentView: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        contentView = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
        }
        addUsername()

        addPassword()

        addLoginButton()
        return contentView
    }

    private fun addUsername() {
        usernameTxtView = EditText(context).apply {
            hint = getString(R.string.username)
            setHintTextColor(resources.getColor(android.R.color.darker_gray))
            isSingleLine = true
            setTextColor(resources.getColor(R.color.black))
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f)
            setPadding(
                AndroidUtils.dp(requireContext(), 10f).toInt(),
                AndroidUtils.dp(requireContext(), 10f).toInt(),
                AndroidUtils.dp(requireContext(), 10f).toInt(),
                AndroidUtils.dp(requireContext(), 10f).toInt()
            )
            Util.setBorder(requireContext(), this)
        }
        contentView.addView(
            usernameTxtView,
            LayoutHelper.createFrame(
                requireActivity().applicationContext,
                LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER,
                AndroidUtils.dp(requireContext(), 15f),
                0f,
                AndroidUtils.dp(requireContext(), 15f),
                AndroidUtils.dp(requireContext(), 5f)
            )
        )
    }

    private fun addPassword() {
        passwordTxtView = EditText(context).apply {
            hint = getString(R.string.password)
            isSingleLine = true
            setHintTextColor(resources.getColor(android.R.color.darker_gray))
            setTextColor(resources.getColor(R.color.black))
            setTypeface(null, Typeface.BOLD)
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f)
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            setSelection(text.length)
            setPadding(AndroidUtils.dp(requireContext(), 5f).toInt())
            setPadding(
                AndroidUtils.dp(requireContext(), 10f).toInt(),
                AndroidUtils.dp(requireContext(), 10f).toInt(),
                AndroidUtils.dp(requireContext(), 10f).toInt(),
                AndroidUtils.dp(requireContext(), 10f).toInt()
            )
            Util.setBorder(requireContext(), this)
        }


        contentView.addView(
            passwordTxtView,
            LayoutHelper.createFrame(
                requireActivity().applicationContext,
                LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER,
                AndroidUtils.dp(requireContext(), 15f),
                AndroidUtils.dp(requireContext(), 5f),
                AndroidUtils.dp(requireContext(), 15f),
                0f
            )
        )
    }

    private fun addLoginButton() {
        val button = CustomizableGenericButton
            .Builder(requireContext())
            .setTitle(getString(R.string.login))
            .build()

        button.setOnClickListener {
            try {
                if (isUserRegistered(
                        usernameTxtView.text.toString(),
                        passwordTxtView.text.toString()
                    )
                ) {
                    val fragment = HomeFragment()
                    val bundle = Bundle()
                    bundle.putString(
                        Config.FULL_NAME_ARGUMENT_KEY,
                        UserConfig.getInstance(requireContext()).user.fullName
                    )
                    fragment.arguments = bundle

                    (activity as MainActivity).showFragment(fragment)
                }

            } catch (e: Exception) {
                val message = e.message ?: getString(R.string.userLoginException)
                usernameTxtView.text.clear()
                passwordTxtView.text.clear()

                (activity as MainActivity).showDialog(message)
            }
        }


        contentView.addView(
            button,
            LayoutHelper.createLinear(
                requireContext(),
                LayoutHelper.WRAP_CONTENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER,
                0f,
                AndroidUtils.dp(requireContext(), 15f),
                0f,
                AndroidUtils.dp(requireContext(), 2f)
            )
        )
    }

    private fun isUserRegistered(username: String?, password: String?): Boolean {
        val currentUser = UserConfig.getInstance(requireContext()).user
        if (username.isNullOrEmpty()) {
            throw Exception(getString(R.string.fillUserName))
        }
        if (password.isNullOrEmpty()) {
            throw Exception(getString(R.string.fillPassword))
        }

        if (username != currentUser.username || password != currentUser.password) {
            throw Exception(getString(R.string.usernameOrPassWrong))
        }

        return true
    }

}