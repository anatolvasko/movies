plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "com.clearing.movies_data"
    compileSdk = ProjectConfig.COMPILE_SDK

    defaultConfig {
        minSdk = ProjectConfig.MIN_SDK

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(Modules.CORE_UI))
    implementation(project(Modules.MOVIES_DOMAIN))

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlin.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation ("com.google.dagger:hilt-android-testing:2.48")
    kaptTest ("com.google.dagger:hilt-compiler:2.48")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("androidx.test.ext:junit:1.1.3")
    kaptTest ("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")


    implementation(libs.network.retrofit)
    implementation(libs.network.gson)
    implementation(libs.network.okhttp)
    implementation(libs.network.logging.interceptor)

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)
    implementation(libs.google.hilt.navigation)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    kapt(libs.room.compiler)

    implementation(libs.paging)

}