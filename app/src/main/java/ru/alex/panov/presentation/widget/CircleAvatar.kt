package ru.alex.panov.presentation.widget

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@Composable
fun CircleAvatar(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    NetworkImage(
        url,
        modifier.clip(CircleShape),
        contentDescription
    )
}