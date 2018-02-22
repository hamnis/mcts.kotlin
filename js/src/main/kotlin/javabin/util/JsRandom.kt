package javabin.util

import kotlin.js.Math

object JsRandom : Random {
    override fun nextInt(size: Int): Int = (Math.random() + (size + 1) - 1).toInt()
}
