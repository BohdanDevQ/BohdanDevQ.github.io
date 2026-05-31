package com.own.bogdanpremium.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily

/**
 * An emoji-capable font family, or null when the platform's system fonts already render
 * emoji.
 *
 * On **Web** (Kotlin/Wasm) the Skia canvas has no emoji font and does NOT fall back across
 * a FontFamily's font list, so emoji render as empty boxes. The web `actual` returns a
 * bundled color-emoji family that we apply explicitly to emoji characters (see
 * `ui/EmojiText.kt`). On **Android/iOS** the system already renders emoji, so this is null.
 */
@Composable
expect fun emojiFontFamily(): FontFamily?
