package com.vodafone.core.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    // Primary Colors
    primary = Blue800,       // blue800 (deep blue)
    onPrimary = White,           // White text/icons

    // Surface Colors
    surface = Blue100,       // blue50 (near-white)
    onSurface = GreyDark,     // Dark gray text
    surfaceVariant = Blue200, // blue100 (subtle tint)
    onSurfaceVariant = GreyMedium, // Medium gray

    // Background
    background = Blue100,    // blue50 (lighter than surface)
)

@Composable
fun WeatherappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}