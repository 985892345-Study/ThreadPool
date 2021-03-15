package com.ndhzs.threadpool.thread.pool

import android.util.Log
import com.ndhzs.threadpool.thread.myinterface.IThreadPool
import com.ndhzs.threadpool.thread.myrunnable.RunnableQueue
import kotlin.math.log

class SingleThreadPool : IThreadPool {

    private var thread: MyThread
    private val tasks = RunnableQueue()
    init {
        thread = MyThread(tasks)
        thread.start()
    }

    override fun execute(task: Runnable) {
        tasks.add(task)
        if (isShutdown()) {
            Log.d("123", "继续")
            thread = MyThread(tasks)
            thread.start()
        }
    }

    override fun shutdown() {
        tasks.stop()
        thread.interrupt()
    }

    override fun getThreadNum(): Int = 1

    override fun getTaskRemainNum(): Int = tasks.taskRemainNum()

    override fun isShutdown(): Boolean = thread.isInterrupted

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
