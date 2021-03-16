package com.ndhzs.threadpool.thread.pool

import com.ndhzs.threadpool.thread.myinterface.IThreadPool
import com.ndhzs.threadpool.thread.myrunnable.MyRunnable
import java.util.*

class CashedThreadPool(private var maxThreadNum: Int = 0) : IThreadPool {

    private val tasks = MyRunnable()
    private val threads = LinkedList<MyThread>()

    override fun execute(task: Runnable) {
        tasks.add(task)
        if (threads.size == 0) {
            threads.add(MyThread(tasks))
        }
    }

    override fun shutdown() {
        TODO("Not yet implemented")
    }

    override fun getThreadNum(): Int = maxThreadNum

    override fun getTaskRemainNum(): Int = tasks.taskRemainNum()

    override fun isShutdown(): Boolean {
        TODO("Not yet implemented")
    }

    private inner class MyThread(tasks: Runnable) : Thread() {
        var isRunning = true
        override fun run() {
            tasks.run()
            isRunning = false
        }
    }
}