# Screen scaffold templates

Concrete templates matching the project's conventions (verified against the existing 6 screens).
Replace `<Name>` (PascalCase, e.g. `Profile`), `<name>` (lowercase package, e.g. `profile`),
`<ROUTE>` (the new `Routes` const), and `<NEXT_ROUTE>` (the route this screen advances to, if any).

## 1. Screen file — `screens/<name>/<Name>Screen.kt`

Existing screens delegate to `PlaceholderScreen`. Two variants:

**With a forward action (most screens):**
```kotlin
package com.own.bogdanpremium.screens.<name>

import androidx.compose.runtime.Composable
import com.own.bogdanpremium.screens.PlaceholderScreen

/** Screen — <Name>. Placeholder until the real UI is built. */
@Composable
fun <Name>Screen(onNext: () -> Unit) {
    PlaceholderScreen(title = "<Name>", nextLabel = "Continue", onNext = onNext)
}
```

**Last screen in the flow (no forward action):**
```kotlin
package com.own.bogdanpremium.screens.<name>

import androidx.compose.runtime.Composable
import com.own.bogdanpremium.screens.PlaceholderScreen

/** Screen — <Name>. Placeholder until the real UI is built. */
@Composable
fun <Name>Screen() {
    PlaceholderScreen(title = "<Name>")
}
```

When building the *real* UI later (not a placeholder), write the Composable directly and pull
colors from `MaterialTheme.colorScheme` — never wrap a second theme (`App()` already applies
`AppTheme`). See `.claude/rules/theme.md`.

## 2. Route — `navigation/Routes.kt`

Add a const inside `object Routes`, in flow order:
```kotlin
const val <ROUTE> = "<route_string>"   // e.g. PROFILE = "profile"
```

## 3. Nav graph — `navigation/AppNavHost.kt`

Add the import for the new screen, then a `composable` block in flow position:
```kotlin
composable(Routes.<ROUTE>) {
    <Name>Screen(onNext = { navController.navigate(Routes.<NEXT_ROUTE>) })
}
```
For the last screen, call `<Name>Screen()` with no callback.

**Rewire the preceding screen** so it navigates to the new route. Example — inserting `Profile`
after `Subscribe` means changing whatever pointed past `Subscribe` (or, if appending, giving the
previously-last screen an `onNext` that navigates to `Routes.<ROUTE>`).
