@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.javax.inject)
    implementation(libs.androidx.paging.common)
}