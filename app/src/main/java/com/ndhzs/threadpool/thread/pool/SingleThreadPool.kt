package com.ndhzs.threadpool.thread.pool

import com.ndhzs.threadpool.thread.myinterface.IThreadPool
import com.ndhzs.threadpool.thread.myinterface.OnThreadListener
import com.ndhzs.threadpool.thread.myrunnable.MyRunnable

class SingleThreadPool : IThreadPool , MyRunnable.OnThreadStopListener{

    private var thread: Thread
    private var isStop = false
    private var listener: OnThreadListener? = null
    private val tasks = MyRunnable(this)
    init {
        thread = Thread(tasks)
        thread.start()
        listener?.threadChange(1)
    }

    override fun execute(task: Runnable) {
        if (isShutdown()) {
            throw IllegalStateException("The thread pool is destroy")
        }
        tasks.add(task)
    }

    override fun shutdown() {
        isStop = true
    }

    override fun shutdownNow() {
        tasks.stop()
        isStop = true
    }

    override fun isShutdown(): Boolean = isStop

    override fun reStart() {
        if (isShutdown()) {
            isStop = false
            tasks.restart()
            thread = Thread(tasks)
            thread.start()
            listener?.threadChange(1)
        }
    }

    override fun getThreadNum(): Int = 1

    override fun getTaskRemainNum(): Int = tasks.taskRemainNum()

    override fun setThreadListener(l: OnThreadListener) {

    }

    override fun threadStop() {
        listener?.threadChange(0)
    }

    override fun allThreadStop() {
        listener?.threadChange(0)
    }
}
