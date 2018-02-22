package javabin.util

fun Int.millis(): Duration = JsDuration(this.toLong())
fun Int.seconds(): Duration = JsDuration(this.toLong() * 1000L)

class JsDuration(val millis: Long): Duration {
    override fun toMillis(): Long = millis
}