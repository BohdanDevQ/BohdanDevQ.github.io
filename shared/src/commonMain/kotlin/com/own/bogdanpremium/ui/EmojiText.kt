package com.own.bogdanpremium.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.own.bogdanpremium.theme.emojiFontFamily

/**
 * Returns [text] as an [AnnotatedString] with any emoji code points styled to use the
 * bundled emoji font (on Web). The Skia/Wasm canvas doesn't fall back to an emoji font on
 * its own, so emoji must be styled explicitly. On native [emojiFontFamily] is null and the
 * text is returned unchanged.
 *
 * Routes astral-plane code points (most emoji are surrogate pairs) plus ZWJ, variation
 * selectors, and combining keycaps to the emoji font. Plain BMP symbols (→ ★ ♥ ✕) are left
 * alone so they keep rendering in the normal text font.
 */
@Composable
fun emojiAware(text: String): AnnotatedString {
    val family = emojiFontFamily() ?: return AnnotatedString(text)
    return buildAnnotatedString {
        var i = 0
        while (i < text.length) {
            if (isEmojiChar(text[i])) {
                val start = i
                while (i < text.length && isEmojiChar(text[i])) i++
                withStyle(SpanStyle(fontFamily = family)) {
                    append(text.substring(start, i))
                }
            } else {
                append(text[i])
                i++
            }
        }
    }
}

private fun isEmojiChar(c: Char): Boolean {
    val code = c.code
    return code in 0xD800..0xDFFF || // surrogate halves (astral-plane emoji)
        code == 0x200D || // zero-width joiner (👨‍💻 etc.)
        code in 0xFE00..0xFE0F || // variation selectors
        code == 0x20E3 // combining enclosing keycap
}
