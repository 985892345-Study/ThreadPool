package com.ndhzs.threadpool.thread.pool

import com.ndhzs.threadpool.thread.myinterface.IThreadPool
import com.ndhzs.threadpool.thread.myrunnable.RunnableQueue

class FixedThreadPool(maxThreadNum: Int) : IThreadPool {

    private val tasks = RunnableQueue()
    private val threads = ArrayList<MyThread>()
    private var maxThreadNum = maxThreadNum

    override fun execute(task: Runnable) {
        if (!isShutdown()) {
            for (i in 1..maxThreadNum)
                threads.add(MyThread(tasks))
        }
        tasks.add(task)
    }

    override fun shutdown() {
        tasks.stop()
    }

    override fun getThreadNum(): Int = maxThreadNum

    override fun getTaskRemainNum(): Int = tasks.taskRemainNum()

    override fun isShutdown(): Boolean {
        for (i in 0 until maxThreadNum) {
            if (threads[i].isRunning)
                return false
        }
        return true
    }

    fun addThreadNum(threadNum: Int) {
        if (threadNum <= 0)
            return
        for (i in 0 until threadNum)
            threads.add(MyThread(tasks))
        this.maxThreadNum += threadNum
    }

    inner class MyThread(tasks: Runnable) : Thread() {

        var isRunning = true

        override fun run() {
            tasks.run()
            isRunning = false
        }
    }
}