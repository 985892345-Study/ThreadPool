package com.ndhzs.threadpool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.ndhzs.threadpool.thread.pool.SingleThreadPool

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "123"
    }

    private val pool = SingleThreadPool()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button1).setOnClickListener{
            for (i in 0 until 10) {
                Log.d(TAG, "onCreate: $i")
                val run = Runnable {
                    Log.d(TAG, "this is $i")
                }
                pool.execute(run)
            }
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            for (i in 0 until 10) {
                Log.d(TAG, "onCreate: $i")
                val run = Runnable {
                    Log.d(TAG, "this is $i")
                }
            }
        }

        findViewById<Button>(R.id.button3).setOnClickListener {
            pool.shutdown()
        }

        findViewById<Button>(R.id.button4).setOnClickListener {

        }
    }
}