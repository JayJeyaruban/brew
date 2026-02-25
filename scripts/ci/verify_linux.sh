#!/usr/bin/env bash
set -euo pipefail

chmod +x ./gradlew

# Linux CI verifies Android build/tests plus shared/web JS targets (no desktop/JVM or Wasm target).
./gradlew \
  :androidApp:assembleDebug \
  :androidApp:testDebugUnitTest \
  :sharedUI:jsTest \
  :webApp:compileKotlinJs \
  --stacktrace
