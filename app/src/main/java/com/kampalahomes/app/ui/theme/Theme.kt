package com.kampalahomes.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = PrimaryDarkGreen,
    secondary = SecondaryLightGreen,
    tertiary = AccentAmber,
    background = BackgroundOffWhite,
    surface = CardBackground,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

private val DarkColors = darkColorScheme(
    primary = PrimaryDarkGreen,
    secondary = SecondaryLightGreen,
    tertiary = AccentAmber
)

@Composable
fun KampalaHomesTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = AppTypography,
        content = content
    )
}
