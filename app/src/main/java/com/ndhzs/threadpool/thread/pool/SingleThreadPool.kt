package com.ndhzs.threadpool.thread.pool

import android.util.Log
import com.ndhzs.threadpool.thread.myinterface.IThreadPool
import com.ndhzs.threadpool.thread.myrunnable.MyRunnable

class SingleThreadPool : IThreadPool {

    private var thread: Thread
    private val tasks = MyRunnable()
    init {
        thread = Thread(tasks)
        thread.start()
    }

    override fun execute(task: Runnable) {
        if (isShutdown()) {
            Log.d("123", "==============  RESTART  ==============")
            tasks.restart()
            thread = Thread(tasks)
            thread.start()
        }
        tasks.add(task)
    }

    override fun shutdown() {
        tasks.stop()
    }

    override fun getThreadNum(): Int = 1

    override fun getTaskRemainNum(): Int = tasks.taskRemainNum()

    override fun isShutdown(): Boolean = tasks.isStop()
}
