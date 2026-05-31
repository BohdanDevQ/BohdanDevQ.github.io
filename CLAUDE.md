# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Rules live in a rule loader

Project rules use the **rule-loader pattern**: one entry point lists every rule file and says
*when* to read it, so context isn't wasted on rules a task doesn't need.

@.claude/rules/rule-loading.md

**At the start of any task:**
1. `.claude/rules/general.md` is **always in effect** — short, applies to everything.
2. Open `.claude/rules/rule-loading.md` and load any conditional rule file whose *Load when* /
   *Keywords* match the task (architecture, theme, build-test-run, …) **before** doing the work.

## Quick overview

**Bogdan Premium** — a fun personal app in **Kotlin Multiplatform + Compose Multiplatform**,
targeting **Android, iOS, and Web** (Kotlin/Wasm) — all active, all driven by the same
`commonMain` Compose UI. Package `com.own.bogdanpremium`. 7 screens, single `MainActivity`,
Compose Navigation. Most code lives in `shared/`. Details are in the rule files above.

## Development plan

The full feature spec and phased build plan for the 6 screens live in
**[`docs/DEVELOPMENT_PLAN.md`](docs/DEVELOPMENT_PLAN.md)** — current state, per-screen plans,
flagged risks, and build order. Consult and update it when implementing screens. Note: the
palette stays **classy pink** (`theme/`), and every "purple" in the spec maps to
`MaterialTheme.colorScheme.primary`.

## Skills

Reusable workflows are packaged as skills in `.claude/skills/<name>/SKILL.md` (Claude Code
surfaces them automatically by their `description`). Current suite:

- **screen-scaffold** — add a Compose screen and wire it into navigation.
- **verify-both-targets** — build + test on Android *and* iOS (the core rule), with the JBR `JAVA_HOME`.
- **pr-description** — draft a PR title/body from the branch diff.

These don't duplicate the built-in skills (`/code-review`, `/security-review`, `/run`, `/verify`).

## You maintain the rules and skills yourself

You don't need to ask permission to keep these healthy. **Without being prompted:**

**Rules** (`.claude/rules/`):
- When you spot a recurring convention, a correction the user repeated, or a non-obvious build
  fact — write it into the matching file.
- If it's substantial and fits nothing existing, add a new `.claude/rules/<topic>.md` and
  register it in `rule-loading.md` with its own *Load when* / *Keywords*.
- Keep `general.md` short; push detail into conditional files. Edit over duplicate; delete
  what's wrong.

**Skills** (`.claude/skills/`):
- When a multi-step workflow recurs (you've now done it more than once the same way), package
  it as `.claude/skills/<name>/SKILL.md` with a trigger-rich `description`, and list it in the
  Skills section above.
- Keep existing skills in sync as the project evolves (new commands, renamed paths). Bump the
  `version` when a skill's steps change meaningfully.

Just mention in your reply when you've added or changed a rule or skill.
