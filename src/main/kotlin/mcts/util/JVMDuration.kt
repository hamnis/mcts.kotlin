package mcts.util

import java.util.concurrent.TimeUnit

fun Int.millis(): Duration = JVMDuration(this.toLong(), TimeUnit.MILLISECONDS)
fun Int.seconds(): Duration = JVMDuration(this.toLong(), TimeUnit.SECONDS)

class JVMDuration(val size: Long, val unit: TimeUnit): Duration {
    override fun toMillis(): Long = unit.toMillis(size)
}