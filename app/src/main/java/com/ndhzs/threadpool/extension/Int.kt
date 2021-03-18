package com.ndhzs.threadpool.extension

import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.atomic.AtomicInteger

fun Int.setButtonOnClickListener(activity: AppCompatActivity, l: View.OnClickListener) {
    activity.findViewById<Button>(this).setOnClickListener(l)
}

operator fun Int.plus(i: AtomicInteger): Int {
    return this + i.get()
}

operator fun Int.minus(i: AtomicInteger): Int {
    return this - i.get()
}