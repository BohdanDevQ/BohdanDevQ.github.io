package com.own.bogdanpremium.screens.tinder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import bogdanpremium.shared.generated.resources.Res
import bogdanpremium.shared.generated.resources.me
import com.own.bogdanpremium.Strings
import org.jetbrains.compose.resources.painterResource

/**
 * The visual content of a Tinder-style profile card: a gradient "photo"
 * placeholder with a big centered emoji, plus an overlaid name and tagline at
 * the bottom. Drag/gesture handling lives in [TinderScreen]; this file is
 * purely the look of the card surface.
 */
@Composable
fun TinderCard(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 6.dp,
        shadowElevation = 12.dp,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Bogdan's profile photo, cropped to fill the card.
            Image(
                painter = painterResource(Res.drawable.me),
                contentDescription = Strings.Tinder.name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop,
            )

            // Soft scrim + name/tagline pinned to the bottom of the card.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.scrim.copy(alpha = 0f),
                                MaterialTheme.colorScheme.scrim.copy(alpha = 0.55f),
                            ),
                        ),
                    ),
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(20.dp),
            ) {
                Text(
                    text = Strings.Tinder.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                Text(
                    text = Strings.Tinder.tagline,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                )
            }
        }
    }
}
