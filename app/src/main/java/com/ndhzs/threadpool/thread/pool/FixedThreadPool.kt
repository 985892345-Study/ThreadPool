package com.ndhzs.threadpool.thread.pool

import android.util.Log
import com.ndhzs.threadpool.thread.myinterface.IThreadPool
import com.ndhzs.threadpool.thread.myrunnable.MyRunnable

class FixedThreadPool(private var maxThreadNum: Int = 3) : IThreadPool {

    private val tasks = MyRunnable()
    private val threads = ArrayList<Thread>()
    init {
        if (maxThreadNum > 10) maxThreadNum = 10
        initializeThreads()
    }

    private fun initializeThreads() {
        var thread: Thread
        for (i in 0 until maxThreadNum) {
            thread = Thread(tasks)
            thread.start()
            threads.add(Thread(tasks))
        }
    }

    override fun execute(task: Runnable) {
        if (isShutdown()) {
            Log.d("123", "==============  RESTART  ==============")
            tasks.restart()
            initializeThreads()
        }
        tasks.add(task)
    }

    override fun shutdown() {
        tasks.stop()
        threads.clear()
    }

    override fun getThreadNum(): Int = maxThreadNum

    override fun getTaskRemainNum(): Int = tasks.taskRemainNum()

    override fun isShutdown(): Boolean {
        return tasks.isStop()
    }

    fun addThreadNum(threadNum: Int) {
        if (threadNum <= 0) {
            return
        }
        if (maxThreadNum + threadNum > 10) {
            maxThreadNum = 10
        }
        var thread: Thread
        for (i in 0 until threadNum) {
            thread = Thread(tasks)
            thread.start()
            threads.add(thread)
        }
        maxThreadNum += threadNum
    }
}