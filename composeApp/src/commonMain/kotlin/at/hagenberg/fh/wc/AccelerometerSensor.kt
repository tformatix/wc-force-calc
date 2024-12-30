package at.hagenberg.fh.wc

interface AccelerometerSensor {
    fun startListening(callback: (x: Float, y: Float, z: Float) -> Unit)
    fun stopListening()
}