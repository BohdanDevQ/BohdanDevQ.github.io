package com.own.bogdanpremium.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Temporary step-1 placeholder so the navigation graph can be exercised before
 * the real screens are built. Each screen shows its title and an optional
 * "next" action. This file will shrink/disappear as real screens land.
 */
@Composable
internal fun PlaceholderScreen(
    title: String,
    nextLabel: String? = null,
    onNext: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )
        if (nextLabel != null && onNext != null) {
            Button(
                onClick = onNext,
                modifier = Modifier.padding(top = 24.dp),
            ) {
                Text(nextLabel)
            }
        }
    }
}
