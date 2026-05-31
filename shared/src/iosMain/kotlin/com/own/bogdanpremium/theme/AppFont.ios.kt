package com.own.bogdanpremium.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily

/** iOS already renders emoji with the system font — keep the default typography. */
@Composable
actual fun appFontFamily(): FontFamily? = null
