package com.nizek.challenge

/**
 * @author yaya (@yahyalmh)
 * @since 31th December 2021
 */

import android.util.SparseArray
import java.util.*

class NotificationCenter {

    interface NotificationCenterDelegate {
        fun didReceivedNotification(id: Int, vararg args: Any?)
    }

    companion object {
        private var totalEvents = 1

        val appStarted = totalEvents++
        val appInBackground = totalEvents++
        val appLock = totalEvents++

        private val observers = SparseArray<ArrayList<Any>>()

        @Volatile
        private var Instance: NotificationCenter? = null

        fun getInstance(): NotificationCenter {
            var localInstance = Instance
            if (localInstance == null) {
                synchronized(NotificationCenter::class.java) {
                    localInstance = Instance
                    if (localInstance == null) {
                        localInstance = NotificationCenter()
                        Instance = localInstance
                    }
                }
            }
            return localInstance!!
        }
    }

    fun postNotification(id: Int, vararg args: Any) {
        val objects = observers[id]
        if (objects != null && objects.isNotEmpty()) {
            for (a in objects.indices) {
                val obj = objects[a]
                (obj as NotificationCenterDelegate).didReceivedNotification(id, *args)
            }
        }
    }

    fun addObserver(observer: Any, id: Int) {
        var objects = observers[id]
        if (objects == null) {
            observers.put(id, ArrayList<Any>().also { objects = it })
        }
        if (objects!!.contains(observer)) {
            return
        }
        objects!!.add(observer)
    }

    fun removeObserver(observer: Any, id: Int) {
        val objects = observers[id]
        objects?.remove(observer)
    }
}