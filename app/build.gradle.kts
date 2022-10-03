plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

apply(plugin = "org.jlleitschuh.gradle.ktlint")
apply(plugin = "io.gitlab.arturbosch.detekt")

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.finalfinalspace"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    // Optionally configure plugin
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
    }
}

dependencies {

    implementation(libs.ktx)
    implementation(libs.material)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.swipe.to.refresh)
    implementation(libs.recycler.view)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.moshi)
    implementation(libs.moshi.converter)
    implementation(libs.coil)
    implementation(libs.room.runtime)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.room)
    implementation(libs.glide)
    implementation(libs.timber)
    implementation(libs.hilt)
    implementation(libs.preference)
    implementation(libs.firebase)

    kapt(libs.room.compiler)
    kapt(libs.glide.compiler)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.junit.ext)
    testImplementation(libs.test.core)
    testImplementation(libs.mockito)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)

    androidTestImplementation(libs.android.test.runner)
    androidTestImplementation(libs.android.test.rules)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
}
