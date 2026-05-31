package com.own.bogdanpremium.screens.tinder

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.own.bogdanpremium.Strings
import com.own.bogdanpremium.ui.FunDialog
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

/** Green used for the "like" affordance (mirrors the Tinder feel). */
private val LikeGreen = Color(0xFF3DDC84)

/** Distance in pixels a card must travel before a swipe "commits". */
private const val SwipeThreshold = 150f

/** Bio lines for the playful Gen-Z "About" section. Cringe but sweet (po polsku). */
private val aboutLines = Strings.Tinder.aboutLines

/**
 * Screen 5 — a fancy Tinder-style mock for Bogdan. A draggable profile card sits up
 * top with real drag gestures, fading LIKE / NOPE / SUPER LIKE stamps, and
 * threshold-based swipe outcomes. The card lives OUTSIDE the scrolling region so its
 * drag never fights the page scroll; the bio scrolls independently below it, and the
 * action buttons stay pinned at the bottom.
 *
 * Swiping right or up (or tapping ♥ / ★) counts as a like and calls [onLiked]. Swiping
 * left (or tapping ✕) triggers the playful "Na pewno??" dialog and springs back.
 */
@Composable
fun TinderScreen(onLiked: () -> Unit) {
    val scope = rememberCoroutineScope()
    val offset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }
    var showNopeDialog by remember { mutableStateOf(false) }

    fun resetCard() {
        scope.launch { offset.animateTo(Offset.Zero) }
    }

    fun triggerNope() {
        showNopeDialog = true
    }

    if (showNopeDialog) {
        FunDialog(
            message = Strings.Tinder.nopeDialog,
            onDismiss = {
                showNopeDialog = false
                resetCard()
            },
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surfaceVariant,
                    ),
                ),
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp),
        ) {
            // --- Swipeable card (NOT in a scroll container, so the drag is exclusive) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp),
            ) {
                val dragX = offset.value.x
                val dragY = offset.value.y

                TinderCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset { IntOffset(dragX.roundToInt(), dragY.roundToInt()) }
                        .graphicsLayer {
                            rotationZ = (dragX / 60f).coerceIn(-15f, 15f)
                        }
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    scope.launch {
                                        offset.snapTo(offset.value + dragAmount)
                                    }
                                },
                                onDragEnd = {
                                    val x = offset.value.x
                                    val y = offset.value.y
                                    when {
                                        // Up beats horizontal: super like.
                                        y < -SwipeThreshold && abs(y) >= abs(x) -> onLiked()
                                        x > SwipeThreshold -> onLiked()
                                        x < -SwipeThreshold -> triggerNope()
                                        else -> resetCard()
                                    }
                                },
                            )
                        },
                )

                // Fading gesture stamps, opacity tied to drag distance.
                Stamp(
                    text = Strings.Tinder.stampLike,
                    color = LikeGreen,
                    alpha = (dragX / SwipeThreshold).coerceIn(0f, 1f),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(24.dp)
                        .graphicsLayer { rotationZ = -18f },
                )
                Stamp(
                    text = Strings.Tinder.stampNope,
                    color = MaterialTheme.colorScheme.error,
                    alpha = (-dragX / SwipeThreshold).coerceIn(0f, 1f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(24.dp)
                        .graphicsLayer { rotationZ = 18f },
                )
                Stamp(
                    text = Strings.Tinder.stampSuper,
                    color = MaterialTheme.colorScheme.primary,
                    alpha = (-dragY / SwipeThreshold).coerceIn(0f, 1f),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(24.dp),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- "About" section: scrolls independently in the remaining space ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
            ) {
                Text(
                    text = Strings.Tinder.aboutTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    tonalElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        aboutLines.forEach { line ->
                            Text(
                                text = line,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Action buttons (pinned, mirror the swipe gestures) ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ActionButton(symbol = "✕", contentColor = MaterialTheme.colorScheme.error, onClick = { triggerNope() })
                ActionButton(symbol = "★", contentColor = MaterialTheme.colorScheme.primary, onClick = onLiked)
                ActionButton(symbol = "♥", contentColor = LikeGreen, onClick = onLiked)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = Strings.Tinder.hint,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

/** A rotated, semi-transparent gesture stamp (LIKE / NOPE / SUPER LIKE). */
@Composable
private fun Stamp(
    text: String,
    color: Color,
    alpha: Float,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.alpha(alpha),
        shape = RoundedCornerShape(8.dp),
        color = Color.Transparent,
        border = BorderStroke(4.dp, color),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Black,
            color = color,
            fontSize = 28.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
        )
    }
}

/** A circular action button rendered with a text symbol (no icon dependency). */
@Composable
private fun ActionButton(
    symbol: String,
    contentColor: Color,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface,
        contentColor = contentColor,
        tonalElevation = 3.dp,
        shadowElevation = 4.dp,
        modifier = Modifier.size(64.dp),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = symbol,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor,
            )
        }
    }
}
