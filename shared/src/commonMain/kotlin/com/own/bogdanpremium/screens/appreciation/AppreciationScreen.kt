package com.own.bogdanpremium.screens.appreciation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.own.bogdanpremium.ui.PagerDots
import com.own.bogdanpremium.ui.PrimaryButton
import kotlinx.coroutines.launch

/** Reasons shown on page 2; all must be checked to finish the screen. */
private val reasons = listOf(
    "Bo zawsze cię rozśmieszam",
    "Bo nasze rozmowy o 3 nad ranem",
    "Bo naprawdę cię słucham",
    "Bo jestem szurnięty w uroczy sposób",
)

/** Screen 3 — Appreciation. A 2-page horizontal pager: a heartfelt note, then a gated checklist. */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppreciationScreen(onFinished: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()
    val checks = remember { mutableStateListOf(false, false, false, false) }

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
        PagerDots(
            pageCount = 2,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp),
        )

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> NotePage(onNext = { scope.launch { pagerState.animateScrollToPage(1) } })
                else -> ChecklistPage(
                    checks = checks,
                    onToggle = { index -> checks[index] = !checks[index] },
                    onFinished = onFinished,
                )
            }
        }
    }
}

/** Page 1: the big emoji hero, the heartfelt note, and a button to advance. */
@Composable
private fun NotePage(onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "💬", fontSize = 64.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Sporo ze sobą gadamy. No serio, mega dużo. Nie pamiętam, kiedy " +
                "ostatnio czułem się z kimś tak głęboko połączony. Jesteś świetną osobą " +
                "i doceniam każdą wiadomość, którą wymieniamy.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(40.dp))
        PrimaryButton(text = "Dalej →", onClick = onNext)
    }
}

/** Page 2: the gated checklist — all four reasons must be checked to finish. */
@Composable
private fun ChecklistPage(
    checks: List<Boolean>,
    onToggle: (Int) -> Unit,
    onFinished: () -> Unit,
) {
    val allChecked = checks.all { it }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Jak myślisz, czemu cię lubię?",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(24.dp))

        reasons.forEachIndexed { index, label ->
            ReasonRow(
                label = label,
                checked = checks[index],
                onClick = { onToggle(index) },
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))
        PrimaryButton(text = "Dalej →", onClick = onFinished, enabled = allChecked)

        if (!allChecked) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "zaznacz wszystkie, napracowałem się nad nimi 😤",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

/** A single clickable checklist row that toggles its checked state. */
@Composable
private fun ReasonRow(
    label: String,
    checked: Boolean,
    onClick: () -> Unit,
) {
    val containerColor by animateColorAsState(
        if (checked) MaterialTheme.colorScheme.surfaceVariant
        else MaterialTheme.colorScheme.surface,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(containerColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(checked = checked, onCheckedChange = { onClick() })
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
