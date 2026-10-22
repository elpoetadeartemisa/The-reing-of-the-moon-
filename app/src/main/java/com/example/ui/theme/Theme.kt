package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LunarDarkColorScheme = darkColorScheme(
    primary = LunarGold,
    onPrimary = MidnightObsidian,
    primaryContainer = MidnightCardLight,
    onPrimaryContainer = LunarGoldLight,
    secondary = RoseLoveSoft,
    onSecondary = Color.White,
    secondaryContainer = RoseBurgundy,
    onSecondaryContainer = Color.White,
    tertiary = MysticVioletSoft,
    onTertiary = Color.White,
    tertiaryContainer = MysticViolet,
    onTertiaryContainer = Color.White,
    background = MidnightObsidian,
    onBackground = MoonSilver,
    surface = MidnightNavy,
    onSurface = MoonSilver,
    surfaceVariant = MidnightSurface,
    onSurfaceVariant = MoonSilverMuted,
    outline = LunarOutline,
    outlineVariant = LunarCardOutline,
    error = RoseLove,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LunarDarkColorScheme,
        typography = Typography,
        content = content
    )
}
