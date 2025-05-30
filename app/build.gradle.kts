plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.fitnessgym"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fitnessgym"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.squareup.retrofit2:retrofit:2.1.0")
    implementation("com.squareup.retrofit2:converter-gson:2.1.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("mysql:mysql-connector-java:5.1.49")
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation ("io.reactivex.rxjava3:rxjava:3.0.0")
    implementation ("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")
    implementation ("com.google.code.gson:gson:2.8.8")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    // For control over item selection of both touch and mouse driven selection
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.google.firebase:firebase-auth:21.0.1")
    implementation ("androidx.security:security-crypto:1.1.0-alpha06")


}