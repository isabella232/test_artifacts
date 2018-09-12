# test_artifacts

Test artifacts for Flank. These are used to develop Flank.

# updating

- Create `archive.zip` based on latest release
- `gradle run` uploads `archive.zip` as the new release

# API token

- Set the `GITHUB_TOKEN` environment variable to a GitHub personal access token with the `public_repo` scope
  - API issues typically mean the token is invalid or is missing the correct scope
