package at.hagenberg.fh.wc.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
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
import androidx.compose.ui.unit.dp
import at.hagenberg.fh.wc.viewmodel.RescueSheetViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun RescueSheetScreen(
    rescueSheetViewModel: RescueSheetViewModel = getViewModel(key = "rescueSheet",
        factory = viewModelFactory { RescueSheetViewModel() })
) {
    var authority by remember { mutableStateOf("FW") }
    var number by remember { mutableStateOf("KFZ1") }

    val euroRescueCar by rescueSheetViewModel.euroRescueCar.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = authority,
            onValueChange = {
                authority = it
            },
            label = { Text("Behörde") },
            placeholder = { Text("FW") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = number,
            onValueChange = {
                number = it
            },
            label = { Text("Vormerkzeichen") },
            placeholder = { Text("KFZ1") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                rescueSheetViewModel.findRescueSheet(authority, number)
            }
        ) {
            Text("Suchen")
        }
        Spacer(modifier = Modifier.height(16.dp))
        euroRescueCar?.let {
            Text("Suchergebnis:")
            Text("${it.makeName} - ${it.name}")
            Text("${it.buildYearFrom} - ${it.buildYearUntil}")
            Text("${it.bodyType} - ${it.powertrain}")
            Text("${it.doors} Türen")
        }
    }
}