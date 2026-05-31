package com.own.bogdanpremium.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily

/** Android renders emoji with the system font — no override needed. */
@Composable
actual fun emojiFontFamily(): FontFamily? = null
