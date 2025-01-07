package at.hagenberg.fh.wc.model

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.sqrt

data class Angle(val pitch: Int, val roll: Int) {
    companion object {
        fun fromAccelerometerValues(x: Float, y: Float, z: Float): Angle {
            val pitch = abs( atan2(y, sqrt(x * x + z * z)))
            val roll = abs(atan2(x, sqrt(y * y + z * z)))

            return Angle(
                pitch = toDegrees(pitch).toInt(),
                roll = toDegrees(roll).toInt(),
            )
        }

        private fun toDegrees(radians: Float): Double {
            return radians * 180.0 / PI
        }
    }

    val incline: Int get() = max(pitch, roll)

    override fun toString(): String {
        return "Rollen: ${pitch}°, Nicken: ${roll}°"
    }
}