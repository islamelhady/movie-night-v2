@file:Suppress("DSL_SCOPE_VIOLATION")
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.elhady.movies.feature.auth"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val properties = Properties()
        val localPropertiesFile = project.rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            properties.load(localPropertiesFile.inputStream())
        }

        buildTypes {
            getByName("debug") {
                buildConfigField("String", "TMDB_SIGNUP_URL", "\"${properties.getProperty("TMDB_SIGNUP_URL")}\"")
                buildConfigField("String", "IMAGE_BASE_PATH", "\"${properties.getProperty("IMAGE_BASE_PATH")}\"")
            }
            getByName("release") {
                buildConfigField("String", "TMDB_SIGNUP_URL", "\"${properties.getProperty("TMDB_SIGNUP_URL")}\"")
                buildConfigField("String", "IMAGE_BASE_PATH", "\"${properties.getProperty("IMAGE_BASE_PATH")}\"")
            }
        }
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
        buildConfig = true
    }

}

dependencies {
    // Core Modules Dependencies
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:datastore"))
    implementation(project(":core:ui"))

    // Navigation & Lifecycle
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Retrofit
    implementation(libs.retrofit)
}
