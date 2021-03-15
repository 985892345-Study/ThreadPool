package com.ndhzs.threadpool.thread.myinterface

interface IThreadPool {
    fun execute(task: Runnable)
    fun shutdown()
    fun getThreadNum(): Int
    fun getTaskRemainNum(): Int
    fun isShutdown(): Boolean
}