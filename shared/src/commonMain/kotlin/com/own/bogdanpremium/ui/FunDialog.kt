package com.own.bogdanpremium.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

/**
 * Styled confirmation dialog for the app's playful popups (e.g. Screen 4's
 * "Do you mean it??" and Screen 5's sad "No..." message). Single dismiss
 * button; rounded corners and theme colors keep it on-palette.
 */
@Composable
fun FunDialog(
    message: String,
    onDismiss: () -> Unit,
    title: String? = null,
    confirmText: String = "Got it",
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(16.dp),
        title = title?.let { { Text(emojiAware(it), style = MaterialTheme.typography.titleLarge) } },
        text = { Text(emojiAware(message), style = MaterialTheme.typography.bodyLarge) },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text(emojiAware(confirmText)) }
        },
    )
}
