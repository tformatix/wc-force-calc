package at.hagenberg.fh.wc.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
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
import at.hagenberg.fh.wc.model.rescue.sheet.Powertrain
import at.hagenberg.fh.wc.viewmodel.LicencePlateViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun RescueSheetScreen(
    licencePlateViewModel: LicencePlateViewModel = getViewModel(key = "rescueSheet",
        factory = viewModelFactory { LicencePlateViewModel() })
) {
    var carMake by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedPowertrain by remember { mutableStateOf(Powertrain.GASOLINE) }
    var authority by remember { mutableStateOf("FW") }
    var number by remember { mutableStateOf("KFZ1") }

    val response by licencePlateViewModel.response.collectAsState()
    val powertrainOptions = Powertrain.entries

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "RescueSheetScreen", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = carMake,
            onValueChange = { carMake = it },
            label = { Text("Car Make") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box {
            OutlinedTextField(
                value = selectedPowertrain.name,
                onValueChange = { /* no-op, we handle selection via dropdown */ },
                label = { Text("Powertrain") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                powertrainOptions.forEach { option ->
                    DropdownMenuItem(onClick = {
                        selectedPowertrain = option
                        expanded = false
                    }) {
                        Text(option.name)
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { expanded = true }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                licencePlateViewModel.sendRaw(carMake, selectedPowertrain)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send Raw Request")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the response
        Text(text = response)
    }

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
                licencePlateViewModel.searchLicencePlate(authority, number)
            }
        ) {
            Text("Suchen")
        }
    }
}