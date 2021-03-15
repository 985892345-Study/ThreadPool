package com.ndhzs.threadpool.thread.pool

import android.util.Log
import com.ndhzs.threadpool.thread.myinterface.IThreadPool
import com.ndhzs.threadpool.thread.myrunnable.RunnableQueue

class FixedThreadPool(private var maxThreadNum: Int = 2) : IThreadPool {

    private val tasks = RunnableQueue()
    private val threads = ArrayList<MyThread>()
    init {
        if (maxThreadNum > 10) maxThreadNum = 10
        initializeThreads()
    }

    private fun initializeThreads() {
        var thread: MyThread
        for (i in 0 until maxThreadNum) {
            thread = MyThread(tasks)
            thread.start()
            threads.add(MyThread(tasks))
        }
    }

    override fun execute(task: Runnable) {
        tasks.add(task)
        if (!isShutdown()) {
            initializeThreads()
        }
    }

    override fun shutdown() {
        tasks.stop()
        threads.forEach {
            it.interrupt()
        }
    }

    override fun getThreadNum(): Int = maxThreadNum

    override fun getTaskRemainNum(): Int = tasks.taskRemainNum()

    override fun isShutdown(): Boolean {
        return threads[maxThreadNum - 1].isInterrupted
    }

    fun addThreadNum(threadNum: Int) {
        if (threadNum <= 0 || maxThreadNum > 10)
            return
        var thread: MyThread
        for (i in 0 until threadNum) {
            thread = MyThread(tasks)
            thread.start()
            threads.add(thread)
        }
        this.maxThreadNum += threadNum
    }

    private inner class MyThread(tasks: Runnable) : Thread() {
        override fun run() {
            while (!isInterrupted) {
                try {
                    tasks.run()
                }catch (e: Exception) {
                    Log.d("123", "OVER")
                    break
                }
            }
        }
    }
}