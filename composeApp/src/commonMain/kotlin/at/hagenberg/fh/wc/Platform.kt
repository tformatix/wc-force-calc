package at.hagenberg.fh.wc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getAccelerometerSensor(): AccelerometerSensor