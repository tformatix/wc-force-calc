package at.hagenberg.fh.wc.model

import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

data class Incline(val pitch: Double, val roll: Double)

fun calculateIncline(x: Float, y: Float, z: Float): Incline {
    val pitch = atan2(y, sqrt(x * x + z * z))
    val roll = atan2(x, sqrt(y * y + z * z))

    return Incline(
        pitch = toDegrees(pitch),
        roll = toDegrees(roll)
    )
}

private fun toDegrees(radians: Float): Double {
    return radians * 180.0 / PI
}
