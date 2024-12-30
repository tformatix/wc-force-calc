package at.hagenberg.fh.wc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.hagenberg.fh.wc.viewmodel.InclineViewModel
import at.hagenberg.fh.wc.viewmodel.ResistanceViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    sensor: AccelerometerSensor,
    inclineViewModel: InclineViewModel = getViewModel(
        key = "app",
        factory = viewModelFactory { InclineViewModel() }
    ),
    resistanceViewModel: ResistanceViewModel = getViewModel(
        key = "app",
        factory = viewModelFactory { ResistanceViewModel() }
    )
) {
    val isMeasuring by inclineViewModel.isMeasuring.collectAsState()
    val angle by inclineViewModel.angle.collectAsState()

    MaterialTheme {
        Column(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (isMeasuring) {
                        inclineViewModel.stopMeasuring(sensor)
                    } else {
                        inclineViewModel.startMeasuring(sensor)
                    }
                }
            ) {
                Text(if (isMeasuring) "Stop Measuring" else "Measure the Incline!")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Incline: $angle", style = MaterialTheme.typography.body1)
        }
    }
}