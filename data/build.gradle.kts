plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.vodafone.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.room.compiler)
    implementation(project(":core"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.gson)

    implementation(libs.androidx.room.runtime)
    kapt("androidx.room:room-compiler:2.7.0")  // Annotation processor

    // Kotlin Extensions and Coroutines support
    implementation(libs.androidx.room.ktx)  // Coroutine support

    // Hilt Core (All modules need this)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Hilt Navigation (For ViewModel injection)
    implementation(libs.androidx.hilt.navigation.compose)
}