# Bogdan Premium — Development Plan

A fun personal KMP + Compose Multiplatform app: **7 screens**, single-Activity, forward-only
Compose Navigation flow. This plan is the source of truth for *what to build*. It does **not**
contain code (except the one screen already started) — it's the map we build against.

> **Targets:** Android is the primary platform, but the project keeps **both Android and iOS**
> compiling (see `.claude/rules/general.md`). Everything lives in `commonMain` and uses Compose
> APIs that work on both — the one exception is the Screen 4 video player (see §2 / §6).

---

## 0. Palette decision (settled)

The design mockups + spec use purple `#534AB7` + accent `#EEEDFE`. **Decision: keep the existing
"classy pink" theme as-is.** `theme/Color.kt` and `theme/Theme.kt` are **not** changed. Wherever
the design shows "purple," read it as **`MaterialTheme.colorScheme.primary`** (currently rose
`#8E3B5C`); "light purple accent" maps to `colorScheme.primaryContainer`. All screens pull from
`colorScheme`, so switching to purple later would be a one-file edit in `Theme.kt`.

---

## 1. Current state

| Area | Status | Notes |
|------|--------|-------|
| KMP/Compose scaffold, both targets | ✅ Done | `App.kt` wraps `AppNavHost` in `AppTheme` + `Surface`. |
| Classy-pink theme | ✅ Done | `theme/Color.kt`, `theme/Theme.kt`. Keep as-is. |
| Navigation graph (7 routes, linear) | ✅ Done | `navigation/Routes.kt`, `AppNavHost.kt` — incl. `SURPRISE_VIDEO`. |
| Shared UI primitives | ✅ Done | `PillBadge`, `PrimaryButton` (`enabled` grey-out), `StepIndicator`, `PagerDots`, `FunDialog`. |
| Dependencies | ✅ Done | Compose MP 1.11.0, material3, navigation-compose, lifecycle. |
| Screen 4 — Surprise video | 🟡 UI built | Real screen done (`surprisevideo/SurpriseVideoScreen.kt`); **video playback + download action still stubbed** (needs platform players + assets, §2/§6). |
| Screens 1–3, 5–7 — real UIs | ✅ UI built | All fancy/animated and compiling on both targets. `PlaceholderScreen` now unused (trim in Phase 8). |
| Pending content | ⏳ Needs assets | `AppConfig.CORRECT_NAME` placeholder; Bogdan's photo; the video file + Drive link. |
| Tests for pure logic | ❌ Not started | Phase 8 — name check, checkbox gating. |

---

## 2. Dependencies

**Almost everything is already wired** in `shared/build.gradle.kts`: Compose MP 1.11.0
(`runtime`, `foundation` incl. `HorizontalPager`/`Canvas`/gestures, `ui`, `material3`),
`navigation-compose` 2.9.2, lifecycle. Animation APIs (`animate*AsState`, `Animatable`,
`AnimatedVisibility`) are part of Compose; `animate*AsState` is already used in `PagerDots`.

