plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.quantumscan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.quantumscan"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures{
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    //implementation(file("/Users/zhiyangwang/Library/Android/sdk/platforms/android-34/android.jar"))

    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation ("org.robolectric:robolectric:4.7.3")

    implementation("com.journeyapps:zxing-android-embedded:4.2.0")
    implementation("androidx.fragment:fragment:1.3.6")
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")

    // FireStore dependencies
    implementation("com.google.firebase:firebase-firestore:24.10.2")
    implementation("com.google.firebase:firebase-bom:32.7.3")

    // QRCode dependencies
    implementation ("com.github.androidmads:QRGenerator:1.0.1")
    implementation("com.journeyapps:zxing-android-embedded:4.2.0")

    // Image picker dependencies

    // FireStore Test
    implementation ("org.mockito:mockito-core:4.0.0")

}