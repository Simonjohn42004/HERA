

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.hera12"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hera12"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    buildToolsVersion = "35.0.0"
    buildFeatures {
        mlModelBinding = true
    }
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    implementation(libs.tensorflow.lite.gpu)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.mpandroidchart.vv310)
    implementation(libs.tensorflow.lite)
    implementation (libs.firebase.storage)
    implementation (libs.onnxruntime.android)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp3.okhttp)
    implementation(libs.logging.interceptor)
    implementation (libs.glide.v4142)
    annotationProcessor (libs.compiler)
    implementation (libs.security.crypto)
    implementation (libs.gson)



}