**Two things to add as we reach the features that need them (don't add speculatively):**

1. **Video playback with sound (Screen 4)** — **not available in commonMain.** Needs an
   `expect`/`actual` player:
   - Android: AndroidX **Media3 / ExoPlayer** (`androidx.media3:media3-exoplayer` + `media3-ui`)
     hosted in an `AndroidView`.
   - iOS: **AVPlayer** (`AVKit`/`AVFoundation`) hosted via `UIKitView`.
   - Plus the actual **video asset** (bundled resource or remote URL) and the **Drive link** for
     the download card, opened through a platform URL opener (`expect fun openUrl(...)`).
2. **Animation artifact** — if `AnimatedVisibility`/`rememberInfiniteTransition` don't resolve,
   add `org.jetbrains.compose.animation:animation` to the catalog + `commonMain`.

**Confetti (Screen 7):** no KMP confetti library exists; hand-rolled `Canvas` particle system.

---

## 3. Project structure

```
shared/src/commonMain/kotlin/com/own/bogdanpremium/
├── App.kt                         (done)
├── AppConfig.kt                   ← add HER_NAME constant + any shared copy here
├── navigation/
│   ├── Routes.kt                  (done — incl. SURPRISE_VIDEO)
│   └── AppNavHost.kt              (done — wiring matches the 7-screen flow)
├── theme/                         (done — untouched)
├── ui/                            (done — shared primitives)
└── screens/
    ├── ScreenScaffold.kt          (PlaceholderScreen — shrinks as screens land)
    ├── welcome/WelcomeScreen.kt          (1) build real UI
    ├── nameverify/NameVerifyScreen.kt    (2) build real UI; uses HER_NAME
    ├── appreciation/AppreciationScreen.kt(3) HorizontalPager + gated next
    ├── surprisevideo/
    │   └── SurpriseVideoScreen.kt        (4) ✅ UI built; player/download stubbed
    │       └── (future) VideoPlayer expect/actual + openUrl expect/actual
    ├── tinder/
    │   ├── TinderScreen.kt               (5) build real UI
    │   └── TinderCard.kt                 ← swipeable + scrollable profile card
    ├── datescience/
    │   ├── DateScienceScreen.kt          (6) reveal flow
    │   ├── CortisolChart.kt              ← animated 2-bar chart
    │   └── FortuneWheel.kt               ← Canvas wheel + spin
    └── subscribe/
        ├── SubscribeScreen.kt            (7) build real UI
        └── Confetti.kt                   ← particle system
```

**State approach:** plain `remember` / `rememberSaveable` per screen — no ViewModels (forward-only,
screen-local state). **Strings:** hardcode playful copy inline; only `AppConfig.HER_NAME` is
centralized.

---

## 4. Navigation graph plan

**Graph is done.** Internal page/section transitions are NOT nav destinations.

```
welcome ──onJumpIn──▶ nameVerify ──onContinue──▶ appreciation ──onFinished──▶
surpriseVideo ──onNext──▶ tinder ──onLiked──▶ dateScience ──onNext──▶ subscribe (end)

Screen 3 (appreciation):  2 pages via HorizontalPager — internal, NOT routes.
Screen 6 (dateScience):   3 sections revealed progressively — internal state, NOT routes.
```

- Forward-only; system back allowed. No args passed between destinations.
- `onFinished` (appreciation → surpriseVideo) fires only when all 4 checkboxes are checked.
- `onLiked` (tinder → dateScience) fires on like/super-like (swipe right/up or ♥/★), never on nope.

---

## 5. Per-screen build plan

### Screen 1 — Welcome  `welcome/WelcomeScreen.kt`
Centered: `PillBadge("BOGDAN PREMIUM")`, 👋, "Hey, girl!", subtitle with **"Bogdan Premium" bold +
primary-colored** (`buildAnnotatedString`), `PrimaryButton("Jump in!")` → `onJumpIn`. Simplest —
build first to validate primitives + theme.

### Screen 2 — Name verification  `nameverify/NameVerifyScreen.kt`
`StepIndicator(current = 1, total = 5)`, title, subtitle, `OutlinedTextField`, "{n} attempts
remaining", `PrimaryButton("Continue")`. State via `rememberSaveable` (text + attempts, start 3).
On Continue: compare to `AppConfig.HER_NAME` **case-insensitive, trimmed**.
- Correct → `onContinue`.
- Wrong → decrement, show `FunDialog` ("Haha, you wanted to test me 😂… middle of the night 🌙").
- Attempts hit 0 → **auto-fill the field with `HER_NAME` only**; she still presses Continue.

### Screen 3 — Appreciation  `appreciation/AppreciationScreen.kt`
`HorizontalPager` (2 pages) + `PagerDots` pinned top.
- Page 1: 💬, the appreciation paragraph, `PrimaryButton("Next →")` → page 2.
- Page 2: "Why do you think I like you?" + 4 `Checkbox` rows ("I always make her laugh", "Our 3am
  conversations", "I actually listen", "Unhinged in a cute way"). `PrimaryButton(enabled = all 4
  checked)` → `onFinished`.

### Screen 4 — Surprise video  `surprisevideo/SurpriseVideoScreen.kt`  ✅ UI built
A personal video (with sound) + a "download" link card. **UI is built** to match the design:
- Dark 16:9 `VideoPreviewCard` (tap-to-play affordance + faux `0:00` scrubber).
- Title "A little something I made for you 🎬" + subtitle "No pressure. Just me being an idiot at
  2am." *(copy is placeholder — swap for whatever fits the video of her.)*
- `DownloadCard`: 📷 thumb + "Akrowypady zdjęcia" + "Tap to download · Drive" + ↓ arrow.
- `PrimaryButton("Next →")` → `onNext`.

**Still to wire (platform-specific follow-up):**
- Real playback with sound → `expect`/`actual` `VideoPlayer` (Android Media3/ExoPlayer in
  `AndroidView`; iOS AVPlayer in `UIKitView`) + the actual video asset.
- Download tap → `expect`/`actual` `openUrl(driveLink)`.

### Screen 5 — Tinder mock  `tinder/TinderScreen.kt` + `TinderCard.kt`
Faithful Tinder feel — **real directional swipe gestures are core**, buttons mirror them.
- `TinderCard`: rounded 16dp card, gradient "photo" + emoji (**swap for Bogdan's real photo via
  Compose resources**); "Bogdan, 25"; "Software eng · 2km away".
- **Scroll down reveals the full profile:** "🎯 Looking for: my person", "☕ Will suggest coffee",
  and a short "why I'm a good match" self-presentation.
- **Swipe gestures** (`pointerInput` + `Animatable` offset + fling thresholds): left = nope →
  `FunDialog("😢 Do you mean it??????")` + spring back; right = like → `onLiked`; up = super like →
  `onLiked`.
- **Buttons mirror gestures:** ✕ (nope), ★ (super like), ♥ (like). Hint "← nope · ★ super like · ♥ yes →".

### Screen 6 — Date science  `datescience/DateScienceScreen.kt` (+ `CortisolChart`, `FortuneWheel`)
`LazyColumn` with **progressive reveal** (`Section.INTRO → CHART → WHEEL`, `AnimatedVisibility`):
- Intro: 😊, "…our meeting today is a date? 😊", "Yes! 🎉" / "No…". "No" → sad `FunDialog`, stay.
  "Yes" → reveal chart.
- Chart (`CortisolChart`): "Cortisol forecast" + 2 bars — "Before 😨" tall/orange, "During 😌"
  short/green, **animate growing from the bottom on appear**. Button "What to expect →" → wheel.
- Wheel (`FortuneWheel`): "What to expect" + 6-segment `Canvas` wheel (Fun, A bit of cringe, Cute
  moments, A kiss, Protein food, Flowers 🌸) that **spins on appear**. "Next →" → `onNext`.
- Scroll newly revealed sections into view (`animateScrollToItem`).

### Screen 7 — Subscribe  `subscribe/SubscribeScreen.kt` + `Confetti.kt`
- On load: confetti falls. 🎉, "Thank you for getting here!", subtitle, `PrimaryButton("Subscribe ✨")`.
- On tap: confetti **burst** + reveal "I absolutely collect all your data!!!! You won't get away
  from me that easily 😈" (dashed quote box).
- `Confetti`: `Canvas` particle system advanced via `withFrameNanos`; cap ~80 particles.

---

## 6. Tricky bits / risks (flagged)

1. **Video playback with sound (Screen 4)** — the **only feature that breaks the commonMain
   rule**. Needs `expect`/`actual` players (Media3/ExoPlayer + AVPlayer), platform view interop
   (`AndroidView`/`UIKitView`), a real video asset, and a URL opener for the download. Most
   platform-specific work in the app. UI shell is already done and compiles on both.
2. **Confetti, cross-platform** — no KMP library; hand-rolled `Canvas` particle system.
3. **Fortune wheel** — `Canvas` `drawArc` for 6 segments + on-arc labels + spin; legend fallback
   if on-arc text is too rough.
4. **Cortisol bars** — must animate **on reveal**, not at composition (drive off reveal state).
5. **Screen 6 reveal + scroll** — `AnimatedVisibility` inside `LazyColumn`; scroll into view.
6. **Tinder swipe gestures (Screen 5)** — drag offset + rotation + fling thresholds, coexisting
   with vertical scroll-to-profile; reconcile gestures + buttons into one like/super-like/nope
   handler; tune drag-vs-scroll axis.
7. **Name auto-fill after 3 attempts** — case/whitespace handling; pure logic → unit-testable.
8. **AnnotatedString partial styling** (Screen 1 subtitle) — minor.
9. **Keyboard / IME** (Screen 2) — verify on both platforms; set `imeAction`/`KeyboardActions`.
10. **Both-target verification** — run `verify-both-targets` after each screen.

---

## 7. Build order & phasing

Ascending complexity; each phase ends by running the **`verify-both-targets`** skill.

- **Phase 0 — Prep:** add `AppConfig.HER_NAME` placeholder; confirm animation artifact resolves.
- **Phase 1 — Screen 1 (Welcome):** validates primitives + theme. (simple)
- **Phase 2 — Screen 2 (Name verify):** input + attempts + dialog + auto-fill.
- **Phase 3 — Screen 3 (Appreciation):** pager + checkbox-gated next.
- **Phase 4 — Screen 4 (Surprise video):** ✅ UI done. Follow-up: wire `expect`/`actual` player +
  download once the video asset + Drive link exist.
- **Phase 5 — Screen 5 (Tinder):** swipeable + scrollable profile card. (custom gestures)
- **Phase 6 — Screen 6 (Date science):** reveal flow + chart + wheel. (hardest)
- **Phase 7 — Screen 7 (Subscribe):** confetti + reveal. (custom animation)
- **Phase 8 — Polish & tests:** trim `PlaceholderScreen`; `commonTest` for pure logic (name
  check, checkbox gating); final both-target build.

**Commit cadence:** one commit per screen/phase, matching the "Step N: …" history style.

---

## 8. Open questions for you

Resolved:
- ~~Screen 2 after 3 fails~~ → **auto-fill only**, she still presses Continue. ✅
- ~~Screen 5 swipe~~ → **real directional swipe gestures are core**, buttons mirror them. ✅
- ~~Where to add the video~~ → **new Screen 4**, between Appreciation and Tinder (matches design). ✅

Still open:
- **Her name** → fill in `AppConfig.HER_NAME`.
- **Video asset + sound** → the actual video file (bundled or URL) so playback can be wired.
- **Download link** → the real Drive URL the download card should open.
- **Screen 4 copy** → keep "Just me being an idiot at 2am", or reword for a video *of her*?
- **Bogdan's photo (Screen 5)** → real image via Compose resources when available.
- **Screen 5 swipe down** → reserved for scrolling the profile (not a swipe action) — confirm.
