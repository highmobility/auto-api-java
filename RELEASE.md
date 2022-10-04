* update ext.ver values in *all* build.gradle or use -Pversion property. search for `hmkit-auto-api`
* publish to sonatype staging  `./gradlew :auto-api-java:publishReleasePublicationToMavenCentralRepository`
* release to mavencentral manually in `https://oss.sonatype.org/#stagingRepositories`
  * delete utils from the Content tab there if they were not updated
  * press Close
  * refresh until Activity processes are finished
  * press Release
  * Release is available in 15 minutes. However, it is not searchable in the [search.maven.org](https://search.maven.org) for a little while. 
* update CHANGELOG.md and create a release from tag in GitHub. Auto generate release notes.