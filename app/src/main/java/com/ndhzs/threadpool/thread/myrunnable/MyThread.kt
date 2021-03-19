package com.ndhzs.threadpool.thread.myrunnable

import java.util.*
import kotlin.concurrent.schedule

class MyThread(private val target: Runnable, private val isAutoOff: Boolean = false) : Thread(target) {

    private var timer: Timer? = null

    override fun run() {
        target.run()
    }

    fun startTimer() {
        if (isAutoOff) {
            if (timer == null) {
                timer = Timer()
            }
            timer?.schedule(60 * 1000) {
                interrupt()
            }
        }
    }

    fun interruptTimer() {
        if (isAutoOff) {
            timer?.cancel()
        }
    }
}