import org.gradle.internal.impldep.com.google.api.client.googleapis.testing.auth.oauth2.MockGoogleCredential.ACCESS_TOKEN

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "com.clearing"
    compileSdk = ProjectConfig.COMPILE_SDK

    defaultConfig {
        applicationId = "com.clearing"
        minSdk = ProjectConfig.MIN_SDK
        targetSdk = ProjectConfig.TARGET_SDK
        versionCode = ProjectConfig.VERSION_CODE
        versionName = ProjectConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig= false
    }
}

dependencies {
    implementation(project(Modules.CORE_UI))

    implementation(project(Modules.MOVIES_DATA))
    implementation(project(Modules.MOVIES_DOMAIN))
    implementation(project(Modules.MOVIES_PRESENTATION))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.coroutines.android)

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)
    implementation(libs.google.hilt.navigation)

    implementation(libs.androidx.splash)
}