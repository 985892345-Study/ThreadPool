package com.ndhzs.threadpool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ndhzs.threadpool.extension.log
import com.ndhzs.threadpool.extension.setButtonOnClickListener
import com.ndhzs.threadpool.extension.showToast
import com.ndhzs.threadpool.thread.pool.CashedThreadPool
import com.ndhzs.threadpool.thread.pool.FixedThreadPool
import com.ndhzs.threadpool.thread.pool.ScheduledThreadPool
import com.ndhzs.threadpool.thread.pool.SingleThreadPool
import kotlin.IllegalStateException
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private val runs =  ArrayList<Runnable>()

    private val singleThreadPool = SingleThreadPool()
    private val fixedThreadPool = FixedThreadPool()
    private val cashedThreadPool = CashedThreadPool()
    private val scheduledThreadPool = ScheduledThreadPool()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for (i in 1..5) {
            runs.add(Runnable {
                log( "==============$i START==============")
                repeat(6) {
                    //加一个耗时的计算
                    for (j in 0..(888888888..999999999).random()) {
                        j.toFloat().pow(j)
                    }
                    log( "Runnable[$i] is running")
                }
                log( "===*===*===*==$i  END ==*===*===*===")
            })
        }
        initSingleThreadPool()
        initFixedThreadPool()
        initCashedThreadPool()
        initScheduledThreadPool()
    }

    private fun initSingleThreadPool() {
        R.id.btn_single_start.setButtonOnClickListener(this) {
            "SingleThreadPool START".showToast(this)
            try {
                runs.forEach{
                    singleThreadPool.execute(it)
                }
            }catch (e: IllegalStateException) {
                "线程池已关闭，任务添加终止，请RESTART".showToast(this)
            }
        }
        R.id.btn_single_shutdown.setButtonOnClickListener(this) {
            "SingleThreadPool END".showToast(this)
            singleThreadPool.shutdownNow()
        }
        R.id.btn_single_restart.setButtonOnClickListener(this) {
            singleThreadPool.reStart()
        }
    }

    private fun initFixedThreadPool() {
        R.id.btn_fixed_start.setButtonOnClickListener(this) {
            "FixedThreadPool START".showToast(this)
            try {
                runs.forEach{
                    fixedThreadPool.execute(it)
                }
            }catch (e: IllegalStateException) {
                "线程池已关闭，任务添加终止，请RESTART".showToast(this)
            }
        }
        R.id.btn_fixed_shutdown.setButtonOnClickListener(this) {
            "FixedThreadPool END".showToast(this)
            fixedThreadPool.shutdownNow()
        }
        R.id.btn_fixed_restart.setButtonOnClickListener(this) {
            fixedThreadPool.reStart()
        }
    }

    private fun initCashedThreadPool() {
        R.id.btn_cashed_start.setButtonOnClickListener(this) {
            "CashedThreadPool START".showToast(this)
            try {
                runs.forEach{
                    cashedThreadPool.execute(it)
                }
            }catch (e: IllegalStateException) {
                "线程池已关闭，任务添加终止，请RESTART".showToast(this)
            }
        }
        R.id.btn_cashed_shutdown.setButtonOnClickListener(this) {
            "CashedThreadPool END".showToast(this)
            cashedThreadPool.shutdownNow()
        }
        R.id.btn_cashed_restart.setButtonOnClickListener(this) {
            cashedThreadPool.reStart()
        }
    }

    private fun initScheduledThreadPool() {
        R.id.btn_schedule_start.setButtonOnClickListener(this) {
            "ScheduledThreadPool START".showToast(this)
            try {
                runs.forEach{
                    scheduledThreadPool.execute(it)
                }
            }catch (e: IllegalStateException) {
                "线程池已关闭，任务添加终止，请RESTART".showToast(this)
            }
        }
        R.id.btn_schedule_shutdown.setButtonOnClickListener(this) {
            "ScheduledThreadPool END".showToast(this)
            scheduledThreadPool.shutdownNow()
        }
        R.id.btn_schedule_restart.setButtonOnClickListener(this) {
            singleThreadPool.reStart()
        }
    }
}