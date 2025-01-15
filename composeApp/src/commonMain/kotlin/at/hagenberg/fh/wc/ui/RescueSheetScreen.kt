package at.hagenberg.fh.wc.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
    Text("RescueSheetScreen")
}