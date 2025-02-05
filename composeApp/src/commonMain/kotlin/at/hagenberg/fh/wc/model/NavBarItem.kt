package at.hagenberg.fh.wc.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarItem (
    val index: Int,
    val title: String,
    val image: ImageVector,
    val screen: @Composable () -> Unit
)