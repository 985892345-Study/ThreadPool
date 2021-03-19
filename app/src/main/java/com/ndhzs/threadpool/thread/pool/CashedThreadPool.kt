package com.ndhzs.threadpool.thread.pool

import android.util.Log
import com.ndhzs.threadpool.thread.myinterface.IThreadPool
import com.ndhzs.threadpool.thread.myinterface.OnThreadListener
import com.ndhzs.threadpool.thread.myrunnable.MyRunnable
import com.ndhzs.threadpool.thread.myrunnable.MyThread
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class CashedThreadPool(private val initialCount: Int = 0, private val maxCount: Int = 10) : IThreadPool, MyRunnable.OnThreadStopListener {

    private var isShutdown = false
    private val tasks = MyRunnable(this)
    private var listener: OnThreadListener? = null
    private val threads = LinkedBlockingQueue<MyThread>()

    override fun execute(task: Runnable) {
        if (isShutdown) {
            throw IllegalStateException("The thread pool is destroy")
        }
        tasks.add(task)
        if (tasks.getRemainNum() > threads.size) {
            initializeThreads()
        }
    }

    private fun initializeThreads(newThreadNum: Int = 1) {
        var thread: MyThread
        for (i in 0 until newThreadNum) {
            thread = MyThread(tasks, true)
            thread.start()
            threads.add(thread)
        }
        listener?.threadChange(newThreadNum + threads.size)
    }

    override fun shutdown() {
        isShutdown = true
    }

    override fun shutdownNow() {
        threads.forEach{
            it.interrupt()
        }
        threads.clear()
        tasks.clear()
        isShutdown = true
    }

    override fun isShutdown(): Boolean = isShutdown
    override fun reStart() {
        if (isShutdown()) {
            Log.d("123", "==============  RESTART  ==============")
            isShutdown = false
            initializeThreads(initialCount)
        }
    }

    override fun getThreadNum(): Int = threads.size
    override fun getTaskRemainNum(): Int = tasks.getRemainNum()
    override fun setThreadListener(l: OnThreadListener) {
        listener = listener?.let { l }
    }

    override fun threadStop(closedThread: MyThread) {
        threads.remove(closedThread)
    }
}