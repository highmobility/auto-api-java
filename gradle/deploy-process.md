# Public release

## Steps
- update CHANGELOG.md. Use version that has incremented the patch number by 1.
- merge a pull request to main. This creates a new tag with incremented patch version.
- create a release from this tag in GitHub. Use `Generate release notes`
    - Action starts that pushes the package to MavenCentral.
    - You can check OSSRH whether release was successful or not.

### Version incrementing
Only patch number is incremented automatically on merge. To update major/minor, you need to update the version in gradle.properties manually before merge. Patch number is still incremented.

## Make a test release locally to staging

- comment out line `useInMemoryPgpKeys(signingKey, signingPassword)` in deploy-ossrh.gradle
- Update version in `$projectRoot/gradle.properties` and call `./gradlew -Prelease :auto-api-java:publishToSonatype`.
- Don't merge test version names to main