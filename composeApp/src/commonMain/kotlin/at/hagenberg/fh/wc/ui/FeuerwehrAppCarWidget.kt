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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import at.hagenberg.fh.wc.model.rescue.sheet.FeuerwehrAppCar
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun FeuerwehrAppCarWidget(feuerwehrAppCar: FeuerwehrAppCar) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().background(
            color = Color.White, shape = RoundedCornerShape(20.dp)
        ).drawBehind {
            val borderSize = 2.dp.toPx()
            val cornerRadius = 20.dp.toPx()
            val dashWidth = 10.dp.toPx()
            val dashGap = 8.dp.toPx()

            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap), 0f)

            drawRoundRect(
                color = Color.LightGray, // Border color
                size = size, cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                    cornerRadius, cornerRadius
                ), style = Stroke(
                    width = borderSize,
                    pathEffect = pathEffect,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }.padding(8.dp)
    ) {
        Text(
            "${feuerwehrAppCar.make} ${feuerwehrAppCar.model}",
            fontWeight = FontWeight.Bold,
        )
        Text("${feuerwehrAppCar.initialRegistrationDate} - ${feuerwehrAppCar.powertrain}")
        Text("${feuerwehrAppCar.maxTotalMass} kg")
    }
}