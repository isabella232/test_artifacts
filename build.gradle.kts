import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.2.61"
}

group = "test_artifacts"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    // https://github.com/jcabi/jcabi-github/releases
    compile("com.jcabi:jcabi-github:0.41")
    compile("org.slf4j:slf4j-nop:1.7.25")
    testCompile("junit", "junit", "4.12")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "test.artifacts.GithubRelease"
}


