plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.room")
    id("kotlinx-serialization")
}

android {
    namespace = "com.vodafone.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        buildConfigField("String", "API_KEY", "BuildConfig.API_KEY")
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

}

dependencies {
    implementation(project(":core"))

//    kapt("androidx.room:room-compiler:2.7.0")  // Annotation processor
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

}