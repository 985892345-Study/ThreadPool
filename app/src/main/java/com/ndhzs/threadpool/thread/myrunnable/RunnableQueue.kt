package com.ndhzs.threadpool.thread.myrunnable

import java.util.concurrent.LinkedBlockingQueue

class RunnableQueue : Runnable {

    private val tasks: LinkedBlockingQueue<Runnable> = LinkedBlockingQueue<Runnable>()
    private var isRunning = true
    private var isStop = false

    override fun run() {
        while (isRunning) {
            tasks.take().run()
        }
        isStop = true
    }

    fun stop() {
        isRunning = false
        tasks.clear()
    }

    fun isStop() : Boolean = isStop

    fun add(task: Runnable) {
        isRunning = true
        tasks.put(task)
    }

    fun taskRemainNum(): Int = tasks.size
}