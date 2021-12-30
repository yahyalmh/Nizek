package com.nizek.challenge

import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nizek.custom_button.CustomizableGenericButton
import com.nizek.helper.AndroidUtils
import com.nizek.helper.LayoutHelper

class MainActivity : AppCompatActivity() {
    private lateinit var contentView: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentView = LinearLayout(this).apply {
            gravity = Gravity.CENTER
            orientation = LinearLayout.VERTICAL
        }
        val button = CustomizableGenericButton
            .Builder(this)
            .setTitle("Title")
            .setSubTitle("Subtitle")
            .setIcon(R.drawable.ic_settings)
            .build()


        contentView.addView(
            button,
            LayoutHelper.createLinear(
                this,
                LayoutHelper.WRAP_CONTENT,
                LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER,
                0f,
                AndroidUtils.dp(this, 7f),
                0f,
                AndroidUtils.dp(this, 2f)
            )
        )
        button.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
        }

        setContentView(contentView)
    }
}