package at.hagenberg.fh.wc.viewmodel

import at.hagenberg.fh.wc.AccelerometerSensor
import at.hagenberg.fh.wc.model.calculateIncline
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InclineViewModel : ViewModel() {
    private val _isMeasuring = MutableStateFlow(false)
    val isMeasuring: StateFlow<Boolean> get() = _isMeasuring

    private val _angle = MutableStateFlow("Not Measuring")
    val angle: StateFlow<String> get() = _angle

    fun startMeasuring(sensor: AccelerometerSensor) {
        _isMeasuring.value = true
        sensor.startListening { x, y, z ->
            val incline = calculateIncline(x, y, z)
            _angle.value = "Pitch: ${incline.pitch.toInt()}°, Roll: ${incline.roll.toInt()}°"
        }
    }

    fun stopMeasuring(sensor: AccelerometerSensor) {
        _isMeasuring.value = false
        sensor.stopListening()
        _angle.value = "Not Measuring"
    }
}