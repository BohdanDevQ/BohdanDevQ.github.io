package com.own.bogdanpremium.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import bogdanpremium.shared.generated.resources.Res
import bogdanpremium.shared.generated.resources.noto_color_emoji
import org.jetbrains.compose.resources.Font

/** Bundled color-emoji font, applied explicitly to emoji glyphs on the Wasm canvas. */
@Composable
actual fun emojiFontFamily(): FontFamily? = FontFamily(Font(Res.font.noto_color_emoji))
