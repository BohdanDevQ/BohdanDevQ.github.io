---
name: verify-both-targets
description: Verify the current change compiles and tests on BOTH Android and iOS — the project's core rule. Runs the Android debug build, the iOS framework compile, and the shared tests with the correct JBR JAVA_HOME. Use when asked to "build", "verify the change", "does it compile", "check both targets", "did I break iOS", or before finishing any change.
version: 1.0.0
allowed-tools: Bash
---

# Verify Both Targets

This project is dual-target: every change must compile on Android **and** iOS. Run the checks
below, in order, and report what passed/failed with the relevant output. Don't stop at Android.

All gradle commands need the JBR as `JAVA_HOME` (no JDK on PATH — see
`.claude/rules/build-test-run.md`):

```
export JAVA_HOME=/Applications/Android\ Studio.app/Contents/jbr/Contents/Home
```

## Step 1 — Android compiles
```
./gradlew :androidApp:assembleDebug
```

## Step 2 — iOS framework compiles
```
./gradlew :shared:compileKotlinIosSimulatorArm64
```
(Add `:shared:compileKotlinIosArm64` if the change might be device-specific.)

## Step 3 — Tests (run if logic changed)
```
./gradlew :shared:testAndroidHostTest
./gradlew :shared:iosSimulatorArm64Test
```
Or `./gradlew :shared:allTests` for everything.

## Step 4 — Report
State plainly which targets/tests passed and which failed. If something failed, show the error
output — don't claim success. Only report "builds on both targets" once Steps 1 and 2 both pass.
