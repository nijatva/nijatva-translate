package com.nijatva.translate.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = IceBlue,
    onPrimary = MidnightNavy,
    primaryContainer = IceBlueSoft,
    onPrimaryContainer = PaperWhite,
    secondary = MutedOnDark,
    onSecondary = MidnightNavy,
    background = MidnightNavy,
    onBackground = PaperWhite,
    surface = DeepNavy,
    onSurface = PaperWhite,
    surfaceVariant = SlateSurface,
    onSurfaceVariant = MutedOnDark,
    surfaceContainerHigh = RaisedNavy,
    outline = OutlineDark,
    outlineVariant = SlateSurface,
    error = Color(0xFFFFB4AB),
    onError = MidnightNavy,
)

private val LightColors = lightColorScheme(
    primary = IceBlueDeep,
    onPrimary = PaperWhite,
    primaryContainer = Color(0xFFDCE6FF),
    onPrimaryContainer = Ink,
    secondary = InkMuted,
    onSecondary = PaperWhite,
    background = PaperWhite,
    onBackground = Ink,
    surface = Color(0xFFFFFFFF),
    onSurface = Ink,
    surfaceVariant = Color(0xFFE7ECF5),
    onSurfaceVariant = InkMuted,
    surfaceContainerHigh = Color(0xFFF0F3F9),
    outline = OutlineLight,
    outlineVariant = Color(0xFFD8DFEB),
    error = Color(0xFFB3261E),
    onError = PaperWhite,
)

/**
 * @param darkTheme follows the choice made in the kit's Appearance screen.
 * @param accent the accent picked there, as an ARGB int; 0 keeps this app's own
 *   ice blue. The accent tints the primary role only, so the midnight surfaces stay.
 */
@Composable
fun NijatvaTranslateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    accent: Int = 0,
    content: @Composable () -> Unit,
) {
    val base = if (darkTheme) DarkColors else LightColors
    val scheme = if (accent == 0) {
        base
    } else {
        val picked = Color(accent)
        base.copy(
            primary = picked,
            onPrimary = Color.White,
            primaryContainer = picked.copy(alpha = 0.28f),
            onPrimaryContainer = base.onSurface,
        )
    }

    MaterialTheme(
        colorScheme = scheme,
        typography = NijatvaTypography,
        content = content,
    )
}
