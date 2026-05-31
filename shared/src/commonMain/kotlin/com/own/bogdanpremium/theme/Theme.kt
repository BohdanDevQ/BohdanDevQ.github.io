package com.own.bogdanpremium.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.font.FontFamily

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
    val family = appFontFamily()
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = appTypography(family),
    ) {
        if (family != null) {
            // Cover bare Text() calls (e.g. big emoji heroes) that don't use a
            // typography style, so they get the emoji-capable family too.
            CompositionLocalProvider(
                LocalTextStyle provides LocalTextStyle.current.copy(fontFamily = family),
                content = content,
            )
        } else {
            content()
        }
    }
}

/**
 * Default Material 3 typography, but with every text style switched to [family]
 * when one is supplied (Web, for emoji support). On native [family] is null and the
 * untouched default typography is returned.
 */
@Composable
private fun appTypography(family: FontFamily?): Typography {
    family ?: return Typography()
    val base = Typography()
    return base.copy(
        displayLarge = base.displayLarge.copy(fontFamily = family),
        displayMedium = base.displayMedium.copy(fontFamily = family),
        displaySmall = base.displaySmall.copy(fontFamily = family),
        headlineLarge = base.headlineLarge.copy(fontFamily = family),
        headlineMedium = base.headlineMedium.copy(fontFamily = family),
        headlineSmall = base.headlineSmall.copy(fontFamily = family),
        titleLarge = base.titleLarge.copy(fontFamily = family),
        titleMedium = base.titleMedium.copy(fontFamily = family),
        titleSmall = base.titleSmall.copy(fontFamily = family),
        bodyLarge = base.bodyLarge.copy(fontFamily = family),
        bodyMedium = base.bodyMedium.copy(fontFamily = family),
        bodySmall = base.bodySmall.copy(fontFamily = family),
        labelLarge = base.labelLarge.copy(fontFamily = family),
        labelMedium = base.labelMedium.copy(fontFamily = family),
        labelSmall = base.labelSmall.copy(fontFamily = family),
    )
}
