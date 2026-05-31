# build-test-run.md — Gradle, Java/JBR, iOS build & signing, tests

## Java / JBR (read this first)

This machine has **no JDK on `PATH`**. Gradle (and Xcode's `embedAndSignAppleFrameworkForXcode`
build phase) finds Java via `org.gradle.java.home` in `~/.gradle/gradle.properties`, pointing at
Android Studio's bundled JBR. **From the terminal, prefix gradle commands with:**

```
JAVA_HOME=/Applications/Android\ Studio.app/Contents/jbr/Contents/Home
```

## Build

```
./gradlew :androidApp:assembleDebug                  # Android debug APK
./gradlew :shared:compileKotlinIosSimulatorArm64     # iOS simulator (arm64)
./gradlew :shared:compileKotlinIosArm64              # iOS device (arm64)
```

Compiling the iOS framework verifies the shared module builds for iOS without opening Xcode.
Always check both targets after a change.

## Web (Kotlin/Wasm)

The shared Compose UI also runs on the web via a `wasmJs` browser target (entry point
`shared/src/wasmJsMain/kotlin/.../main.kt`, page `wasmJsMain/resources/index.html`).

```
./gradlew :shared:compileKotlinWasmJs                # fast: verify the web target compiles
./gradlew :shared:wasmJsBrowserDevelopmentRun        # run locally → http://localhost:8080
./gradlew :shared:wasmJsBrowserDistribution          # production bundle → shared/build/dist/wasmJs/productionExecutable/
```

The dev-run server is long-running (stop with Ctrl-C). The distribution output is a static site
(html + js + `skiko.wasm` renderer + app wasm) deployable to any free static host. Needs a recent
browser (WasmGC — Safari/iOS 18.2+); older browsers fall back to JS via Compose's web compat mode.

## Test

```
./gradlew :shared:testAndroidHostTest                # Android (JVM host) tests
./gradlew :shared:iosSimulatorArm64Test              # iOS simulator tests (arm64)
./gradlew :shared:allTests                           # all KMP test targets
```

Single test (Gradle filter):

```
./gradlew :shared:testAndroidHostTest --tests "com.own.bogdanpremium.SharedLogicAndroidHostTest"
```

## iOS app & signing

Open `iosApp/iosApp.xcodeproj` in Xcode, set a signing **Team** under the iosApp target's
Signing & Capabilities (`TEAM_ID` in `iosApp/Configuration/Config.xcconfig` is empty), pick a
Simulator or connected device, and Run. Xcode's build phase invokes
`:shared:embedAndSignAppleFrameworkForXcode` automatically.

Signing uses a **free Personal Team** — provisioning certs expire ~7 days, so rebuild from
Xcode to reinstall when the app stops launching.
