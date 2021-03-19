package com.ndhzs.threadpool.thread.pool

import android.util.Log
import com.ndhzs.threadpool.extension.log
import com.ndhzs.threadpool.thread.myinterface.IThreadPool
import com.ndhzs.threadpool.thread.myinterface.OnThreadListener
import com.ndhzs.threadpool.thread.myrunnable.MyRunnable
import com.ndhzs.threadpool.thread.myrunnable.MyThread

class SingleThreadPool : IThreadPool {

    private var thread: MyThread
    private var isShutdown = false
    private var listener: OnThreadListener? = null
    private val tasks = MyRunnable()
    init {
        thread = MyThread(tasks)
        thread.start()
        log("========== < Thread[1] START > ==========")
        listener?.threadChange(1)
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
        thread.interrupt()
        tasks.clear()
        isShutdown = true
    }

    override fun isShutdown(): Boolean = isShutdown
    override fun reStart() {
        if (isShutdown()) {
            isShutdown = false
            thread = MyThread(tasks)
            thread.start()
            listener?.threadChange(1)
        }
    }

    override fun getThreadNum(): Int = 1
    override fun getTaskRemainNum(): Int = tasks.getRemainNum()
    override fun setThreadListener(l: OnThreadListener) {
        if (listener == null) {
            listener = l
        }
    }
}
