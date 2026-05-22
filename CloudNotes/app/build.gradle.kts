plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.ayush.cloudnotes"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.ayush.cloudnotes"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("com.amazonaws:aws-android-sdk-core:2.73.0")
    implementation("com.amazonaws:aws-android-sdk-cognitoidentityprovider:2.73.0")
    implementation("com.amazonaws:aws-android-sdk-ddb:2.73.0")
    implementation("com.amazonaws:aws-android-sdk-ddb-mapper:2.73.0")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
}