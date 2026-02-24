#!/usr/bin/env bash
set -euo pipefail

chmod +x ./gradlew

./gradlew \
  :androidApp:assembleDebug \
  :androidApp:testDebugUnitTest \
  :sharedUI:jvmTest \
  :webApp:compileKotlinJs \
  :webApp:compileKotlinWasmJs \
  --stacktrace
