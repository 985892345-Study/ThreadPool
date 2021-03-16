package com.ndhzs.threadpool.thread.pool

import com.ndhzs.threadpool.thread.myinterface.IThreadPool

class ScheduledThreadPool : IThreadPool{
    override fun execute(task: Runnable) {
        TODO("Not yet implemented")
    }

    override fun shutdown() {
        TODO("Not yet implemented")
    }

    override fun getThreadNum(): Int {
        TODO("Not yet implemented")
    }

    override fun getTaskRemainNum(): Int {
        TODO("Not yet implemented")
    }

    override fun isShutdown(): Boolean {
        TODO("Not yet implemented")
    }
}