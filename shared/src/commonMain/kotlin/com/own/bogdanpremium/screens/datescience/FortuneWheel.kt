package com.own.bogdanpremium.screens.datescience

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.own.bogdanpremium.Strings
import com.own.bogdanpremium.ui.emojiAware
import kotlin.math.min

/**
 * A playful "what to expect from today" fortune wheel for the date science screen.
 *
 * A [Canvas] draws one wedge per entry in [segments] in alternating, palette-derived
 * colors, with a center hub reading "Spin!" and a small pointer triangle pinned to the
 * top. On first composition the wheel spins via an [Animatable] (several full turns plus
 * a random-feeling offset, decelerating with [FastOutSlowInEasing]). Drawing curved text
 * on arcs is fiddly across platforms, so the segment names are listed as a compact legend
 * beneath the wheel instead.
 *
 * commonMain only — pure Compose foundation/ui/graphics, runs on Android + iOS.
 */
@Composable
fun FortuneWheel(
    segments: List<String>,
    stopAtIndex: Int,
    modifier: Modifier = Modifier,
) {
    val wedgeColors = wedgePalette()
    val rotation = remember { Animatable(0f) }
    val sweepDeg = 360f / segments.size.coerceAtLeast(1)

    LaunchedEffect(Unit) {
        // Rigged spin: several full turns, decelerating so [stopAtIndex] lands under the
        // top pointer. (The wheel always "happens" to stop on Mortal Kombat. 😈)
        rotation.animateTo(
            targetValue = 360f * 4f - sweepDeg * (stopAtIndex + 0.5f),
            animationSpec = tween(durationMillis = 2600, easing = FastOutSlowInEasing),
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.size(240.dp),
            contentAlignment = Alignment.Center,
        ) {
            // The rotating wheel itself.
            Canvas(
                modifier = Modifier
                    .size(240.dp)
                    .graphicsLayer { rotationZ = rotation.value },
            ) {
                val count = segments.size.coerceAtLeast(1)
                val sweep = 360f / count
                val diameter = min(size.width, size.height)
                val topLeft = Offset(
                    x = (size.width - diameter) / 2f,
                    y = (size.height - diameter) / 2f,
                )
                val arcSize = Size(diameter, diameter)

                // Wedges.
                for (i in 0 until count) {
                    drawArc(
                        color = wedgeColors[i % wedgeColors.size],
                        startAngle = -90f + sweep * i,
                        sweepAngle = sweep,
                        useCenter = true,
                        topLeft = topLeft,
                        size = arcSize,
                    )
                }

                // Center hub circle (background for the "Spin!" label).
                drawCircle(
                    color = hubColor,
                    radius = diameter * 0.18f,
                    center = center,
                )
            }

            // Center hub label — sits outside the rotating layer so it stays upright.
            Text(
                text = Strings.DateScience.wheelSpin,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )

            // Pointer triangle pinned to the top edge, pointing down into the wheel.
            Canvas(
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.TopCenter),
            ) {
                val pointer = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width / 2f, size.height)
                    close()
                }
                drawPath(path = pointer, color = pointerColor)
            }
        }

        Spacer(Modifier.height(20.dp))

        // Legend: two columns of color swatch + label, easier to read than on-arc text.
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            segments.chunked(2).forEach { pair ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    pair.forEachIndexed { localIndex, label ->
                        val globalIndex = segments.indexOf(label)
                        LegendItem(
                            color = wedgeColors[globalIndex % wedgeColors.size],
                            label = label,
                        )
                    }
                }
            }
        }
    }
}

/** A single legend entry: a small color swatch next to the segment label. */
@Composable
private fun LegendItem(
    color: Color,
    label: String,
) {
    Row(
        modifier = Modifier.width(150.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(color),
        )
        Text(
            text = emojiAware(label),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start,
        )
    }
}

/** Alternating, on-vibe wedge colors derived from the rose/champagne palette. */
@Composable
private fun wedgePalette(): List<Color> = listOf(
    MaterialTheme.colorScheme.primary,
    MaterialTheme.colorScheme.secondary,
    MaterialTheme.colorScheme.tertiary,
    MaterialTheme.colorScheme.primaryContainer,
    MaterialTheme.colorScheme.secondaryContainer,
    MaterialTheme.colorScheme.tertiaryContainer,
)

// Dark charcoal for the hub and pointer so they read against the rosy wedges.
private val hubColor = Color(0xFF3A2C30)
private val pointerColor = Color(0xFF3A2C30)
