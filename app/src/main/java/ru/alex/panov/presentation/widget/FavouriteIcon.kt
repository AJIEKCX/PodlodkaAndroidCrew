package ru.alex.panov.presentation.widget

import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FavouriteIcon(
    isFavourite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconToggleButton(
        checked = isFavourite,
        onCheckedChange = { onClick() },
        modifier = modifier
    ) {
        Icon(
            if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = null,
            tint = if (isFavourite) Color.Red else Color.Gray
        )
    }
}