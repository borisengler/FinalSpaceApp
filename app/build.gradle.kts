plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
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

    val version_ktx = "1.9.0"
    val version_material = "1.6.1"
    val version_navigation = "2.5.2"
    val version_swipe_to_refresh = "1.1.0"
    val version_recycler_view = "1.2.1"
    val version_lifecycle = "2.5.1"
    val version_moshi = "1.13.0"
    val version_retrofit = "2.9.0"
    val version_coil = "1.1.1"
    val version_room = "2.4.3"
    val version_okhttp = "4.10.0"
    val version_glide = "4.13.2"
    val version_timber = "5.0.1"
    val hiltVersion = "2.44"

    val version_junit = "4.13.2"
    val version_junit_ext = "1.1.3"
    val version_test_core = "1.4.0"
    val version_mockito = "4.8.0"
    val version_mockito_kotlin = "4.0.0"
    val version_mockk = "1.13.1"
    val version_coroutines_test = "1.6.4"

    val version_android_test_runner = "1.4.0"
    val version_android_test_rules = "1.4.0"
    val version_espresso = "3.4.0"
    val version_preference = "1.2.0"

    // Kotlin
    implementation("androidx.core:core-ktx:$version_ktx")
    implementation("com.google.android.material:material:$version_material")
    implementation("androidx.navigation:navigation-fragment-ktx:$version_navigation")
    implementation("androidx.navigation:navigation-ui-ktx:$version_navigation")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:$version_swipe_to_refresh")
    implementation("androidx.recyclerview:recyclerview:$version_recycler_view")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$version_lifecycle")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$version_lifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$version_lifecycle")
    implementation("com.squareup.moshi:moshi-kotlin:$version_moshi")
    implementation("com.squareup.retrofit2:converter-moshi:$version_retrofit")
    implementation("io.coil-kt:coil:$version_coil")
    implementation("androidx.room:room-runtime:$version_room")
    implementation("com.squareup.okhttp3:logging-interceptor:$version_okhttp")
    implementation("com.squareup.retrofit2:retrofit:$version_retrofit")
    implementation("androidx.room:room-ktx:$version_room")
    implementation("com.github.bumptech.glide:glide:$version_glide")
    implementation("com.jakewharton.timber:timber:$version_timber")
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    implementation("androidx.preference:preference-ktx:$version_preference")

    kapt("androidx.room:room-compiler:$version_room")
    kapt("com.github.bumptech.glide:compiler:$version_glide")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    testImplementation("junit:junit:$version_junit")
    testImplementation("androidx.test.ext:junit:$version_junit_ext")
    testImplementation("androidx.test:core-ktx:$version_test_core")
    testImplementation("org.mockito:mockito-core:$version_mockito")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$version_mockito_kotlin")
    testImplementation("io.mockk:mockk:$version_mockk")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$version_coroutines_test")

    androidTestImplementation("androidx.test:runner:$version_android_test_runner")
    androidTestImplementation("androidx.test:rules:$version_android_test_rules")
    androidTestImplementation("androidx.test.ext:junit:$version_junit_ext")
    androidTestImplementation("androidx.test.espresso:espresso-core:$version_espresso")
}
