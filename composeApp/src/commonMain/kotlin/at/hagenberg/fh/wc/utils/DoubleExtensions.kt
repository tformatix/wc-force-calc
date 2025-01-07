package at.hagenberg.fh.wc.utils

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.toString(numOfDec: Int): String {
    println(this)
    val integerDigits = this.toInt()
    val floatDigits = ((this - integerDigits) * 10f.pow(numOfDec)).roundToInt()
    return "$integerDigits.${abs(floatDigits)}"
}