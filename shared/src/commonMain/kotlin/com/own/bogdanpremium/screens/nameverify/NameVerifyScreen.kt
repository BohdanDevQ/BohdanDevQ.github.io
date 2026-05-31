package com.own.bogdanpremium.screens.nameverify

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.own.bogdanpremium.AppConfig
import com.own.bogdanpremium.ui.FunDialog
import com.own.bogdanpremium.ui.PrimaryButton
import com.own.bogdanpremium.ui.StepIndicator

/** The cheeky dialog shown on a wrong guess. */
private const val WRONG_MESSAGE =
    "Haha, you wanted to test me 😂 I'm genuinely doing this for the first time! " +
        "I'm doing it in the middle of the night before our date 🌙"

/**
 * Screen 2 — playful name verification. The user must type [AppConfig.CORRECT_NAME]
 * (case-insensitive). After [AppConfig.MAX_ATTEMPTS] wrong tries the field is
 * auto-filled, but she still presses Continue herself.
 */
@Composable
fun NameVerifyScreen(onContinue: () -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var remaining by rememberSaveable { mutableIntStateOf(AppConfig.MAX_ATTEMPTS) }
    var showDialog by remember { mutableStateOf(false) }

    val autoFilled = remaining <= 0
    val heroScale by animateFloatAsState(
        targetValue = if (autoFilled) 1.1f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "heroScale",
    )

    fun onSubmit() {
        if (autoFilled) {
            onContinue()
            return
        }
        if (name.trim().equals(AppConfig.CORRECT_NAME, ignoreCase = true)) {
            onContinue()
        } else {
            remaining -= 1
            if (remaining <= 0) {
                name = AppConfig.CORRECT_NAME
            } else {
                showDialog = true
            }
        }
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            StepIndicator(current = 1, total = 5)

            Spacer(Modifier.height(32.dp))

            Text(
                text = "🔍",
                fontSize = 64.sp,
                modifier = Modifier.graphicsLayer {
                    scaleX = heroScale
                    scaleY = heroScale
                },
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Please provide your name",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "We take identity very seriously at Bogdan Premium.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Type your name…") },
                placeholder = { Text("Type your name…") },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = attemptsLabel(remaining),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = if (autoFilled) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
            )

            Spacer(Modifier.height(28.dp))

            PrimaryButton(
                text = "Continue",
                onClick = { onSubmit() },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    if (showDialog) {
        FunDialog(
            message = WRONG_MESSAGE,
            onDismiss = { showDialog = false },
            title = "Suuure 🤨",
        )
    }
}

/** Attempts copy that gets more dramatically Gen-Z as tries run out. */
private fun attemptsLabel(remaining: Int): String = when (remaining) {
    3 -> "3 attempts remaining"
    2 -> "2 left, no pressure 😅"
    1 -> "1 attempt. this is your villain origin story 💀"
    else -> "ok I got you 🙄 just press continue ➡️"
}
