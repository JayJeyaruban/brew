#!/usr/bin/env bash
set -euo pipefail

if [[ $# -ne 2 ]]; then
  echo "Usage: $0 <base-rev> <head-rev>" >&2
  exit 1
fi

base_rev="$1"
head_rev="$2"

echo "Checking docs-only diff for range: ${base_rev}..${head_rev}" >&2

has_changes=false

while IFS= read -r file; do
  [[ -z "${file}" ]] && continue
  has_changes=true

  case "${file}" in
    docs/*|*.md|*.MD)
      ;;
    *)
      echo "Non-doc change detected: ${file}" >&2
      echo "false"
      exit 0
      ;;
  esac
done < <(git diff --name-only "${base_rev}" "${head_rev}")

if [[ "${has_changes}" == "false" ]]; then
  echo "No changed files detected in diff; treating as non-doc change for safety." >&2
  echo "false"
  exit 0
fi

echo "All changed files are docs-only." >&2
echo "true"
