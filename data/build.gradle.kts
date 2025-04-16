plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.room")
    id("kotlinx-serialization")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.vodafone.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        buildConfigField("String", "API_KEY", project.properties["API_KEY"].toString())
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kapt {
        correctErrorTypes = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
    android.buildFeatures.buildConfig = true
}

dependencies {
    implementation(project(":core"))

    kapt(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.gson)

    implementation(libs.androidx.room.runtime)
    // Kotlin Extensions and Coroutines support
    implementation(libs.androidx.room.ktx)  // Coroutine support

    // Hilt Core (All modules need this)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Hilt Navigation (For ViewModel injection)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.retrofit.kotlin.serialization)

    // Core testing
    testImplementation(libs.junit)
    testImplementation(libs.androidx.core.testing)

    // Coroutines testing
    testImplementation(libs.kotlinx.coroutines.test)

    // Mocking (MockK recommended)
    testImplementation(libs.mockk)

    // Truth for assertions
    testImplementation(libs.truth)

    //local library
    implementation("com.zek:weather-utils:1.0.0")
}