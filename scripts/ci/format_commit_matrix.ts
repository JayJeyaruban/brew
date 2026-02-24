// GitHub Actions expands strategy.matrix from this object shape, using `include`
// entries as explicit matrix rows: https://docs.github.com/en/actions/using-jobs/using-a-matrix-for-your-jobs
type CommitMatrix = {
  include: Array<{ sha: string; short: string }>;
};

async function main() {
  const commits = await readStdinLines();
  const matrix: CommitMatrix = {
    include: commits.map((sha) => ({ sha, short: sha.slice(0, 7) })),
  };
  const hasCommits = commits.length > 0 ? "true" : "false";

  console.error(`Non-head commits to verify: ${commits.length}`);
  for (const sha of commits) {
    console.error(`- ${sha}`);
  }

  const payload = [
    `has_commits=${hasCommits}`,
    "matrix_json<<EOF",
    JSON.stringify(matrix),
    "EOF",
    "",
  ].join("\n");
  await Deno.stdout.write(encoder.encode(payload));
}

async function readStdinLines(): Promise<string[]> {
  const stdinText = await new Response(Deno.stdin.readable).text();
  return stdinText
    .split("\n")
    .map((line) => line.trim())
    .filter(Boolean);
}

const encoder = new TextEncoder();

await main();
