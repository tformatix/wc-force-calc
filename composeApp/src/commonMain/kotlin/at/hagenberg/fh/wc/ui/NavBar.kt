package at.hagenberg.fh.wc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.hagenberg.fh.wc.model.NavBarItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun NavBar(
    navbarItems: List<NavBarItem>,
    selectedNavBarItem: NavBarItem,
    onSelectionChanged: (NavBarItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        navbarItems.forEach { item ->
            NavBarButton(
                title = item.title,
                image = item.image,
                onClick = { onSelectionChanged(item) },
                isSelected = item == selectedNavBarItem,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
@Preview
fun NavBarButton(
    title: String,
    image: ImageVector,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier,
) {
    val color = if (isSelected) Color.White else Color.LightGray

    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = image,
                contentDescription = title,
                tint = color,
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                title,
                color = color,
                fontSize = 12.sp
            )
        }
    }
}