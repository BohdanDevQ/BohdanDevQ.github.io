package com.own.bogdanpremium.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = RoseDeep,
    onPrimary = CreamWhite,
    primaryContainer = RoseBlush,
    onPrimaryContainer = RoseOnContainer,
    secondary = Mauve,
    onSecondary = CreamWhite,
    secondaryContainer = MauveContainer,
    onSecondaryContainer = MauveOnContainer,
    tertiary = Champagne,
    onTertiary = CreamWhite,
    tertiaryContainer = ChampagneContainer,
    onTertiaryContainer = ChampagneOnContainer,
    background = CreamWhite,
    onBackground = Charcoal,
    surface = CreamWhite,
    onSurface = Charcoal,
    outline = OutlineMauve,
)

private val DarkColors = darkColorScheme(
    primary = RoseDark,
    onPrimary = RoseOnContainer,
    primaryContainer = RoseDeep,
    onPrimaryContainer = RoseBlush,
    secondary = MauveLight,
    onSecondary = MauveOnContainer,
    secondaryContainer = Mauve,
    onSecondaryContainer = MauveContainer,
    tertiary = ChampagneLight,
    onTertiary = ChampagneOnContainer,
    tertiaryContainer = Champagne,
    onTertiaryContainer = ChampagneContainer,
    background = CharcoalSurface,
    onBackground = PearlOnDark,
    surface = CharcoalSurface,
    onSurface = PearlOnDark,
    outline = OutlineMauve,
)

/**
 * The app's classy pink theme. Wrap all screen content in [AppTheme] so the
 * rose/mauve/champagne palette and dark-mode support apply consistently.
 */
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content,
    )
}
