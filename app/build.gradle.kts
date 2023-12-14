plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.app.muscu3000"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.muscu3000"
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
    buildFeatures {
        viewBinding = true
    }
//    testOptions {
//        unitTests {
//            isIncludeAndroidResources = true
//        }
//    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.6.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("androidx.room:room-common:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")
    implementation("androidx.test:core-ktx:1.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    val roomVersion = "2.6.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    testImplementation("junit:junit:4.13.2")
    testImplementation ("androidx.test.ext:junit:1.1.5")
    testImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    testImplementation("junit:junit:4.13.2") // or the latest version
    testImplementation("org.mockito:mockito-core:3.12.4") // or the latest version

    // For testing with LiveData
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // For testing with coroutines
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

    // Add the following if you are using the Android Testing Support Library
    testImplementation ("androidx.test:core:1.5.0")
    testImplementation ("androidx.test.ext:junit:1.1.5") // or the latest version
    testImplementation ("org.mockito:mockito-android:3.12.4") // or the latest version
    testImplementation ("org.robolectric:robolectric:4.7.3")

}