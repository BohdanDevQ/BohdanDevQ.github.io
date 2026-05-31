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

    /** Wrong-name tries allowed on Screen 2 before the field is auto-filled. */
    const val MAX_ATTEMPTS = 3
}
