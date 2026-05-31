package com.own.bogdanpremium.screens.datescience

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.own.bogdanpremium.ui.FunDialog
import com.own.bogdanpremium.ui.PrimaryButton

/** Which section of the progressive reveal is currently the furthest unlocked. */
private enum class Section { INTRO, CHART, WHEEL }

/**
 * Screen 5 — "Date science." A fancy, scrollable screen that reveals itself in three
 * stages. It opens by (sweetly) confirming today is a date; saying "Yes! 🎉" unlocks a
 * tongue-in-cheek "cortisol forecast" bar chart, and tapping through that reveals a
 * fortune wheel of what to expect. Each stage appears with [AnimatedVisibility] and the
 * scroll position eases down to follow along.
 *
 * Reveal state lives in a single [Section] flag; later sections render once the flag has
 * advanced far enough. Pure commonMain Compose — runs on Android + iOS.
 */
@Composable
fun DateScienceScreen(onNext: () -> Unit) {
    var section by remember { mutableStateOf(Section.INTRO) }
    var showSadDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    // Ease the scroll down as new sections unlock.
    LaunchedEffect(section) {
        if (section != Section.INTRO) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    if (showSadDialog) {
        FunDialog(
            title = "💔",
            message = "ouch. ok pretend I never asked… (but it's a date right? 🥺)",
            onDismiss = { showSadDialog = false },
            confirmText = "ok ok 😅",
        )
    }

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
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(8.dp))

            IntroSection(
                onYes = { section = Section.CHART },
                onNo = { showSadDialog = true },
            )

            AnimatedVisibility(
                visible = section == Section.CHART || section == Section.WHEEL,
                enter = fadeIn() + expandVertically(),
            ) {
                ChartSection(
                    // Animate the bars once this section is on screen.
                    animateBars = section == Section.CHART || section == Section.WHEEL,
                    onContinue = { section = Section.WHEEL },
                )
            }

            AnimatedVisibility(
                visible = section == Section.WHEEL,
                enter = fadeIn() + expandVertically(),
            ) {
                WheelSection(onNext = onNext)
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

/** Always-visible opener: confirms (sweetly) that today is a date. */
@Composable
private fun IntroSection(
    onYes: () -> Unit,
    onNo: () -> Unit,
) {
    SectionCard {
        Text(
            text = "😊",
            fontSize = 64.sp,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "If I understand correctly, our meeting today is a date? 😊",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            PrimaryButton(
                text = "Yes! 🎉",
                onClick = onYes,
                modifier = Modifier.weight(1f),
            )
            OutlinedButton(
                onClick = onNo,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(20.dp),
            ) {
                Text(
                    text = "No…",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

/** Second stage: the (very scientific) cortisol forecast chart. */
@Composable
private fun ChartSection(
    animateBars: Boolean,
    onContinue: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(20.dp))
        SectionCard {
            Text(
                text = "Cortisol forecast for today",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(20.dp))
            CortisolChart(animate = animateBars)
            Spacer(Modifier.height(16.dp))
            Text(
                text = "science doesn't lie 🤓",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(20.dp))
            PrimaryButton(
                text = "What to expect →",
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

/** Final stage: the fortune wheel of what to expect from today. */
@Composable
private fun WheelSection(onNext: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(20.dp))
        SectionCard {
            Text(
                text = "What to expect from today",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(20.dp))
            FortuneWheel(
                segments = listOf(
                    "Fun",
                    "A bit of cringe",
                    "Cute moments",
                    "A kiss",
                    "Protein food",
                    "Flowers 🌸",
                ),
            )
            Spacer(Modifier.height(24.dp))
            PrimaryButton(
                text = "Next →",
                onClick = onNext,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

/** Shared rounded surface that every section sits on, for consistent rhythm. */
@Composable
private fun SectionCard(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content,
        )
    }
}
