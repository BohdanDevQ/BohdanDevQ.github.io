---
name: pr-description
description: Draft a pull request title and description from the current branch's diff against the main branch. Summarizes what changed and why, and gives how-to-test notes for both Android and iOS. Use when asked to "write a PR description", "draft a PR", "PR summary", "describe this PR", or when preparing to open a pull request.
version: 1.0.0
allowed-tools: Bash(git:*), Read
---

# PR Description

Draft a clear PR title + body from the branch's changes. Don't open or push anything — just
produce the text for the user to review.

## Step 1 — Gather the change
```
git fetch origin main          # if a remote exists
git log --oneline origin/main..HEAD    # commits on this branch (fallback: main..HEAD)
git diff --stat origin/main...HEAD     # files touched
git diff origin/main...HEAD            # full diff for context
```
Read key changed files if the diff alone isn't clear.

## Step 2 — Write it
Output this structure:

```
## <concise title — what this PR does>

### What
- bullet summary of the changes

### Why
- the motivation / problem being solved

### How to test
- Android: <steps, e.g. ./gradlew :androidApp:assembleDebug then run>
- iOS: <steps, e.g. open iosApp in Xcode, run on simulator>
```

## Notes
- This is a dual-target project — always include test notes for **both** Android and iOS.
- Keep it honest: if something is partial or untested, say so.
- End the PR body with the project's standard trailer if the user asks you to actually create
  the PR later.
