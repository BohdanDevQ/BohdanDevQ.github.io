package com.own.bogdanpremium.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily

/**
 * Font family used for the whole app's typography.
 *
 * On **Web** (Kotlin/Wasm) the Skia canvas has no system fonts, so emojis render blank
 * unless we bundle one. The web `actual` returns a family of Noto Sans (Latin text) +
 * Noto Color Emoji (fallback for emoji glyphs). On **Android/iOS** there's nothing to fix —
 * the system fonts already render emoji — so the `actual` returns `null` and the platform
 * default typography is kept (no font bloat in the native apps).
 */
@Composable
expect fun appFontFamily(): FontFamily?
