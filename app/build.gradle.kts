@file:Suppress("DSL_SCOPE_VIOLATION")
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.navigation.safeargs)
}

android {
    namespace = "com.elhady.movies"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.elhady.movies"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildTypes {
            getByName("debug") {
                buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
                buildConfigField("String", "BASE_URL", "\"${properties.getProperty("BASE_URL")}\"")
                buildConfigField("String", "IMAGE_BASE_PATH", "\"${properties.getProperty("IMAGE_BASE_PATH")}\"")
                buildConfigField("String", "TMDB_SIGNUP_URL", "\"${properties.getProperty("TMDB_SIGNUP_URL")}\"")
            }
            getByName("release") {
                buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
                buildConfigField("String", "BASE_URL", "\"${properties.getProperty("BASE_URL")}\"")
                buildConfigField("String", "IMAGE_BASE_PATH", "\"${properties.getProperty("IMAGE_BASE_PATH")}\"")
                buildConfigField("String", "TMDB_SIGNUP_URL", "\"${properties.getProperty("TMDB_SIGNUP_URL")}\"")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
    }
    kotlinOptions {
        jvmTarget = libs.versions.javaVersion.get()
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    // Core Modules
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:data"))

    // Feature Modules
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:search"))
    implementation(project(":feature:details"))
    implementation(project(":feature:watchlist"))
    implementation(project(":feature:player"))
    implementation(project(":feature:explore"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:tvshow"))
    implementation(project(":feature:showmore"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.activity.ktx)

    // navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // okhttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Coil
    implementation(libs.coil)

    // recycler
    implementation(libs.androidx.recyclerview)

    // refresh-layout
    implementation(libs.androidx.swiperefreshlayout)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //youtube player
    implementation(libs.youtube.player)

    // Expandable TextView
    implementation(libs.expandable.textview)

    // splash
    implementation(libs.androidx.core.splashscreen)

}