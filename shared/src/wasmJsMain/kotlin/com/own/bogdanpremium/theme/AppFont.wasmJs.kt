package com.own.bogdanpremium.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import bogdanpremium.shared.generated.resources.Res
import bogdanpremium.shared.generated.resources.noto_color_emoji
import bogdanpremium.shared.generated.resources.noto_sans_regular
import org.jetbrains.compose.resources.Font

/**
 * Web font family: Noto Sans for Latin text, with Noto Color Emoji as the fallback so
 * emoji glyphs actually render on the Skia/Wasm canvas (they're blank otherwise).
 */
@Composable
actual fun appFontFamily(): FontFamily? = FontFamily(
    Font(Res.font.noto_sans_regular),
    Font(Res.font.noto_color_emoji),
)
