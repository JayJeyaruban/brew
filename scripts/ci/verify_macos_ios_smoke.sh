#!/usr/bin/env bash
set -euo pipefail

chmod +x ./gradlew

./gradlew \
  :sharedUI:compileKotlinIosX64 \
  :sharedUI:compileKotlinIosSimulatorArm64 \
  --stacktrace
