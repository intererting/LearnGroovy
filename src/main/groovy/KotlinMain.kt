import kotlinx.coroutines.*
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import java.io.InputStream

/*
author        yiliyang
date          2023-02-16
time          上午10:10
version       1.0
since         1.0
*/
val myThreadLocal = ThreadLocal<String?>()

fun main() {
//    testThreadLocal()

//    println(fibo(10))

    /* for (v in fib1().take(10)) {
         println(v)
     }*/

//    for (v in fib2().take(10)) {
//        println(v)
//    }

//    println(stackFun(10))
    myThreadLocal.set("init")

    for (i in 0..100000) {
        threadLocal()
    }
    Thread.sleep(10000)
}

fun threadLocal() {
    GlobalScope.launch {
        withContext(myThreadLocal.asContextElement("changed")) {
        }
        if (null != myThreadLocal.get()) {
            println("error")
        }
    }
}

val stackFun = DeepRecursiveFunction<Long, Long> {
    val result = if (it in 0..1) {
        it
    } else {
        callRecursive(it - 1) + callRecursive(it - 2)
    }
    return@DeepRecursiveFunction result
}

tailrec fun fibo(n: Int, a: Long = 0, b: Long = 1): Long {
    println(a)
    if (n == 0) {
        return a
    }
    return fibo(n - 1, b, a + b)
}

fun fib1() = sequence {
    var a = 0
    var b = 1
    while (true) {
        yield(a.toLong())
        a = b.also {
            b = a + it
        }
    }
}

fun fib2() = sequence {
    val fibList = mutableListOf(0, 1)
    var index = 0
    while (true) {
        if (index in 0..1) {
            yield(index++)
        } else {
            fibList.add(fibList[index - 1] + fibList[index - 2])
            yield(fibList[index++])
        }
    }
}

fun testThreadLocal() {
    GlobalScope.launch(myThreadLocal.asContextElement()) {
        threadLocalCoroutine()
    }
    Thread.sleep(1000)
}

/**
 * threadLocal的修改只在当前环境有效,每次进入协程调用updateThreadContext方法,执行结束调用restoreThreadContext
 * threadLocal必须放在context中 GlobalScope.launch(myThreadLocal.asContextElement()),不要声明在context之外
 */
fun CoroutineScope.threadLocalCoroutine() {
    println(myThreadLocal.get()) // Prints "null"
    launch(Dispatchers.Default + myThreadLocal.asContextElement(value = "foo")) {
        println(Thread.currentThread())
        println("before ${myThreadLocal.get()}") // Prints "foo"
        val job = launch(Dispatchers.Default) {
            println(Thread.currentThread())
            myThreadLocal.set(Thread.currentThread().name)
            println("changed ${myThreadLocal.get()}") // Prints "foo", but it's on UI thread
        }
        job.join()
        println("after change ${myThreadLocal.get()}")
    }
}

/*
inline fun testInline(func: () -> Unit) {
    func()
    println("innine fun")
}
*/

fun testInline(func: () -> Unit) {
    func()
    println("innine fun")
}

fun testInlineBlock() {
    println("before")
    testInline {
        println("inline block")
        return@testInline
    }
    println("after")
}