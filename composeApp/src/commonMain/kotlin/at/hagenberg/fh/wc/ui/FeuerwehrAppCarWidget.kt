package at.hagenberg.fh.wc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import at.hagenberg.fh.wc.model.rescue.sheet.FeuerwehrAppCar
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun FeuerwehrAppCarWidget(feuerwehrAppCar: FeuerwehrAppCar) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            "${feuerwehrAppCar.make} ${feuerwehrAppCar.model}",
            fontWeight = FontWeight.Bold,
        )
        Text("${feuerwehrAppCar.initialRegistrationDate} - ${feuerwehrAppCar.powertrain}")
        Text("${feuerwehrAppCar.maxTotalMass} kg")
    }
}