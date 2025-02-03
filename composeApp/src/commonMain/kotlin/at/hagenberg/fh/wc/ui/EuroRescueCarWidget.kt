package at.hagenberg.fh.wc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import at.hagenberg.fh.wc.model.rescue.sheet.dto.EuroRescueCar
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val DOCUMENT_TYPE = "Rescue Sheet"
private const val DOCUMENT_LANGUAGE_DE = "DE"
private const val DOCUMENT_LANGUAGE_EN = "EN"

@Composable
@Preview
fun EuroRescueCarWidget(euroRescueCar: EuroRescueCar) {
    val uriHandler = LocalUriHandler.current
    val url = euroRescueCar.documents.find {
        it.type == DOCUMENT_TYPE &&
                (it.language == DOCUMENT_LANGUAGE_DE || it.language == DOCUMENT_LANGUAGE_EN)
    }?.url

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(8.dp)
            .clickable {
                url?.let { uriHandler.openUri(it) }
            }
    ) {
        Text(
            "${euroRescueCar.makeName} ${euroRescueCar.name}",
            fontWeight = FontWeight.Bold,
        )
        Text("${euroRescueCar.buildYearFrom} - ${euroRescueCar.buildYearUntil ?: ""}")
        Text("${euroRescueCar.bodyType} - ${euroRescueCar.powertrain}")
        Text("${euroRescueCar.doors} Türen")

        if (url == null) {
            Text(
                "Keine Rettungskarte verfügbar",
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colors.error
            )
        }
    }
}