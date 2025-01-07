package at.hagenberg.fh.wc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import at.hagenberg.fh.wc.model.Surface
import at.hagenberg.fh.wc.utils.toString
import at.hagenberg.fh.wc.viewmodel.AngleViewModel
import at.hagenberg.fh.wc.viewmodel.ResistanceViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun App(
    sensor: AccelerometerSensor,
    angleViewModel: AngleViewModel = getViewModel(key = "app",
        factory = viewModelFactory { AngleViewModel() }),
    resistanceViewModel: ResistanceViewModel = getViewModel(key = "app",
        factory = viewModelFactory { ResistanceViewModel() })
) {
    var weight by remember { mutableStateOf<Int?>(null) }

    var surface by remember { mutableStateOf(Surface.SOLID) }
    var surfaceDropdownExpanded by remember { mutableStateOf(false) }

    val isAngleMeasuring by angleViewModel.isMeasuring.collectAsState()
    val angle by angleViewModel.angle.collectAsState()

    val rollingResistance = resistanceViewModel.getRollingResistance(weight ?: 0, surface)
    val inclineResistance =
        resistanceViewModel.getInclineResidence(weight ?: 0, angle?.incline ?: 0)
    val totalResistance = rollingResistance + inclineResistance

    MaterialTheme {
        Column(
            Modifier.fillMaxWidth().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = weight?.toString() ?: "",
                onValueChange = { weight = it.toIntOrNull() },
                label = { Text("Gewicht (t)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )
            Spacer(modifier = Modifier.height(16.dp))
            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(),
                expanded = surfaceDropdownExpanded,
                onExpandedChange = { surfaceDropdownExpanded = it },
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { surfaceDropdownExpanded = true },
                ) {
                    Text("Untergrund: $surface")
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                ExposedDropdownMenu(expanded = surfaceDropdownExpanded,
                    onDismissRequest = { surfaceDropdownExpanded = false }) {
                    Surface.entries.forEach { surfaceOption ->
                        DropdownMenuItem(onClick = {
                            surface = surfaceOption
                            surfaceDropdownExpanded = false
                        }) {
                            Text(text = surfaceOption.toString())
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = isAngleMeasuring,
                value = angle?.incline?.toString() ?: "",
                onValueChange = {
                    angleViewModel.setCustomIncline(it.toIntOrNull())
                },
                label = { Text("Steigung (°)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                if (isAngleMeasuring) {
                    angleViewModel.stopMeasuring(sensor)
                } else {
                    angleViewModel.startMeasuring(sensor)
                }
            }) {
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
}
