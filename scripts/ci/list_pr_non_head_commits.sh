#!/usr/bin/env bash
set -euo pipefail

if [[ $# -ne 2 ]]; then
  echo "Usage: $0 <base-rev> <head-rev>" >&2
  exit 1
fi

base_rev="$1"
head_rev="$2"

echo "Listing PR commits in ancestry path: ${base_rev}..${head_rev}" >&2

git rev-list --reverse --ancestry-path "${base_rev}..${head_rev}" \
  | sed '$d' \
  | deno run ./scripts/ci/format_commit_matrix.ts
