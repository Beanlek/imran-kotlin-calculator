plugins {
    alias(libs.plugins.androidApplication)
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.imrandev.mycalculator"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.imrandev.mycalculator"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    dataBinding { enable = true }
}

@Suppress("UseTomlInstead")
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Lifecycle dependency -- 2.8.4
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")

    // Activity dependency -- 1.9.1
    implementation("androidx.activity:activity:1.9.1")

    // SQLite dependencies -- 2.4.0
    implementation("androidx.sqlite:sqlite:2.4.0")
    implementation("androidx.sqlite:sqlite-ktx:2.4.0")
    implementation("androidx.sqlite:sqlite-framework:2.4.0")

    // Room dependencies -- 2.6.1
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Kodein dependency -- 7.22.0
    implementation("org.kodein.di:kodein-di:7.22.0")
}