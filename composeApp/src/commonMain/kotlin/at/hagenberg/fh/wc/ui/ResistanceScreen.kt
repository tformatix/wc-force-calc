package at.hagenberg.fh.wc.ui

import at.hagenberg.fh.wc.AccelerometerSensor
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import at.hagenberg.fh.wc.model.resistance.Surface
import at.hagenberg.fh.wc.model.resistance.Vehicle
import at.hagenberg.fh.wc.utils.toString
import at.hagenberg.fh.wc.viewmodel.AngleViewModel
import at.hagenberg.fh.wc.viewmodel.ResistanceViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun ResistanceScreen(
    sensor: AccelerometerSensor,
    angleViewModel: AngleViewModel = getViewModel(key = "resistance",
        factory = viewModelFactory { AngleViewModel() }),
    resistanceViewModel: ResistanceViewModel = getViewModel(key = "resistance",
        factory = viewModelFactory { ResistanceViewModel() })
) {
    var vehicle by remember { mutableStateOf(Vehicle.CAR) }
    var weight by remember { mutableStateOf(vehicle.weight) }
    var surface by remember { mutableStateOf(Surface.SOLID) }

    val isAngleMeasuring by angleViewModel.isMeasuring.collectAsState()
    val angle by angleViewModel.angle.collectAsState()

    val rollingResistance = resistanceViewModel.getRollingResistance(weight ?: 0, surface)
    val inclineResistance =
        resistanceViewModel.getInclineResidence(weight ?: 0, angle?.incline ?: 0)
    val totalResistance = rollingResistance + inclineResistance

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomDropdown(
            title = "Fahrzeug",
            values = Vehicle.entries,
            value = vehicle,
            onValueChanged = {
                vehicle = it
                weight = it.weight
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = weight?.toString() ?: "",
            onValueChange = {
                vehicle = Vehicle.CUSTOM
                weight = it.toIntOrNull()
            },
            label = { Text("Gewicht (kN)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomDropdown(
            title = "Untergrund",
            values = Surface.entries,
            value = surface,
            onValueChanged = { surface = it },
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = isAngleMeasuring,
            value = angle?.incline?.toString() ?: "",
            onValueChange = { angleViewModel.setCustomIncline(it.toIntOrNull()) },
            label = { Text("Steigung (°)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (isAngleMeasuring) {
                    angleViewModel.stopMeasuring(sensor)
                } else {
                    angleViewModel.startMeasuring(sensor)
                }
            },
        ) {
            Text(if (isAngleMeasuring) "Messung stoppen" else "Messung starten")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Winkel: ${angle ?: "N/A"}",
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Rollwiderstand: ${rollingResistance.toString(2)} kN",
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Steigungswiderstand: ${inclineResistance.toString(2)} kN",
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Gesamtwiderstand: ${totalResistance.toString(2)} kN",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h5
        )
    }
}
