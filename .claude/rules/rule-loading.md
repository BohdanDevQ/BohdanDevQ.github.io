# rule-loading.md — the rule loader

This is the **table of contents** for project rules. It carries no rules itself — it
tells you *which* rule file to read *when*, so you don't burn context on rules a task
doesn't need.

## How to use this

- Rules live in `.claude/rules/`. If that folder exists in the project, these are the rules.
- **`general.md` is always in effect** — its content is short and applies to every task.
- The other files are **conditional**: read a file only when the current task matches its
  **Load when** triggers or **Keywords** below. Read it *before* doing that work, not after.
- When several apply, load all of them.

## Rule files

### general.md — Core principles (ALWAYS load)
**Load when:** always — every task.
**Keywords:** anything; KMP, Compose, commonMain, both targets, conventions, versions.

### architecture.md — Module layout & expect/actual
**Load when:**
- Deciding where a class/file belongs (`shared` vs `androidApp` vs `iosApp`; which source set).
- Adding platform-specific behavior, or touching `expect`/`actual` declarations.
- Reasoning about the iOS framework, `MainViewController`, or module boundaries.
**Keywords:** module, source set, commonMain, androidMain, iosMain, expect, actual, platform, framework, layout.

### theme.md — Classy-pink Material 3 theme
**Load when:**
- Writing or restyling any UI / Composable.
- Touching colors, dark mode, `AppTheme`, `Color.kt`, or `Theme.kt`.
**Keywords:** theme, color, palette, pink, rose, mauve, Material 3, colorScheme, dark mode, AppTheme, styling, UI.

### build-test-run.md — Gradle, Java/JBR, iOS build & signing, tests
**Load when:**
- Building, running, or testing either target.
- Hitting Java/`JAVA_HOME` issues, or running anything through `./gradlew`.
- Working on iOS code signing or the Xcode project.
**Keywords:** gradle, build, assembleDebug, compile, test, JAVA_HOME, JBR, JDK, iOS, Xcode, signing, simulator, run.

## Maintaining these rules (do this yourself)

You own this rule set. **Without being asked**, keep it current:

- When you discover a recurring convention, repeat a correction the user gave you, or learn
  a non-obvious fact about how this project is built — write it into the right rule file.
- If it doesn't fit an existing file and is substantial, create a new `<topic>.md` here and
  **register it in the "Rule files" list above** with its own *Load when* / *Keywords*.
- Keep `general.md` short; push detail into the conditional files.
- Prefer editing an existing rule over duplicating one. Delete rules that become wrong.
- This is for stable project knowledge. One-off task state belongs in memory, not here.

Mention in your reply when you've changed a rule file, but you don't need to ask permission first.
