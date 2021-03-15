package com.ndhzs.threadpool.thread.pool

import com.ndhzs.threadpool.thread.myinterface.IThreadPool
import com.ndhzs.threadpool.thread.myrunnable.RunnableQueue

class SingleThreadPool : IThreadPool {

    private val tasks = RunnableQueue()
    private val thread = MyThread(tasks)

    override fun execute(task: Runnable) {
        if (!isShutdown()) {
            thread.start()
        }
        tasks.add(task)
    }

    override fun shutdown() {
        tasks.stop()
    }

    override fun getThreadNum(): Int = 1

    override fun getTaskRemainNum(): Int = tasks.taskRemainNum()

    override fun isShutdown(): Boolean = !thread.isRunning

    inner class MyThread(tasks: Runnable) : Thread() {

        var isRunning = true

        override fun run() {
            tasks.run()
            isRunning = false
        }
    }
}