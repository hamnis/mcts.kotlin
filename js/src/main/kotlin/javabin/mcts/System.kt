package javabin.mcts

object System {
    fun currentTimeMillis(): Long = kotlin.js.Date.now().toLong()
}
