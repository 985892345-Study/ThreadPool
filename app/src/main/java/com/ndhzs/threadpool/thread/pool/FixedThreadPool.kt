package com.ndhzs.threadpool.thread.pool

import com.ndhzs.threadpool.extension.log
import com.ndhzs.threadpool.thread.myinterface.IThreadPool
import com.ndhzs.threadpool.thread.myinterface.OnThreadListener
import com.ndhzs.threadpool.thread.myrunnable.MyRunnable
import com.ndhzs.threadpool.thread.myrunnable.MyThread
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

class FixedThreadPool(private val initialCount: Int = 3, private val maxCount: Int = 10) : IThreadPool {

    private var isShutdown = false
    private val tasks = MyRunnable()
    private var listener: OnThreadListener? = null
    private val threads = LinkedBlockingQueue<MyThread>()
    init {
        initializeThreads(initialCount)
    }

    private fun initializeThreads(newThreadNum: Int) {
        var thread: MyThread
        for (i in 0 until newThreadNum) {
            thread = MyThread(tasks)
            thread.start()
            threads.add(thread)
        }
        listener?.threadChange(newThreadNum + threads.size)
    }

    override fun execute(task: Runnable) {
        if (isShutdown) {
            throw IllegalStateException("The thread pool is destroy")
        }
        tasks.add(task)
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
            log("==============  RESTART  ==============")
            isShutdown = false
            initializeThreads(initialCount)
        }
    }

    override fun getThreadNum(): Int = threads.size
    override fun getTaskRemainNum(): Int = tasks.getRemainNum()
    override fun setThreadListener(l: OnThreadListener) {
        if (listener == null) {
            listener = l
        }
    }

    fun addThread(diffThreadNum: Int) {
        when {
            diffThreadNum <= 0 -> {
                return
            }threads.size + diffThreadNum > maxCount -> {
                initializeThreads(threads.size + diffThreadNum - maxCount)
            }else -> {
                initializeThreads(diffThreadNum)
            }
        }
    }
}