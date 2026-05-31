# theme.md — Classy-pink Material 3 theme

The app uses a custom **"classy pink"** Material 3 theme — a sophisticated rose / mauve /
champagne palette (not loud bubblegum pink), with warm off-white and charcoal neutrals. It
lives in `shared/src/commonMain/kotlin/com/own/bogdanpremium/theme/`:

- `Color.kt` — raw palette constants, named by role + tone (e.g. `RoseDeep`, `Mauve`,
  `Champagne`, `CreamWhite`).
- `Theme.kt` — `AppTheme { ... }`, the entry point that supplies the light/dark `ColorScheme`.
  It follows the system dark mode via `isSystemInDarkTheme()`.

## Rules

- **Wrap all UI in `AppTheme { ... }`** (not `MaterialTheme {}` directly) so the palette and
  dark-mode support apply consistently — `App()` already does this.
- **Pull colors from `MaterialTheme.colorScheme`** in screens, rather than referencing the raw
  `Color.kt` constants directly.
- To adjust the theme, edit the palette constants or the `lightColorScheme`/`darkColorScheme`
  mappings in `Theme.kt`.
