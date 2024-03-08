plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.elhady.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
    implementation(project(":core:base"))
    implementation(project(":feature:home:presentation:viewModel"))
    implementation(project(":feature:auth:presentation:ui"))

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Hilt
    implementation ("com.google.dagger:hilt-android:2.45")
    kapt ("com.google.dagger:hilt-compiler:2.45")

    // view model
    implementation ("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")

    // Lottie
    implementation ("com.airbnb.android:lottie:5.2.0")

    // Paging
    implementation("androidx.paging:paging-runtime:3.2.1")

    //glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.github.amoskorir:avatarimagegenerator:1.5.0")

    /// refresh-layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // recycler view decorator
    implementation("com.github.xabaras:RecyclerViewSwipeDecorator:1.4")

    //youtube player
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0")

}