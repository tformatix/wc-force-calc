package at.hagenberg.fh.wc

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class AndroidAccelerometerSensor : AccelerometerSensor {
    private var context: Context? = null
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var listener: SensorEventListener? = null

    override fun startListening(callback: (x: Float, y: Float, z: Float) -> Unit) {
        context?.let {
            sensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    event?.let {
                        callback(it.values[0], it.values[1], it.values[2])
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }
            accelerometer?.let {
                sensorManager?.registerListener(listener, it, SensorManager.SENSOR_DELAY_NORMAL)
            }
        } ?: run {
            throw IllegalStateException()
        }
    }

    override fun stopListening() {
        listener?.let {
            sensorManager?.unregisterListener(it)
        }
        listener = null
    }

    fun context(context: Context) {
        this.context = context
        Log.i(this.javaClass.name, "Context set.")
    }
}