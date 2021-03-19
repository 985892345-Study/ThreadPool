package com.ndhzs.threadpool.thread.pool

import com.ndhzs.threadpool.thread.myinterface.IThreadPool
import com.ndhzs.threadpool.thread.myinterface.OnThreadListener

class ScheduledThreadPool : IThreadPool {
    override fun execute(task: Runnable) {
        TODO("Not yet implemented")
    }

    override fun shutdown() {
        TODO("Not yet implemented")
    }

    override fun shutdownNow() {
        TODO("Not yet implemented")
    }

    override fun isShutdown(): Boolean {
        TODO("Not yet implemented")
    }

    override fun reStart() {
        TODO("Not yet implemented")
    }

    override fun getThreadNum(): Int {
        TODO("Not yet implemented")
    }

    override fun getTaskRemainNum(): Int {
        TODO("Not yet implemented")
    }

    override fun setThreadListener(l: OnThreadListener) {
        TODO("Not yet implemented")
    }
}