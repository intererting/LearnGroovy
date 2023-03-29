import kotlinx.coroutines.*
import kotlin.coroutines.resumeWithException

/**
 * author        yiliyang
 * date          2023-03-29
 * time          上午10:19
 * version       1.0
 * since         1.0
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun main() {
    val coroutineScope = CoroutineScope(Dispatchers.Default)
    val cHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("cHandler $throwable")
    }
    coroutineScope.launch(cHandler) {
//        println(Thread.currentThread())
//        println(coroutineContext[kotlin.coroutines.ContinuationInterceptor])
        val result = suspendCancellableCoroutine<String> { continuation ->
//            continuation.invokeOnCancellation {
//                println("invokeOnCancellation")
//            }
//            println(Thread.currentThread())
//                continuation.cancel(java.lang.RuntimeException("error"))
            launch {
                delay(4000)
                continuation.resume("success") {
                    println("throwable $it")
                }
            }
            println("after resume")
//            Thread.sleep(5000)
        }
        println(result)
    }
    Thread.sleep(100000)
}