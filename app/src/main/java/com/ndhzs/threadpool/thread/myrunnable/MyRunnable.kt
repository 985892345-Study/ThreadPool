package com.ndhzs.threadpool.thread.myrunnable

import android.util.Log

class MyRunnable : Runnable {

    private val tasks = RunnableQueue()
    @Volatile
    private var isRunning = true
    @Volatile
    private var isStop = false

    override fun run() {
        while (isRunning) {
            tasks.take()?.run()
        }
        Log.d("123", "OVER")
        isStop = true
    }

    fun stop() {
        isRunning = false
        tasks.stop()
    }

    fun isStop() : Boolean = isStop

    fun restart() {
        isRunning = true
        isStop = false
        tasks.restart()
    }

    fun add(task: Runnable) {
        isRunning = true
        tasks.put(task)
    }

    fun taskRemainNum(): Int = tasks.size()
}