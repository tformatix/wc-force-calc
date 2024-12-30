package at.hagenberg.fh.wc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import at.hagenberg.fh.wc.viewmodel.InclineViewModel

class MainActivity : ComponentActivity() {
    private val accelerometerSensor = getAccelerometerSensor() as AndroidAccelerometerSensor
    private val viewModel = InclineViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accelerometerSensor.context(applicationContext)

        setContent {
            App(accelerometerSensor)
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isMeasuring.value) {
            viewModel.startMeasuring(accelerometerSensor)
        }
    }

    override fun onStop() {
        super.onStop()
        if (viewModel.isMeasuring.value) {
            accelerometerSensor.stopListening()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(getAccelerometerSensor())
}