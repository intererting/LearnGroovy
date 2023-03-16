/**
 * author        yiliyang
 * date          2023-02-16
 * time          上午10:10
 * version       1.0
 * since         1.0
 */
fun main() {
    println("before")
    testInline {
        println("inline block")
        return@testInline
    }
    println("after")
}

/*inline fun testInline(func: () -> Unit) {
    func()
    println("innine fun")
}*/

fun testInline(func: () -> Unit) {
    func()
    println("innine fun")
}