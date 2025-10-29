package com.floreschumbirayco.mediturn.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BlueMedium,
    onPrimary = Color.White,
    primaryContainer = BluePetrol,
    onPrimaryContainer = Color.White,
    secondary = BlueGrayLight,
    onSecondary = NavyDark,
    tertiary = GrayDarkNeutral,
    background = NavyDark,
    onBackground = Color(0xFFE6E6E6),
    surface = NavyDark,
    onSurface = Color(0xFFE6E6E6)
)

private val LightColorScheme = lightColorScheme(
    primary = BluePetrol,
    onPrimary = Color.White,
    primaryContainer = BlueMedium,
    onPrimaryContainer = Color.White,
    secondary = BlueGrayLight,
    onSecondary = NavyDark,
    tertiary = GrayDarkNeutral,
    background = Color(0xFFF7FAFB),
    onBackground = NavyDark,
    surface = Color.White,
    onSurface = NavyDark
)

@Composable
fun MediturnTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}