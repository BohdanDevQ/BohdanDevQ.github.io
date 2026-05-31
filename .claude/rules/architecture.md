# architecture.md — Module layout & expect/actual

## Module layout

- `shared/` — KMP library holding nearly all code: the Compose UI (`App.kt`) and business
  logic, plus platform-specific implementations. **Most work happens here.**
- `androidApp/` — thin Android entry point. `MainActivity` just calls `App()`.
- `iosApp/` — Xcode/SwiftUI entry point. `ContentView` hosts the Compose UI via
  `MainViewController()` (defined in `shared/src/iosMain`).

The iOS framework is built statically with `baseName = "Shared"` and consumed by the Swift code.

## Source set structure (`shared/src/`)

- `commonMain/` — shared code for all targets, including the Compose `App()` and `expect` declarations.
- `androidMain/`, `iosMain/` — platform `actual` implementations.
- `commonTest/` — shared tests; `androidHostTest/`, `iosTest/` — platform tests.
  (Android tests live in **`androidHostTest`**, not `androidUnitTest`, because of the
  `com.android.kotlin.multiplatform.library` plugin.)

## expect / actual

Cross-platform code uses the **`expect`/`actual`** pattern: declare `expect` in `commonMain`
(e.g. `getPlatform()` in `Platform.kt`), implement `actual` in each platform's `*Main` source
set (`Platform.android.kt`, `Platform.ios.kt`). When adding platform-specific behavior, follow
this pattern rather than branching at runtime.
