package com.ndhzs.threadpool.thread

interface IThreadPool {
    fun execute(task: Runnable)
    fun shutdown()
    fun threadNum(): Int
    fun isShutdown(): Boolean
}