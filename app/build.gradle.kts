plugins {
    alias(libs.plugins.androidApplication)
    id("realm-android")
}


android {
    namespace = "com.example.getsavego"
    compileSdk = 34
    buildFeatures{
        viewBinding = true
    }

//    viewBinding {
//        enable = true
//    }

    defaultConfig {
        applicationId = "com.example.getsavego"
        minSdk = 27
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
realm{
    isSyncEnabled = true
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.10")
//    implementation ("io.realm:realm-android-library:10.11.1")
//    implementation ("io.realm:realm-android-sync:10.11.1")
    implementation("io.realm.kotlin:library-sync:1.6.0")
    implementation ("com.github.AnyChart:AnyChart-Android:1.1.5")
    implementation ("jp.wasabeef:blurry:4.0.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
