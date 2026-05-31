package com.own.bogdanpremium.screens.welcome

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.own.bogdanpremium.Strings
import com.own.bogdanpremium.ui.PillBadge
import com.own.bogdanpremium.ui.PrimaryButton
import com.own.bogdanpremium.ui.emojiAware

/**
 * Screen 1 — Welcome. A fancy, playful landing screen that "selects" the user as
 * a Bogdan Premium candidate: a waving hero emoji, a warm greeting, and a full-width
 * CTA that hands off to [onJumpIn]. Pure commonMain Compose, runs on Android + iOS.
 */
@Composable
fun WelcomeScreen(onJumpIn: () -> Unit) {
    // Gentle infinite wave/wiggle for the hand emoji.
    val waveTransition = rememberInfiniteTransition(label = "wave")
    val waveAngle by waveTransition.animateFloat(
        initialValue = -20f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "waveAngle",
    )

    // Hero scales in from 0.85 on entry for a little polish.
    val heroScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
        label = "heroScale",
    )

    // Press scale-down feedback for the CTA. We track presses on a shared
    // interactionSource that also drives the button's own ripple/state.
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val buttonScale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "buttonScale",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
                    ),
                ),
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(Modifier.weight(1f))

            PillBadge(text = Strings.Welcome.badge)

            Spacer(Modifier.height(32.dp))

            Text(
                text = emojiAware("👋"),
                fontSize = 72.sp,
                modifier = Modifier.graphicsLayer {
                    rotationZ = waveAngle
                    scaleX = heroScale
                    scaleY = heroScale
                },
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = Strings.Welcome.title,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(16.dp))

            val subtitle = buildAnnotatedString {
                append(Strings.Welcome.subtitlePrefix)
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                ) {
                    append(Strings.Welcome.brand)
                }
                append(Strings.Welcome.subtitleSuffix)
            }
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = Strings.Welcome.tag,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary,
                letterSpacing = 0.5.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.weight(1f))

            // Subtle press scale-down: a transparent overlay shares its
            // interactionSource so presses anywhere on the button feed buttonScale,
            // while the PrimaryButton underneath handles the actual click + ripple.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        scaleX = buttonScale
                        scaleY = buttonScale
                    },
            ) {
                PrimaryButton(
                    text = Strings.Welcome.cta,
                    onClick = onJumpIn,
                    modifier = Modifier.fillMaxWidth(),
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = onJumpIn,
                        ),
                )
            }
        }
    }
}
