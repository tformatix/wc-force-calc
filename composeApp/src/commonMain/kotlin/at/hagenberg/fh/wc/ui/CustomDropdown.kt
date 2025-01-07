package at.hagenberg.fh.wc.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun <T> CustomDropdown(
    title: String,
    values : List<T>,
    value: T,
    onValueChanged: (T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { expanded = true },
        ) {
            Text("$title: $value")
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
        }
        ExposedDropdownMenu(expanded = expanded,
            onDismissRequest = { expanded = false }) {
            values.forEach { valueOption ->
                DropdownMenuItem(
                    onClick = {
                        onValueChanged(valueOption)
                        expanded = false
                    },
                ) {
                    Text(text = valueOption.toString())
                }
            }
        }
    }
}