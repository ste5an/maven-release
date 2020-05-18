# SpringBoot Application: Maven Release Plugin Example
The following repository contains an example of a SpringBoot application that makes use of the Maven Release Plugin to deploy snapshot versions to a Snapshot Repository on PackageCloud and also the release artifacts within the Release Repository on PackageCloud. 

## Maven Release Plugin

#### Goal: Prepare Release
The goal release:prepare prepares for a release in SCM. Preparing a release goes through the following release phases: 
* Check that there are no uncommitted changes in the sources
* Check that there are no SNAPSHOT dependencies
* Change the version in the POMs from x-SNAPSHOT to a new version (you will be prompted for the versions to use)
* Transform the SCM information in the POM to include the final destination of the tag
* Run the project tests against the modified POMs to confirm everything is in working order
* Commit the modified POMs
* Tag the code in the SCM with a version name (this will be prompted for)
* Bump the version in the POMs to a new value y-SNAPSHOT (these values will also be prompted for)
* Commit the modified POMs 

```bash
$ mvn clean release:prepare
```

#### Goal: Perform Release
The goal release:perform performs a release from SCM, either from a specified tag, or the tag representing the previous release in the working copy created by release:prepare. Performing a release runs the following release phases:
* Checkout from an SCM URL with optional tag
* Run the predefined Maven goals to release the project (by default, deploy site-deploy)

```bash
$ mvn clean release:perform
```

#### Goal: Rollback Release
The goal release:rollback rollbacks changes made by a previous release. This requires that the previous release descriptor release.properties is still available in the local working copy.

```bash
$ mvn clean release:rollback
```

#### Goal: Clean Release
The goal release:clean cleans up after a release preparation. This is done automatically after a successful release:perform, so is best served for cleaning up a failed or abandoned release, or a dry run.

```bash
$ mvn clean release:clean
```

## PackageCloud Repositories
A software repository, or “repo” for short, is a storage location for software packages. PackageCloud is a software repository that supports most popular package types, from Java to Python to Ruby and Node, and more. 

For this example, I will use PackageCloud as a maven repository to store the snapshot and release artifacts of this project.

To setup a free account on PackageCloud, use the following [link][0].

The following two repositories were created to host the snapshot artifacts and the release artifacts.
* [Java Nibble Snapshot Repository][1] 
* [Java Nibble Release Repository][2] 

## Maven Setup
After you have created a Snapshot & Release repository on PackageCloud, it is important also to setup maven to connect to these repositories. PackageCloud provides an **API-Token** that is used for maven to authenticate with PackageCloud.

**Step 1:** Add the API Token to the maven settings file located at $HOME/.m2/settings.xml
```xml
<server>
        <id>packagecloud.snapshot</id>
        <password>{{ PASTE API TOKEN HERE }}</password>
</server>
<server>
        <id>packagecloud.release</id>
        <password>{{ PASTE API TOKEN HERE }}</password>
</server>
```

**Step 2:** Add the dependency to the <build/> section of your project'spom.xml.
```xml
<build>
  <extensions>
    <extension>
      <groupId>io.packagecloud.maven.wagon</groupId>
      <artifactId>maven-packagecloud-wagon</artifactId>
      <version>0.0.6</version>
    </extension>
  </extensions>
</build>
```

**Step 3:** Configure your project's <distributionManagement/> to point to your packagecloud.io repository,
```xml
<distributionManagement>
    <repository>
        <id>packagecloud.release</id>
        <url>packagecloud+https://packagecloud.io/javanibble/release</url>
    </repository>
    <snapshotRepository>
        <id>packagecloud.snapshot</id>
        <url>packagecloud+https://packagecloud.io/javanibble/snapshot</url>
    </snapshotRepository>
</distributionManagement>
```

## Compile and Run the Application
Use the following command to compile the SpringBoot application and deploy the snapshot version of the JAR artifact to the PackageCloud snapshot repository. 

#### Build & Deploy Application

The following command will build the SpringBoot application and then deploy the snapshot version to the PackageCloud [Java Nibble Snapshot Repository][1].
```bash
$ mvn clean deploy
```

#### Release Application

The following command will run maven in batch mode and will not accept input from the user. It will run the release:prepare and release:perform goals.
```bash
$ mvn clean --batch-mode release:prepare release:perform
```

The following command will run maven in batch mode passing the Release Version and Development Version as system properties. It also specify the tag name in your SCM.
```bash
$ mvn clean --batch-mode release:prepare -Dtag=SpringBootMavenReleasePlugin-1.0.0 -DreleaseVersion=1.0.0 -DdevelopmentVersion=1.0.1-SNAPSHOT
```

## Summary
Congratulations! You have successfully deployed the snapshot artifacts to a snapshot repo and also performed a release and deployed the released artifact to a release repo. Follow me on any of the different social media platforms and feel free to leave comments.



[0]:https://packagecloud.io/users/new?plan=free_usage_plan
[1]:https://packagecloud.io/javanibble/snapshot
[2]:https://packagecloud.io/javanibble/release