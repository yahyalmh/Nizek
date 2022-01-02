package com.nizek.challenge

import android.app.Application

/**
 * @author yaya (@yahyalmh)
 * @since 31th December 2021
 */

class ApplicationLoader : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionManager()
        UserConfig.getInstance(applicationContext)
    }
}