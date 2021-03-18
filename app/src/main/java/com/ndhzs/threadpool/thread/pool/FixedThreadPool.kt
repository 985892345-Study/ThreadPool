package com.ndhzs.threadpool.thread.pool

import android.util.Log
import com.ndhzs.threadpool.extension.dec
import com.ndhzs.threadpool.extension.plus
import com.ndhzs.threadpool.thread.myinterface.IThreadPool
import com.ndhzs.threadpool.thread.myinterface.OnThreadListener
import com.ndhzs.threadpool.thread.myrunnable.MyRunnable
import java.util.concurrent.atomic.AtomicInteger

class FixedThreadPool(maxThreadNum: Int = 3) : IThreadPool , MyRunnable.OnThreadStopListener{

    private var threadNum: AtomicInteger = when {
        maxThreadNum > 10 -> AtomicInteger(10)
        maxThreadNum <= 0 -> AtomicInteger(3)
        else -> AtomicInteger(maxThreadNum)
    }
    private var isStop = false
    private val tasks = MyRunnable(this)
    private var listener: OnThreadListener? = null
    private val threads = ArrayList<Thread>()
    init {
        initializeThreads()
    }

    private fun initializeThreads() {
        var thread: Thread
        for (i in 0 until threadNum.get()) {
            thread = Thread(tasks)
            thread.start()
            threads.add(Thread(tasks))

        }
        listener?.threadChange(threadNum.get())
    }

    override fun execute(task: Runnable) {
        if (isStop) {
            throw IllegalStateException("The thread pool is destroy")
        }
        tasks.add(task)
    }

    override fun shutdown() {
        isStop = true
    }

    override fun shutdownNow() {
        tasks.stop()
        threads.clear()
        isStop = true
    }

    override fun isShutdown(): Boolean = isStop

    override fun reStart() {
        if (isStop) {
            Log.d("123", "==============  RESTART  ==============")
            isStop = false
            tasks.restart()
            initializeThreads()
        }
    }

    override fun getThreadNum(): Int = threadNum.get()

    override fun getTaskRemainNum(): Int = tasks.taskRemainNum()

    override fun setThreadListener(l: OnThreadListener) {
        listener = listener?.let { l }
    }

    fun addThread(diffThreadNum: Int) {
        if (diffThreadNum <= 0) {
            return
        }
        if (threadNum.get() + diffThreadNum > 10) {
            threadNum.set(10)
        }
        var thread: Thread
        for (i in 0 until diffThreadNum) {
            thread = Thread(tasks)
            thread.start()
            threads.add(thread)
        }
        this.threadNum += diffThreadNum
    }

    fun removeThread() {
    }

    override fun threadStop() {
        threadNum--
        listener?.threadChange(threadNum.get())
    }

    override fun allThreadStop() {
        listener?.threadChange(0)
    }
}