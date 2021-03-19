package com.ndhzs.threadpool.thread.myrunnable

import com.ndhzs.threadpool.extension.log
import java.util.concurrent.LinkedBlockingQueue

class MyRunnable(private val listener: OnThreadStopListener? = null) : Runnable {

    private val tasks = LinkedBlockingQueue<Runnable>()

    override fun run() {
        val currentThread = Thread.currentThread() as MyThread
        while (true) {
            try {
                currentThread.startTimer()
                val task = tasks.take()
                currentThread.interruptTimer()
                task.run()
            }catch (e: InterruptedException) {
                listener?.threadStop(currentThread)
                break
            }
        }
    }

    fun add(task: Runnable) {
        tasks.offer(task)
    }

    fun clear() {
        tasks.clear()
    }

    fun getRemainNum(): Int = tasks.size

    interface OnThreadStopListener {
        fun threadStop(closedThread: MyThread)
    }
}