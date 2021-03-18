package com.ndhzs.threadpool.thread.myrunnable

import android.util.Log
import java.util.*
import java.util.concurrent.locks.LockSupport
import java.util.concurrent.locks.ReentrantLock

class RunnableQueue {

    private val TAG = "123"

    /*
    * 这里我之前用的LinkedBlockingQueue，但却在线程被阻塞时无法实现shutdown
    * */
    private val tasks = LinkedList<Runnable>()
    @Volatile
    private var waitingThread: Thread? = null
    @Volatile
    private var isStop = false
    private val lock = ReentrantLock(true)

    fun put(task: Runnable) {
        tasks.add(task)
        waitingThread?.let {
            LockSupport.unpark(it)
            waitingThread = null
        }
    }

    /**
     * 由于Synchronized原因被上锁，如果有三个线程同时调用该方法，则这三个线程会排队运行该方法
     * 又由于LockSupport.park()阻塞线程，所以会形成第一个线程一直卡在该方法里，后面排队的线程一直等待
     */
    fun take(): Runnable? {
        val currentThread = Thread.currentThread()
        var task: Runnable? = null
        try {
            lock.lockInterruptibly()
            /*
            * 调用stop方法后被锁线程解锁，然后在下下下面的那个if中return null关闭被锁的线程
            * 再然后由于线程被锁的原因，下一个线程开始运行take()方法，此时再return null关闭线程
            * */
            if (isStop || currentThread.isInterrupted) {
                lock.unlock()
                return null
            }
            if (tasks.isEmpty()) {
                waitingThread = currentThread
                LockSupport.park()
                task = tasks.removeFirst()
            }
            lock.unlock()
        }catch (e: InterruptedException) {
            /*
            * 这里是调用interrupt()后中端线程
            * */
            Log.d(TAG, "INTERRUPT")
            return null
        }
        if (isStop) {//调用stop方法后线程解锁，此时return null关闭被锁线程
            return null
        }
        return task
    }

    fun stop() {
        isStop = true
        tasks.clear()
        waitingThread?.let {
            LockSupport.unpark(it)
        }
    }

    fun restart() {
        isStop = false
    }

    fun size(): Int = tasks.size
}