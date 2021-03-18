package com.ndhzs.threadpool.extension

import java.util.concurrent.atomic.AtomicInteger

operator fun AtomicInteger.plus(i: Int): AtomicInteger {
    set(get() + i)
    return this
}

operator fun AtomicInteger.minus(i: Int): AtomicInteger {
    set(get() - i)
    return this
}

operator fun AtomicInteger.inc(): AtomicInteger {
    set(get() + 1)
    return this
}

operator fun AtomicInteger.dec(): AtomicInteger {
    set(get() - 1)
    return this
}