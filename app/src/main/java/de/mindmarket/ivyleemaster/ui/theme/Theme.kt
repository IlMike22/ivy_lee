package de.mindmarket.ivyleemaster.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = IvyLeeLightBlue,
    secondary = IvyLeeLighterBlue,
    tertiary = IvyLeeDarkBlue,
    background = IvyLeeLightGrey,
    surface = IvyLeeGray,
    onPrimary = IvyLeeBlack,
    onSecondary = IvyLeeDarkerGrey,
    onTertiary = IvyLeeBlack,
    onBackground = IvyLeeLightBlue,
    onSurface = IvyLeeWhite,
    error = IvyLeeDarkRed
)

@Composable
fun IvyLeeMasterTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window,view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}