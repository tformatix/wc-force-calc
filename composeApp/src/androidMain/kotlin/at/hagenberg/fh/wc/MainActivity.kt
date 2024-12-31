package at.hagenberg.fh.wc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    private val accelerometerSensor = getAccelerometerSensor() as AndroidAccelerometerSensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accelerometerSensor.context(applicationContext)

        setContent {
            App(accelerometerSensor)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(getAccelerometerSensor())
}