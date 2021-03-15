package com.ndhzs.threadpool.thread.myrunnable

import java.util.concurrent.LinkedBlockingQueue

class RunnableQueue : Runnable {

    private val tasks: LinkedBlockingQueue<Runnable> = LinkedBlockingQueue<Runnable>()
    private var isRunning = true
    private var isShutDown = false

    override fun run() {
        while (isRunning) {
            tasks.take().run()
        }
        isShutDown = true
    }

    fun stop() {
        this.isRunning = false
    }

    fun isStop() : Boolean = isShutDown

    fun add(task: Runnable) {
        tasks.put(task)
    }

    fun taskRemainNum(): Int = tasks.size
}