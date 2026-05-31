---
name: screen-scaffold
description: Scaffold a new Compose Multiplatform screen and wire it into the linear navigation flow. Asks for the screen name and where it goes in the flow one question at a time, then creates the screen package in commonMain, adds a route, and wires the NavHost. Use when asked to "add a screen", "scaffold a screen", "new screen", "create a screen", or "bootstrap a screen".
version: 1.1.0
allowed-tools: Bash(find:*), Bash(grep:*), Read, Write, Edit, AskUserQuestion
---

# Screen Scaffold

Add a new screen to the shared Compose app and wire it into navigation. Collect answers
interactively, confirm, then generate. **One question at a time.**

KMP + Compose Multiplatform — the screen lives in `commonMain` and runs on both platforms. See
`.claude/rules/general.md` and `.claude/rules/theme.md`.

## The project's actual structure (verified)

- **Routes:** `shared/src/commonMain/kotlin/com/own/bogdanpremium/navigation/Routes.kt` —
  an `object Routes` of `const val SCREAMING_SNAKE = "snake_case"` entries.
- **Nav graph:** `.../navigation/AppNavHost.kt` — a single `NavHost` with a **forward-only
  linear flow** (`welcome -> nameVerify -> appreciation -> tinder -> dateScience -> subscribe`).
  Each entry is `composable(Routes.X) { XScreen(onNext = { navController.navigate(Routes.Y) }) }`.
- **Screens:** one package per screen at `.../screens/<name>/<Name>Screen.kt`, package
  `com.own.bogdanpremium.screens.<name>`. Each screen takes its navigation callback(s) as
  lambda params and (for now) delegates to `PlaceholderScreen` in `.../screens/ScreenScaffold.kt`.

Before generating, open `AppNavHost.kt`, `Routes.kt`, and one existing screen to confirm nothing
has changed since this was written.

## Step 1 — Screen name
Ask for the name (e.g. `Profile`). Derive: package segment `profile`, route const
`PROFILE = "profile"`, file `screens/profile/ProfileScreen.kt`, Composable `ProfileScreen`.

## Step 2 — Position in the flow
The flow is linear and forward-only. Ask where it goes:
- **Append at the end** (after the current last screen `Subscribe`), or
- **Insert between two existing screens** (which two).

This determines the callback wiring: the screen *before* it must navigate to the new route, and
the new screen navigates to whatever comes *after* it (or takes no callback if it's the last).

## Step 3 — Confirm
Show: the new file path, Composable signature (which `onNext`-style callback, if any), the new
`Routes` const, and the exact `AppNavHost.kt` edits (new `composable` block + the neighbor's
rewired `navigate(...)`). Get a yes before writing.

## Step 4 — Generate
Use `references/templates.md`. In order:
1. Create `screens/<name>/<Name>Screen.kt`.
2. Add the route const to `Routes.kt`.
3. Add the `composable(Routes.NEW) { ... }` block in `AppNavHost.kt` and rewire the preceding
   screen's navigation callback to point at the new route.

## Step 5 — Verify
Confirm it builds on **both** targets — invoke the `verify-both-targets` skill. Report the result.
