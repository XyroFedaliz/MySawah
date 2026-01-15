package com.mysawah.predict.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ColorScheme = darkColorScheme(
    primary = PrimaryColor,
    secondary = AccentColor,
    tertiary = SecondaryTextColor,
    onSurface = DarkGrayPanel
)

@Composable
fun MySawahTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}
