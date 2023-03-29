import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * author        yiliyang
 * date          2023-03-29
 * time          上午11:11
 * version       1.0
 * since         1.0
 */
fun main() {
    val coroutineScope = CoroutineScope(Dispatchers.Default)
    val cHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("cHandler $throwable")
    }
    val newDispatcher = Dispatchers.Default.limitedParallelism(2)
    for (i in 0..10) {
        coroutineScope.launch(newDispatcher) {
            println(Thread.currentThread())
            Thread.sleep(10000)
        }
    }
    Thread.sleep(300000)
}