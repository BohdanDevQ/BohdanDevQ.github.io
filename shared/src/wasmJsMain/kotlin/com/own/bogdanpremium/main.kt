package com.own.bogdanpremium

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

/**
 * Web (Kotlin/Wasm) entry point. Mounts the shared Compose [App] into the page body
 * so the exact same UI that runs on Android and iOS runs in the browser.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        App()
    }
}
