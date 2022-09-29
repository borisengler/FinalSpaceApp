// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val hiltVersion = "2.44"
    val gradleVersion = "7.3.0"
    val kotlinVersion = "1.7.10"

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
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
