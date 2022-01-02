package com.nizek.challenge

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.nizek.challenge.model.User

/**
 * @author yaya (@yahyalmh)
 * @since 31th December 2021
 */

class UserConfig private constructor(context: Context) {
    var user: User
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        @Volatile
        private var Instance: UserConfig? = null
        fun getInstance(context: Context): UserConfig {
            var localInstance = Instance
            if (localInstance == null) {
                synchronized(UserConfig::class.java) {
                    localInstance = Instance
                    if (localInstance == null) {
                        localInstance = UserConfig(context)
                        Instance = localInstance
                    }
                }
            }
            return localInstance!!
        }
    }

    init {
        val fullName = sharedPreferences.getString(Config.FULL_NAME_PREF_KEY, null)
        val username = sharedPreferences.getString(Config.USERNAME_PREF_KEY, null)
        val password = sharedPreferences.getString(Config.PASSWORD_PREF_KEY, null)
        user = User(fullName, username, password)
    }

    fun isRegistered(): Boolean {
        return !user.fullName.isNullOrEmpty() && !user.username.isNullOrEmpty() && !user.password.isNullOrEmpty()
    }

    fun save(user: User) {
        sharedPreferences.edit {
            this.putString(Config.FULL_NAME_PREF_KEY, user.fullName)
            this.putString(Config.USERNAME_PREF_KEY, user.username)
            this.putString(Config.PASSWORD_PREF_KEY, user.password)
            commit()
        }
    }
}