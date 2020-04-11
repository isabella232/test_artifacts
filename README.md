# Test Artifacts

Test artifacts for Flank's test suite.

# Updating

- Create `Archive.zip` based on latest release
- `./gradlew run` uploads `Archive.zip` as the new release

# API Token

- Set the `GITHUB_TOKEN` environment variable to a GitHub personal access token with the `public_repo` scope
  - API issues typically mean the token is invalid or is missing the correct scope
