package com.own.bogdanpremium.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.own.bogdanpremium.Strings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Tiny uppercase progress label like "STEP 1 OF 4" for the multi-step flow.
 */
@Composable
fun StepIndicator(
    current: Int,
    total: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = Strings.Common.step(current, total),
        modifier = modifier,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp,
        color = MaterialTheme.colorScheme.primary,
    )
}
