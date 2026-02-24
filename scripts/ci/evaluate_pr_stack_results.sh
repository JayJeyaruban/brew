#!/usr/bin/env bash
set -euo pipefail

require_env() {
  if [[ -z "${!1-}" ]]; then
    echo "Missing required environment variable: $1" >&2
    exit 1
  fi
}

require_env DOCS_ONLY
require_env DETECT_CHANGES_RESULT
echo "detect-changes: ${DETECT_CHANGES_RESULT}"
echo "docs_only: ${DOCS_ONLY}"

if [[ "${DETECT_CHANGES_RESULT}" != "success" ]]; then
  echo "Change detection failed." >&2
  exit 1
fi

if [[ "${DOCS_ONLY}" == "true" ]]; then
  echo "Docs-only PR changes detected; skipping per-commit verification."
  exit 0
fi

require_env HAS_COMMITS
require_env ENUMERATE_RESULT
require_env LINUX_RESULT
require_env MACOS_RESULT

echo "enumerate-commits: ${ENUMERATE_RESULT}"
echo "verify-linux-per-commit: ${LINUX_RESULT}"
echo "verify-macos-per-commit: ${MACOS_RESULT}"
echo "has_commits: ${HAS_COMMITS}"

if [[ "${ENUMERATE_RESULT}" != "success" ]]; then
  echo "Commit enumeration failed." >&2
  exit 1
fi

if [[ "${HAS_COMMITS}" == "false" ]]; then
  echo "No non-head commits in PR; head commit is covered by CI workflow."
  exit 0
fi

if [[ "${LINUX_RESULT}" != "success" ]]; then
  echo "Linux per-commit verification failed." >&2
  exit 1
fi

if [[ "${MACOS_RESULT}" != "success" ]]; then
  echo "macOS per-commit verification failed." >&2
  exit 1
fi

echo "All non-head PR commits passed Linux and macOS verification."
