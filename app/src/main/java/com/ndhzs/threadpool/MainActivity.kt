package com.ndhzs.threadpool

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.ndhzs.threadpool.thread.pool.CashedThreadPool
import com.ndhzs.threadpool.thread.pool.FixedThreadPool
import com.ndhzs.threadpool.thread.pool.ScheduledThreadPool
import com.ndhzs.threadpool.thread.pool.SingleThreadPool
import kotlin.math.pow

fun String.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

fun Int.setButtonOnClickListener(activity: AppCompatActivity, l: View.OnClickListener) {
    activity.findViewById<Button>(this).setOnClickListener(l)
}

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "123"
    }

    private val singleThreadPool = SingleThreadPool()
    private val fixedThreadPool = FixedThreadPool()
    private val cashedThreadPool = CashedThreadPool()
    private val scheduledThreadPool = ScheduledThreadPool()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val runs = ArrayList<Runnable>()
        for (i in 1..5) {
            runs.add(Runnable {
                Log.d(TAG, "==============$i START==============")
                repeat(6) {
                    //加一个耗时的计算
                    for (j in 0..(888888888..999999999).random()) {
                        j.toFloat().pow(j)
                    }
                    Log.d(TAG, "Runnable[$i] is running")
                }
                Log.d(TAG, "===*===*===*==$i  END ==*===*===*===")
            })
        }

        R.id.btn_single_start.setButtonOnClickListener(this) {
            "SingleThreadPool START".showToast(this)
            runs.forEach{
                singleThreadPool.execute(it)
            }
        }
        R.id.btn_single_shutdown.setButtonOnClickListener(this) {
            "SingleThreadPool END".showToast(this)
            singleThreadPool.shutdown()
        }

        R.id.btn_fixed_start.setButtonOnClickListener(this) {
            "FixedThreadPool START".showToast(this)
            runs.forEach{
                fixedThreadPool.execute(it)
            }
        }
        R.id.btn_fixed_shutdown.setButtonOnClickListener(this) {
            "FixedThreadPool END".showToast(this)
            fixedThreadPool.shutdown()
        }

        R.id.btn_cashed_start.setButtonOnClickListener(this) {
            "CashedThreadPool START".showToast(this)
            runs.forEach{
                cashedThreadPool.execute(it)
            }
        }
        R.id.btn_cashed_shutdown.setButtonOnClickListener(this) {
            "CashedThreadPool END".showToast(this)
            cashedThreadPool.shutdown()
        }

        R.id.btn_schedul_start.setButtonOnClickListener(this) {
            "ScheduledThreadPool START".showToast(this)
            runs.forEach{
                scheduledThreadPool.execute(it)
            }
        }
        R.id.btn_schedul_shutdown.setButtonOnClickListener(this) {
            "ScheduledThreadPool END".showToast(this)
            scheduledThreadPool.shutdown()
        }
    }
}