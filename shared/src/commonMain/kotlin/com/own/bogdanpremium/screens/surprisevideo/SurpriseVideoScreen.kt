package com.own.bogdanpremium.screens.surprisevideo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.own.bogdanpremium.AppConfig
import com.own.bogdanpremium.Strings
import com.own.bogdanpremium.ui.PrimaryButton
import com.own.bogdanpremium.ui.emojiAware
import com.own.bogdanpremium.video.rememberVideoLauncher

/**
 * Screen 4 — Surprise video. A little personal video with sound, plus a "download"
 * link card underneath.
 *
 * NOTE: the video preview here is a tap-to-play **placeholder** matching the design.
 * Real playback with sound is platform-specific and not available in commonMain — it
 * needs an expect/actual player (Android: Media3/ExoPlayer; iOS: AVPlayer) and the
 * actual video asset. The download card likewise needs the Drive URL + a platform URL
 * opener. Both are wired in a follow-up; see docs/DEVELOPMENT_PLAN.md.
 */
@Composable
fun SurpriseVideoScreen(onNext: () -> Unit) {
    val uriHandler = LocalUriHandler.current
    val playVideo = rememberVideoLauncher(AppConfig.VIDEO_URL)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        VideoPreviewCard(
            onPlay = playVideo,
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = emojiAware(Strings.SurpriseVideo.title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = Strings.SurpriseVideo.subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(24.dp))

        DownloadCard(
            title = Strings.SurpriseVideo.downloadTitle,
            subtitle = Strings.SurpriseVideo.downloadSubtitle,
            onDownload = { uriHandler.openUri(AppConfig.PHOTOS_URL) },
        )

        Spacer(Modifier.height(32.dp))

        PrimaryButton(
            text = Strings.SurpriseVideo.next,
            onClick = onNext,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

/** Dark 16:9 video placeholder with a centered play affordance and a faux scrubber. */
@Composable
private fun VideoPreviewCard(
    onPlay: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.inverseSurface)
            .clickable(onClick = onPlay),
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .size(56.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.9f),
        ) {
            Box(contentAlignment = Alignment.Center) {
                // U+25B6 play triangle — avoids a material-icons dependency.
                Text(
                    text = "▶",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.inverseSurface,
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(3.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.5f)),
            )
            Text(
                text = "0:00",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.inverseOnSurface,
            )
        }
    }
}

/** Light "file" card that looks like a download link (thumbnail + label + arrow). */
@Composable
private fun DownloadCard(
    title: String,
    subtitle: String,
    onDownload: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        onClick = onDownload,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text(emojiAware("📷"), style = MaterialTheme.typography.titleMedium)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                text = emojiAware("⬇️"),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
