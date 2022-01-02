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
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.nizek.challenge.R
import com.nizek.challenge.model.User
import com.nizek.challenge.UserConfig
import com.nizek.challenge.util.Util
import com.nizek.custom_button.CustomizableGenericButton
import com.nizek.helper.AndroidUtils
import com.nizek.helper.LayoutHelper

/**
 * @author yaya (@yahyalmh)
 * @since 31th December 2021
 */

class SignupFragment : Fragment() {

    private lateinit var fullNameTxtView: EditText
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
        addFullName()

        addUsername()

        addPassword()

        addSignupButton()

        return contentView
    }

    private fun addFullName() {
        fullNameTxtView = EditText(context).apply {
            hint = getString(R.string.fullName)
            setHintTextColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))
            isSingleLine = true
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
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
            fullNameTxtView,
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

    private fun addUsername() {
        usernameTxtView = EditText(context).apply {
            hint = getString(R.string.username)
            setHintTextColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))
            isSingleLine = true
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
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
                AndroidUtils.dp(requireContext(), 5f),
                AndroidUtils.dp(requireContext(), 15f),
                AndroidUtils.dp(requireContext(), 5f)
            )
        )
    }

    private fun addPassword() {
        passwordTxtView = EditText(context).apply {
            hint = getString(R.string.password)
            isSingleLine = true
            setHintTextColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
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

    private fun addSignupButton() {
        val button = CustomizableGenericButton
            .Builder(requireContext())
            .setTitle(getString(R.string.register))
            .build()

        button.setOnClickListener {
            val user = User(
                fullNameTxtView.text.toString(),
                usernameTxtView.text.toString(),
                passwordTxtView.text.toString()
            )
            try {
                if (isValidUser(user)) {
                    UserConfig.getInstance(requireContext()).save(user)
                }
                (activity as MainActivity).showFragment(LoginFragment())
            } catch (e: Exception) {
                val message = e.message ?: getString(R.string.userSavingException)
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


    private fun isValidUser(user: User?): Boolean {
        if (user == null) {
            throw Exception(getString(R.string.userNullWarning))
        }

        if (user.fullName.isNullOrEmpty()) {
            throw Exception(getString(R.string.fillFullName))
        }

        if (user.username.isNullOrEmpty()) {
            throw Exception(getString(R.string.fillUserName))
        }

        if (user.password.isNullOrEmpty()) {
            throw Exception(getString(R.string.fillPassword))
        }

        val passwordLength = 6
        if (user.password.length < passwordLength) {
            throw Exception(getString(R.string.passwordLengthWarning, passwordLength.toString()))
        }

        return true
    }
}