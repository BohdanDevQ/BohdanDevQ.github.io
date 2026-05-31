package com.own.bogdanpremium.video

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalUriHandler

/** Android: open the video URL in the system player/browser. */
@Composable
actual fun rememberVideoLauncher(url: String): () -> Unit {
    val uriHandler = LocalUriHandler.current
    return remember(url, uriHandler) { { uriHandler.openUri(url) } }
}
