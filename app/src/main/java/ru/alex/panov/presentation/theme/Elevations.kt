package ru.alex.panov.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Elevations(val card: Dp = 4.dp)

internal val LocalElevations = staticCompositionLocalOf { Elevations() }
