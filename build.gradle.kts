// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.hilt.plugin)
        classpath(libs.gradle)
        classpath(libs.kotlin)
        classpath(libs.google.services)
    }
}

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
}

detekt {
    config = files("$projectDir/config/detekt.yml")
}

allprojects {
    // Version should be inherited from parent

    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
