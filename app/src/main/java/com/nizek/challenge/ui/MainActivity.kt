package com.nizek.challenge.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nizek.challenge.NotificationCenter
import com.nizek.challenge.R
import com.nizek.challenge.UserConfig
import com.nizek.challenge.ui.component.CustomAlertDialog


class MainActivity : AppCompatActivity(), NotificationCenter.NotificationCenterDelegate {
    private lateinit var contentView: FrameLayout
    private lateinit var currentFragment: Fragment
    private val contentViewId = 14378

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.appLock)
        super.onCreate(savedInstanceState)

        contentView = FrameLayout(this).apply {
            id = contentViewId
        }

        if (UserConfig.getInstance(this).isRegistered()) {
            showFragment(LoginFragment())
        } else {
            showFragment(SignupFragment())
        }

        setContentView(contentView)
    }

    fun showDialog(message: String) {
        val dialog = CustomAlertDialog.Builder(this).setIcon(R.drawable.ic_warning)
            .setMessage(message).setFirstOption(getString(R.string.ok),
                object : CustomAlertDialog.OptionalDialogClickListener {
                    override fun onClick(dialog: CustomAlertDialog) {
                        dialog.dismiss()
                    }
                })
        dialog.show()
    }


    fun showFragment(fragment: Fragment) {
        currentFragment = fragment
        val manager = supportFragmentManager
        val beginTransaction = manager.beginTransaction()

        beginTransaction.replace(contentView.id, fragment)
        beginTransaction.commitAllowingStateLoss()
    }

    override fun didReceivedNotification(id: Int, vararg args: Any?) {
        if (id == NotificationCenter.appLock) {
            if (currentFragment !is LoginFragment) {
                showFragment(LoginFragment())
            }
        }
    }

    override fun onStop() {
        super.onStop()
        NotificationCenter.getInstance().postNotification(NotificationCenter.appInBackground)
    }

    override fun onDestroy() {
        super.onDestroy()
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.appLock)
    }
}