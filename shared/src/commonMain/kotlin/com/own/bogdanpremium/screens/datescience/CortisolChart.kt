package com.own.bogdanpremium.screens.datescience

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * A tiny, playful "cortisol forecast" bar chart for the date science screen.
 *
 * Two vertical bars sit on a clean baseline: a tall, anxious orange bar
 * ("Before date") and a short, relaxed green bar ("During date"). When [animate]
 * flips to true the bars grow up from the baseline via [animateFloatAsState],
 * so the chart "draws itself" as the section is revealed.
 *
 * commonMain only — pure Compose foundation/material3, runs on Android + iOS.
 */
@Composable
fun CortisolChart(
    animate: Boolean,
    modifier: Modifier = Modifier,
) {
    // Target height fractions of the plot area for each bar.
    val beforeTarget = 0.92f
    val duringTarget = 0.28f

    val beforeFraction by animateFloatAsState(
        targetValue = if (animate) beforeTarget else 0f,
        animationSpec = tween(durationMillis = 900),
        label = "beforeBar",
    )
    val duringFraction by animateFloatAsState(
        targetValue = if (animate) duringTarget else 0f,
        animationSpec = tween(durationMillis = 900),
        label = "duringBar",
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Plot area: two bars anchored to a shared baseline.
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
            )
            ChartBar(
                heightFraction = duringFraction,
                color = CortisolGreen,
            )
        }

        // Minimal baseline axis.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(MaterialTheme.colorScheme.outlineVariant),
        )

        Spacer(Modifier.height(10.dp))

        // Labels under each bar.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            BarLabel(text = "Before date 😰")
            BarLabel(text = "During date 😌")
        }
    }
}

/** A single rounded-top bar that fills [heightFraction] of the plot area. */
@Composable
private fun ChartBar(
    heightFraction: Float,
    color: Color,
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
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 4.dp),
        )
    }
}

// Tasteful, on-vibe data-viz colors (literals are allowed for the chart).
private val CortisolOrange = Color(0xFFEF8A5B)
private val CortisolGreen = Color(0xFF4CAF7D)
