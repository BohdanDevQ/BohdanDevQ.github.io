package com.own.bogdanpremium

/**
 * App-wide constants. Single source of truth for the playful flow logic so the
 * values aren't scattered across screens.
 */
object AppConfig {
    /**
     * The name Screen 2 verifies against. Placeholder for now — fill in later.
     * Matching is case-insensitive and trimmed (see NameVerifyScreen).
     */
    const val CORRECT_NAME = "Zuza"

    /** Any of these (trimmed, case-insensitive) is accepted as the right name. */
    val ACCEPTED_NAMES = listOf("Zuza", "Zuzanna")

    /** Wrong-name tries allowed on Screen 2 before the field is auto-filled. */
    const val MAX_ATTEMPTS = 3

    /** Screen 4 — the surprise video (direct MP4; played inline on web). */
    const val VIDEO_URL =
        "https://pub-af8ae1ee9a214f5c8677c32f7487912f.r2.dev/video.MP4"

    /** Screen 4 — the "Akrowypady zdjęcia" download card opens this Drive photo folder. */
    const val PHOTOS_URL =
        "https://drive.google.com/drive/folders/1CF-QrKRHaH3TupvpvnANzJzVOTo16J7E?usp=drive_link"
}
