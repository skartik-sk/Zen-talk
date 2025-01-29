plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.zen_talk"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.zen_talk"
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
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    sourceSets {
        getByName("main") {
            res {
                srcDirs("src/main/res", "src/main/res/layouts/chat_act")
            }
        }
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")

    implementation("com.google.firebase:firebase-analytics-ktx")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.core:core-splashscreen:1.0.1")  // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.4.0"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-storage")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.google.android.gms:play-services-safetynet:18.0.1")
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.facebook.android:facebook-android-sdk:latest.release")
    implementation("com.vanniktech:emoji-google:0.15.0")

    implementation ("com.google.firebase:firebase-messaging:23.3.1")
//// Coroutines
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
//
//// Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:retrofit:2.6.2")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

        // ...

        // Import the Firebase BoM
        implementation(platform("com.google.firebase:firebase-bom:32.7.2"))

        // When using the BoM, you don't specify versions in Firebase library dependencies

        // Add the dependency for the Firebase SDK for Google Analytics
        implementation("com.google.firebase:firebase-analytics")

        // TODO: Add the dependencies for any other Firebase products you want to use
        // See https://firebase.google.com/docs/android/setup#available-libraries
        // For example, add the dependencies for Firebase Authentication and Cloud Firestore
        implementation("com.google.firebase:firebase-auth")
        implementation("com.google.firebase:firebase-firestore")


}