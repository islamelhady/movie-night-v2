import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
}

android {
    namespace = "com.elhady"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    /// viewModel
//    implementation("androidx.fragment:fragment-ktx:$androidxFragmentVersion")

    // Lottie
    implementation ("com.airbnb.android:lottie:5.2.0")

    /// glide
    implementation("com.github.bumptech.glide:glide:4.15.1")

    /// splash
//    implementation("androidx.core:core-splashscreen:1.0.1")


    // Hilt
//    implementation ("com.google.dagger:hilt-android:2.45")
//    kapt ("com.google.dagger:hilt-compiler:2.45")

    implementation ("androidx.fragment:fragment-ktx:1.6.1")
    implementation ("androidx.activity:activity-ktx:1.7.2")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}