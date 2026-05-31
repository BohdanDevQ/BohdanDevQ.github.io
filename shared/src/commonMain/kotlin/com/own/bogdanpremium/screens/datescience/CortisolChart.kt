package com.own.bogdanpremium.screens.datescience

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.own.bogdanpremium.Strings
import com.own.bogdanpremium.ui.emojiAware

/**
 * A tiny, playful "cortisol forecast" bar chart for the date science screen.
 *
 * Two vertical bars sit on a clean baseline: a tall, anxious orange bar ("Przed randką")
 * and a short, relaxed green bar ("W trakcie"). The bars **grow up from the baseline**
 * the first time the chart appears — the growth is self-triggered (via a [LaunchedEffect]
 * that flips an internal flag) so it always animates from zero, no matter how the parent
 * reveals it. The anxious orange bar then keeps a subtle continuous "shiver" so it reads
 * as stressed, while the calm green bar stays still.
 *
 * commonMain only — pure Compose foundation/material3, runs on Android + iOS + Web.
 */
@Composable
fun CortisolChart(
    animate: Boolean,
    modifier: Modifier = Modifier,
) {
    // Grow from zero once the chart is on screen, regardless of initial [animate] value.
    var grown by remember { mutableStateOf(false) }
    LaunchedEffect(animate) {
        if (animate) grown = true
    }

    val beforeTarget = 0.92f
    val duringTarget = 0.28f

    val growSpec = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow,
    )
    val beforeFraction by animateFloatAsState(
        targetValue = if (grown) beforeTarget else 0f,
        animationSpec = growSpec,
        label = "beforeBar",
    )
    val duringFraction by animateFloatAsState(
        targetValue = if (grown) duringTarget else 0f,
        animationSpec = growSpec,
        label = "duringBar",
    )

    // Continuous "anxious" shiver applied to the tall bar (visual scale, never overflows).
    val pulse = rememberInfiniteTransition(label = "anxiety")
    val anxiousScale by pulse.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 170, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "shiver",
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom,
        ) {
            ChartBar(
                heightFraction = beforeFraction,
                color = CortisolOrange,
                scaleY = if (grown) anxiousScale else 1f,
            )
            ChartBar(
                heightFraction = duringFraction,
                color = CortisolGreen,
                scaleY = 1f,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(MaterialTheme.colorScheme.outlineVariant),
        )

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            BarLabel(text = Strings.DateScience.chartBarBefore)
            BarLabel(text = Strings.DateScience.chartBarDuring)
        }
    }
}

/** A single rounded-top bar that fills [heightFraction] of the plot area. */
@Composable
private fun ChartBar(
    heightFraction: Float,
    color: Color,
    scaleY: Float,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(64.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(heightFraction)
                .graphicsLayer {
                    this.scaleY = scaleY
                    transformOrigin = TransformOrigin(0.5f, 1f)
                }
                .background(
                    color = color,
                    shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp),
                ),
        )
    }
}

/** Caption shown beneath a bar, sized to roughly match the bar column. */
@Composable
private fun BarLabel(text: String) {
    Box(
        modifier = Modifier.width(96.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = emojiAware(text),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

// Tasteful, on-vibe data-viz colors (literals are allowed for the chart).
private val CortisolOrange = Color(0xFFEF8A5B)
private val CortisolGreen = Color(0xFF4CAF7D)
