package com.ndhzs.threadpool.thread

import android.util.Log
import java.util.concurrent.LinkedBlockingQueue

object SingleThreadPool : IThreadPool{

    private const val TAG = "123"

    init {
        Log.d(TAG, "init()")
        MyThread().start()
    }

    private val taskQueue = LinkedBlockingQueue<Runnable>()

    override fun execute(task: Runnable) {
        taskQueue.put(task)
    }

    override fun shutdown() {
        while (!taskQueue.isEmpty()) {
            Thread.sleep(20)
        }
    }

    override fun threadNum(): Int = taskQueue.size

    override fun isShutdown(): Boolean = taskQueue.isEmpty()

    class MyThread : Thread() {
        override fun run() {
            while (true) {
                sleep(500)
                taskQueue.take().run()
                Log.d(TAG, "run: ")
            }
        }
    }
}