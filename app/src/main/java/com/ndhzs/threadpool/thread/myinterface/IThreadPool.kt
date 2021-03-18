package com.ndhzs.threadpool.thread.myinterface

interface IThreadPool {
    fun execute(task: Runnable)
    fun shutdown()
    fun shutdownNow()
    fun isShutdown(): Boolean
    fun reStart()
    fun getThreadNum(): Int
    fun getTaskRemainNum(): Int
    fun setThreadListener(l: OnThreadListener)
}