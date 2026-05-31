package com.own.bogdanpremium.screens.subscribe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.foundation.Canvas
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/** Festive multicolor palette for the confetti flakes. Literal colors are intentional here. */
private val ConfettiColors = listOf(
    Color(0xFFF06292), // pink
    Color(0xFFBA68C8), // purple
    Color(0xFF4FC3F7), // sky
    Color(0xFFFFD54F), // gold
    Color(0xFF81C784), // green
    Color(0xFFFF8A65), // coral
)

/**
 * A single confetti flake.
 *
 * Positions and velocities are expressed in **canvas pixels** so the frame loop can
 * advance them with simple integration. [vx]/[vy] are pixels-per-frame, [angularVel]
 * is radians-per-frame. [seed] randomizes the per-flake sway phase.
 */
private data class Particle(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    var rotation: Float,
    var angularVel: Float,
    val size: Float,
    val color: Color,
    val seed: Float,
)

private const val ParticleCount = 75
private const val Gravity = 0.18f          // gentle downward pull per frame
private const val BurstUpwardKick = -22f   // strong upward launch on burst
private const val Drag = 0.992f            // mild air resistance

/** Seeds a flake near the top of [width]x[height], drifting gently down. */
private fun spawnFalling(width: Float, height: Float): Particle = Particle(
    x = Random.nextFloat() * width,
    y = -Random.nextFloat() * height * 0.5f,
    vx = (Random.nextFloat() - 0.5f) * 2f,
    vy = 1.5f + Random.nextFloat() * 2.5f,
    rotation = Random.nextFloat() * 360f,
    angularVel = (Random.nextFloat() - 0.5f) * 0.25f,
    size = 8f + Random.nextFloat() * 10f,
    color = ConfettiColors[Random.nextInt(ConfettiColors.size)],
    seed = Random.nextFloat() * 6.2831855f,
)

/** Re-seeds [p] for a celebratory burst from the lower-center, fired up and outward. */
private fun reseedForBurst(p: Particle, width: Float, height: Float) {
    val angle = (Random.nextFloat() - 0.5f) * 2.4f // spread around straight-up
    val speed = 12f + Random.nextFloat() * 14f
    p.x = width * (0.35f + Random.nextFloat() * 0.3f)
    p.y = height * (0.55f + Random.nextFloat() * 0.2f)
    p.vx = sin(angle) * speed
    p.vy = BurstUpwardKick - cos(angle) * speed * 0.2f
    p.rotation = Random.nextFloat() * 360f
    p.angularVel = (Random.nextFloat() - 0.5f) * 0.6f
}

/**
 * A full-screen [Canvas] confetti particle system.
 *
 * On its own it shows a gentle, continuous fall (flakes that exit the bottom are
 * recycled back to the top). Toggling [burst] to `true` re-seeds every flake with an
 * upward/outward launch for a celebratory pop; flip it back to `false` and `true` to
 * fire again. Each flake is drawn as a small rotated rectangle.
 *
 * Place inside a [androidx.compose.foundation.layout.Box] with [Modifier.fillMaxSize]
 * so it overlays the screen content.
 */
@Composable
fun Confetti(burst: Boolean, modifier: Modifier = Modifier) {
    var canvasWidth by remember { mutableStateOf(0f) }
    var canvasHeight by remember { mutableStateOf(0f) }

    // Particles are created once; the frame loop mutates them in place.
    val particles = remember {
        MutableList(ParticleCount) { Particle(0f, 0f, 0f, 0f, 0f, 0f, 0f, ConfettiColors[0], 0f) }
    }
    var initialized by remember { mutableStateOf(false) }

    // Apply a burst whenever the flag flips to true.
    LaunchedEffect(burst) {
        if (burst && canvasWidth > 0f && canvasHeight > 0f) {
            for (p in particles) reseedForBurst(p, canvasWidth, canvasHeight)
        }
    }

    // Drive the simulation off the frame clock.
    LaunchedEffect(Unit) {
        var lastTime = 0L
        while (true) {
            withFrameNanos { now ->
                val w = canvasWidth
                val h = canvasHeight
                if (w <= 0f || h <= 0f) return@withFrameNanos

                if (!initialized) {
                    for (i in particles.indices) {
                        particles[i] = spawnFalling(w, h)
                    }
                    initialized = true
                }

                // Normalize to ~60fps so motion is frame-rate independent.
                val dt = if (lastTime == 0L) 1f else
                    ((now - lastTime) / 16_666_667f).coerceIn(0.2f, 3f)
                lastTime = now

                for (p in particles) {
                    p.vy += Gravity * dt
                    p.vx = (p.vx + sin((p.y * 0.01f) + p.seed) * 0.12f * dt) * Drag
                    p.x += p.vx * dt
                    p.y += p.vy * dt
                    p.rotation += p.angularVel * dt * 60f

                    // Recycle flakes once they fall below the canvas.
                    if (p.y - p.size > h) {
                        val fresh = spawnFalling(w, h)
                        p.x = fresh.x
                        p.y = -p.size - Random.nextFloat() * h * 0.2f
                        p.vx = fresh.vx
                        p.vy = fresh.vy
                        p.angularVel = fresh.angularVel
                    }
                }
            }
        }
    }

    Canvas(modifier = modifier) {
        canvasWidth = size.width
        canvasHeight = size.height
        for (p in particles) {
            rotate(degrees = p.rotation, pivot = Offset(p.x, p.y)) {
                drawRect(
                    color = p.color,
                    topLeft = Offset(p.x - p.size / 2f, p.y - p.size / 4f),
                    size = Size(p.size, p.size / 2f),
                )
            }
        }
    }
}
