package javabin.util

object JVMRandom: Random {
    private val random = java.util.Random()
    override fun nextInt(size: Int): Int = random.nextInt(size)
}
