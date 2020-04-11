import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.3.71"
}

group = "test_artifacts"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    // https://github.com/jcabi/jcabi-github/releases
    implementation("com.jcabi:jcabi-github:1.0")
    implementation("org.slf4j:slf4j-nop:1.7.25")
    testImplementation("junit", "junit", "4.12")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "test.artifacts.GithubRelease"
}
