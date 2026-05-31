package com.own.bogdanpremium.screens.subscribe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.own.bogdanpremium.ui.PrimaryButton

/**
 * Screen 6 — Subscribe (terminal screen).
 *
 * A fancy, celebratory finale: confetti gently falls behind the content, and tapping
 * "Subscribe" fires a confetti burst, flips the button to a disabled "Subscribed 💅"
 * state, and reveals a sarcastic data-harvesting callout for some Gen-Z humor.
 */
@Composable
fun SubscribeScreen() {
    var subscribed by remember { mutableStateOf(false) }

    val scheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        scheme.surface,
                        scheme.surfaceVariant.copy(alpha = 0.6f),
                        scheme.surface,
                    ),
                ),
            ),
    ) {
        // Falling confetti overlay — bursts when the user subscribes.
        Confetti(
            burst = subscribed,
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "🎉",
                fontSize = 64.sp,
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Dziękuję, że tu dotarłaś!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = scheme.onSurface,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = buildAnnotatedString {
                    append("Kliknij przycisk poniżej, żeby oficjalnie zasubskrybować ")
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = scheme.primary,
                        ),
                    ) {
                        append("Bogdan Premium")
                    }
                    append(".")
                },
                style = MaterialTheme.typography.bodyLarge,
                color = scheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(32.dp))

            PrimaryButton(
                text = if (subscribed) "Zasubskrybowano 💅" else "Subskrybuj ✨",
                onClick = { subscribed = true },
                enabled = !subscribed,
            )

            AnimatedVisibility(visible = subscribed) {
                Text(
                    text = "(zwrotów brak)",
                    style = MaterialTheme.typography.labelSmall,
                    color = scheme.onSurfaceVariant.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 8.dp),
                )
            }

            Spacer(Modifier.height(24.dp))

            AnimatedVisibility(
                visible = subscribed,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
            ) {
                SarcasticCallout()
            }
        }
    }
}

/** A tinted, rounded callout box delivering the sarcastic "I collect your data" punchline. */
@Composable
private fun SarcasticCallout() {
    val scheme = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .widthIn(max = 420.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(scheme.primaryContainer.copy(alpha = 0.45f))
            .border(
                width = 2.dp,
                color = scheme.primary.copy(alpha = 0.6f),
                shape = RoundedCornerShape(16.dp),
            )
            .padding(20.dp),
    ) {
        Text(
            text = "Oczywiście, że zbieram wszystkie dane z Twoich wyborów!!!! " +
                "Tak łatwo mi nie uciekniesz 😈",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = scheme.onPrimaryContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
