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

    /** Screen 4 — the surprise video (opens in the browser / Drive on tap). */
    const val VIDEO_URL =
        "https://drive.google.com/file/d/1NJHIkTZneju5aIDzxuUE3deLzK9I9uiJ/view?usp=sharing"

    /** Screen 4 — the "Akrowypady zdjęcia" download card opens this Drive photo folder. */
    const val PHOTOS_URL =
        "https://drive.google.com/drive/folders/1CF-QrKRHaH3TupvpvnANzJzVOTo16J7E?usp=drive_link"
}
