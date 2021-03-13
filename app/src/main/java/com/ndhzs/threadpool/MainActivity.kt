package com.ndhzs.threadpool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.ndhzs.threadpool.thread.SingleThreadPool
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "123"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
            for (i in 0 until 10) {
                Log.d(TAG, "onCreate: $i")
                val run = Runnable {
                    Log.d(TAG, "this is $i")
                }
                SingleThreadPool.execute(run)
            }
        }
    }
}