# general.md — Core principles (always load)

<primary_directive>
**Bogdan Premium** is a fun personal app built with **Kotlin Multiplatform (KMP)** and
**Compose Multiplatform**, targeting **both Android and iOS**. Package namespace:
`com.own.bogdanpremium`. Both platforms are **active targets** — every change must compile
and run on both.
</primary_directive>

## Always true

- **`commonMain` first.** The shared Compose UI in `commonMain` drives both platforms. Put
  all UI and logic there. Only drop into `androidMain`/`iosMain` (via `expect`/`actual`) for
  genuinely platform-specific behavior — see [[architecture]].
- **Verify on both targets**, not Android alone. When you finish a change, build the Android
  APK *and* compile the iOS framework — see [[build-test-run]]. There is also a **web
  (Kotlin/Wasm)** target running the same `commonMain` UI; compile it too when touching shared
  code (`compileKotlinWasmJs`).
- **7 screens**, a single `MainActivity`, routed with Compose Navigation.
- **Wrap UI in `AppTheme { ... }`** and pull colors from `MaterialTheme.colorScheme` — see [[theme]].

## Conventions & versions

- Versions are centralized in `gradle/libs.versions.toml` (version catalog); reference them as
  `libs.*` in `build.gradle.kts`. Add new dependencies there, not inline.
- Inter-project deps use typesafe project accessors (`projects.shared`).
- Compose resources go through the generated `Res` class (e.g. `Res.drawable.compose_multiplatform`);
  files live under `shared/src/commonMain/composeResources/`.
- JVM target Java 11; Android `minSdk` 24, `compileSdk`/`targetSdk` 36.
