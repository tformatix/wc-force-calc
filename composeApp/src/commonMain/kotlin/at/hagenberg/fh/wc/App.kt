package at.hagenberg.fh.wc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import at.hagenberg.fh.wc.model.NavBarItem
import at.hagenberg.fh.wc.ui.NavBar
import at.hagenberg.fh.wc.ui.RescueSheetScreen
import at.hagenberg.fh.wc.ui.ResistanceScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(sensor: AccelerometerSensor) {
    val navBarItems = listOf(
        NavBarItem(0, "Rettungsblatt", Icons.Default.Search) @Composable { RescueSheetScreen() },
        NavBarItem(1, "Widerstand", Icons.Default.Build) @Composable { ResistanceScreen(sensor) }
    )
    var selectedNavBarItem by remember { mutableStateOf(navBarItems.first()) }

    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            primary = Color(0xFF1A237E),
            primaryVariant = Color(0xFF1a607e),
            secondary = Color(0xFF7e1a1a),
            secondaryVariant = Color(0xFF7e491a),
            onPrimary = Color.White,
            onSecondary = Color.White
        )
    ) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary),
                ) {
                    Text(
                        text = selectedNavBarItem.title,
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = MaterialTheme.typography.h6.fontWeight,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            },
            bottomBar = {
                NavBar(
                    navbarItems = navBarItems,
                    selectedNavBarItem = selectedNavBarItem,
                    onSelectionChanged = { selectedNavBarItem = it }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(32.dp)
            ) {
                selectedNavBarItem.screen()
            }
        }
    }
}
