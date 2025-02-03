package at.hagenberg.fh.wc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import at.hagenberg.fh.wc.model.rescue.sheet.dto.EuroRescueCar
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val DOCUMENT_TYPE = "Rescue Sheet"
private const val DOCUMENT_LANGUAGE_DE = "DE"
private const val DOCUMENT_LANGUAGE_EN = "EN"

@Composable
@Preview
fun EuroRescueCarWidget(euroRescueCar: EuroRescueCar) {
    val uriHandler = LocalUriHandler.current
    val url = euroRescueCar.documents.find {
        it.type == DOCUMENT_TYPE && (it.language == DOCUMENT_LANGUAGE_DE || it.language == DOCUMENT_LANGUAGE_EN)
    }?.url

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().border(
            brush = Brush.linearGradient(listOf(Color.Red, Color.Blue)),
            width = 2.dp,
            shape = RoundedCornerShape(20.dp)
        ).background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .clickable { url?.let(uriHandler::openUri) }.padding(8.dp)
    ) {
        val pictureUrl = euroRescueCar.pictureUrl
        if (pictureUrl.isNullOrBlank()) {
            // Fallback icon
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "No image available",
                tint = Color.Gray,
                modifier = Modifier.size(48.dp)
            )
        } else {
            KamelImage({
                asyncPainterResource(data = pictureUrl)
            },
                contentDescription = "Image of ${euroRescueCar.makeName} ${euroRescueCar.modelName}",
                modifier = Modifier.weight(1f).fillMaxHeight(),
                onLoading = {
                    // Optional: e.g., a small CircularProgressIndicator
                    CircularProgressIndicator()
                },
                onFailure = {
                    // If loading fails, show the same fallback icon
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Failed to load image",
                        tint = Color.Gray,
                        modifier = Modifier.size(48.dp)
                    )
                })
        }

        Column(
            modifier = Modifier.weight(2f).padding(start = 8.dp)
        ) {
            Text(
                text = "${euroRescueCar.makeName} ${euroRescueCar.name}",
                fontWeight = FontWeight.Bold,
            )

            val yearText: String = if (euroRescueCar.buildYearUntil == null) {
                "seit ${euroRescueCar.buildYearFrom}"
            } else {
                "${euroRescueCar.buildYearFrom} - ${euroRescueCar.buildYearUntil}"
            }

            Text(yearText)
            Text("${euroRescueCar.bodyType.german()} - ${euroRescueCar.doors} Türen")
            Text(euroRescueCar.powertrain)

            if (url == null) {
                Text(
                    text = "Keine Rettungskarte verfügbar",
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colors.error
                )
            }
        }
    }
}