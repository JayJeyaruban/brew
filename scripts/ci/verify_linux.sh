#!/usr/bin/env bash
set -euo pipefail

chmod +x ./gradlew

# Fail fast on style violations in CI. Local auto-format-on-build is disabled when CI=true.
./gradlew lintKotlin --stacktrace

# Linux CI verifies Android build/tests.
./gradlew \
  :androidApp:assembleDebug \
  :androidApp:testDebugUnitTest \
  --stacktrace
