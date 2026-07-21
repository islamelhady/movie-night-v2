@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.elhady.movies.core.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

}

dependencies {
    implementation(project(":core:common"))

    // Image Loading
    api(libs.glide)

    // Lottie Animations
    api(libs.lottie)

    // Paging (for BaseFooterAdapter)
    implementation(libs.androidx.paging.runtime)

    // Recycler Swipe Decorator
    implementation(libs.recyclerview.swipe.decorator)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // AndroidX & Material Design
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}