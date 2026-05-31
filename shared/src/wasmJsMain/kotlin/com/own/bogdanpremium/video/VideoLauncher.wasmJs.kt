package com.own.bogdanpremium.video

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.browser.document
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLVideoElement

/** Web: play the video in a fullscreen overlay (native controls + sound), in-page. */
@Composable
actual fun rememberVideoLauncher(url: String): () -> Unit =
    remember(url) { { openFullscreenVideo(url) } }

private fun openFullscreenVideo(url: String) {
    val body = document.body ?: return

    val backdrop = document.createElement("div") as HTMLElement
    backdrop.setAttribute(
        "style",
        "position:fixed;inset:0;background:rgba(0,0,0,0.92);z-index:2147483647;" +
            "display:flex;align-items:center;justify-content:center;",
    )

    val video = document.createElement("video") as HTMLVideoElement
    video.src = url
    video.controls = true
    video.autoplay = true
    video.setAttribute("playsinline", "true")
    video.setAttribute("style", "max-width:92%;max-height:92%;border-radius:12px;")

    val closeBtn = document.createElement("div") as HTMLElement
    closeBtn.textContent = "✕"
    closeBtn.setAttribute(
        "style",
        "position:fixed;top:14px;right:20px;color:white;font-size:30px;cursor:pointer;" +
            "font-family:sans-serif;line-height:1;",
    )

    fun close() {
        video.pause()
        body.removeChild(backdrop)
    }

    // Click on the backdrop (or the close button, which bubbles up) closes; clicks on the
    // video itself stop here so its controls keep working.
    backdrop.addEventListener("click") { close() }
    video.addEventListener("click") { event -> event.stopPropagation() }

    backdrop.appendChild(video)
    backdrop.appendChild(closeBtn)
    body.appendChild(backdrop)
}
