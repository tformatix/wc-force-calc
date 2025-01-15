package at.hagenberg.fh.wc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.hagenberg.fh.wc.ui.RescueSheetScreen
import at.hagenberg.fh.wc.ui.ResistanceScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(sensor: AccelerometerSensor) {
    var currentScreen by remember { mutableStateOf(0) }

    MaterialTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Button(
                    enabled = currentScreen != 0,
                    onClick = {
                        currentScreen = 0
                    },
                ) {
                    Text("Kräfte")
                }
                Spacer(Modifier.width(16.dp))
                Button(
                    enabled = currentScreen != 1,
                    onClick = {
                        currentScreen = 1
                    }
                ) {
                    Text("Rettungsblatt")
                }
            }
            Divider(Modifier.padding(vertical = 16.dp))
            when (currentScreen) {
                0 -> ResistanceScreen(sensor)
                1 -> RescueSheetScreen()
            }
        }
    }
}
