import kotlinx.coroutines.*

/**
 * author        yiliyang
 * date          2023-06-07
 * time          下午10:07
 * version       1.0
 * since         1.0
 */
fun main() {
    val scope1 = CoroutineScope(Dispatchers.IO)
    scope1.launch {
        val scope2 = CoroutineScope(Dispatchers.IO + this.coroutineContext)
        scope2.launch {
            repeat(100) {
                delay(1000)
                println("repeat")
            }
        }
    }
    Thread.sleep(2000)
    scope1.cancel()


    Thread.sleep(10000)
}