package com.nizek.challenge

import android.os.CountDownTimer

/**
 * @author yaya (@yahyalmh)
 * @since 31th December 2021
 */

class SessionManager : NotificationCenter.NotificationCenterDelegate {
    private var timer: CountDownTimer? = null
    private val foregroundSessionPeriod = 30 // sec
    private val backgroundSessionPeriod = 10 // sec

    init {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.appStarted)
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.appInBackground)
    }

    private fun startTimer(time: Int) {
        timer?.cancel()

        val oneMilliSec: Long = 1000
        timer = object : CountDownTimer((time * oneMilliSec), oneMilliSec) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                NotificationCenter.getInstance().postNotification(NotificationCenter.appLock)
            }
        }

        (timer as CountDownTimer).start()
    }

    override fun didReceivedNotification(id: Int, vararg args: Any?) {
        when (id) {
            NotificationCenter.appStarted -> {
                startTimer(foregroundSessionPeriod)
            }

            NotificationCenter.appInBackground -> {
                startTimer(backgroundSessionPeriod)
            }
        }
    }
}