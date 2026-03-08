# Contributing to brew

This file is the source of truth for contributing workflow and commit message conventions for `brew`.
Use it for validation expectations, coding guidelines, commit message conventions, and pull request expectations.
For prerequisites, local setup, and platform run/build commands, see `README.MD`.

## Project Overview

`brew` is a Kotlin Multiplatform project.

Most contributions will touch one or more of:
- shared code in `sharedUI`
- Android app code in `androidApp`
- iOS app code in `iosApp`

## Local Setup and Running

For prerequisites, initial setup, and platform run/build commands, see `README.MD`.

## Build and Validation Expectations

Before opening a pull request:
- Run relevant Gradle build/test commands for the module(s) you changed.
- Prefer module-scoped tasks for faster iteration.
- If you changed `sharedUI`, validate at least one supported consumer platform (Android or iOS) as practical.
- If there is no automated test coverage for your change, include manual verification steps in the PR.

Formatting and lint workflow:
- Local build/run workflows auto-trigger Kotlin formatting.
- Run `./gradlew formatKotlin` to trigger formatting manually.
- Run `./gradlew lintKotlin` before opening a PR to catch style issues early.
- If lint still fails after formatting, fix remaining non-auto-correctable violations manually.

Validation scope note:
- Validate the platform(s) you changed whenever possible.
- Changes in `sharedUI` may require checking multiple platforms.

Examples (not strict requirements for every change):

```bash
./gradlew build
./gradlew :sharedUI:build
./gradlew :androidApp:assembleDebug
```

## CI Checks

- CI runs on pull requests.
- Required checks are:
  - `Linux Verify`
  - `macOS iOS Smoke`
  - `PR Commit Stack Verify`
- `CI` validates the PR head commit.
- `PR Commit Stack Verify` validates earlier commits in the current PR stack.
- GitHub may show many checks because each job (and matrix-expanded job) appears as a separate check.
- Docs-only PRs intentionally skip expensive CI jobs while still producing passing required checks.

### CI Maintainer Notes

- Avoid renaming required check job names without updating branch protection/rulesets.
- Keep non-trivial CI logic in `scripts/ci/` rather than large inline workflow scripts.
- Use job-level docs-only skips (not workflow-level `paths-ignore`) for required-check workflows.
- Any job that runs repo scripts must include `actions/checkout@v4`.
- Prefer local smoke tests for CI helper scripts when changing CI behavior.

## Coding Guidelines

- Follow the existing Kotlin/Compose style used in nearby files.
- Keep changes scoped; avoid unrelated refactors in the same PR.
- Put cross-platform behavior in `sharedUI` where appropriate.
- Keep platform-specific code in the correct platform module/source set.
- Document validation steps for schema or data-layer changes.

SQLDelight note:
- If you change `.sq` files under `sharedUI/src/commonMain/sqldelight`, describe the impact and how you validated it in your PR.

## Commit Messages (Conventional Commits)

This project uses Conventional Commits.
Commit messages are also validated on pull requests (via cocogitto) for commits in the PR branch.

Format:

```text
<type>(optional-scope): short summary
```

Examples:
- `feat(sharedUI): add brew notes field`
- `fix(androidApp): prevent crash on startup`
- `docs: add contributing guide`
- `chore: update Gradle dependencies`

Common types:
- `feat`
- `fix`
- `docs`
- `refactor`
- `test`
- `chore`
- `build`
- `ci`

Guidelines:
- Use imperative mood (`add`, `fix`, `update`)
- Keep the summary short and specific
- Do not end the summary with a period
- Prefer one logical change per commit when practical

## Pull Request Guidelines

Pull requests should include:
- What changed
- Why it changed
- How it was tested (commands and/or manual steps)
- Screenshots or video for UI changes (when applicable)
- Platforms tested and any platforms not tested

Additional expectations:
- Keep PRs focused and reviewable.
- Link the relevant issue/ticket if one exists.
- Call out follow-up work instead of bundling unrelated changes.

## Reporting Bugs and Suggesting Features

When reporting bugs or proposing features, include:
- Steps to reproduce (for bugs)
- Expected behavior vs actual behavior
- Affected platforms (`androidApp`, `iosApp`, or shared code)
- Relevant environment details (OS/device/browser/tool versions) when useful
