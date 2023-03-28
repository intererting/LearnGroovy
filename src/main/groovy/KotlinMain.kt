import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/*
author        yiliyang
date          2023-02-16
time          上午10:10
version       1.0
since         1.0
*/
val myThreadLocal = ThreadLocal<String?>()

fun main() {
    testThreadLocal()
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

