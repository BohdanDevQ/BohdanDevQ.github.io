package com.own.bogdanpremium.video

import androidx.compose.runtime.Composable

/**
 * Returns a callback that plays the video at [url].
 *
 * On **Web** it opens a fullscreen `<video>` overlay (with native controls + sound) right
 * inside the page. On **Android/iOS** it opens the URL in the system browser/player for now
 * (native in-app players can be added later).
 */
@Composable
expect fun rememberVideoLauncher(url: String): () -> Unit
