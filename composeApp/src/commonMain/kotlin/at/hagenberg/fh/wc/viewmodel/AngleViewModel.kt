package at.hagenberg.fh.wc.viewmodel

import at.hagenberg.fh.wc.AccelerometerSensor
import at.hagenberg.fh.wc.model.Angle
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AngleViewModel : ViewModel() {
    private val _isMeasuring = MutableStateFlow(false)
    val isMeasuring: StateFlow<Boolean> get() = _isMeasuring

    private val _angle = MutableStateFlow<Angle?>(null)
    val angle: StateFlow<Angle?> get() = _angle

    fun startMeasuring(sensor: AccelerometerSensor) {
        _isMeasuring.value = true
        sensor.startListening { x, y, z ->
            _angle.value = Angle.fromAccelerometerValues(x, y, z)
        }
    }

    fun stopMeasuring(sensor: AccelerometerSensor) {
        _isMeasuring.value = false
        sensor.stopListening()
    }

    fun setCustomIncline(incline: Int?) {
        _angle.value = if(incline != null) Angle(incline, incline) else null
    }
}