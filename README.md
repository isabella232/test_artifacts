# test_artifacts

Test artifacts for Flank. These are used to develop Flank.

# updating

- Create `archive.zip` based on latest release
- `gradle run` updates the GitHub release with the latest fixtures

# API token

- Set the `GITHUB_TOKEN` environment variable to a GitHub personal access token with the `public_repo` scope
  - API issues typically mean the token is invalid or is missing the correct scope
