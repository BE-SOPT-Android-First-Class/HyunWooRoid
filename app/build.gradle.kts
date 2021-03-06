plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    compileSdk = Apps.compileSdk
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = Apps.pacakageName
        minSdk = Apps.minSdk
        targetSdk = Apps.targetSdk
        versionCode = Apps.versionCode
        versionName = Apps.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Versions.java_version
        targetCompatibility = Versions.java_version
    }
    kotlinOptions {
        jvmTarget = Versions.jvm_version
    }

    externalNativeBuild {
        cmake {
            path("src/main/jni/CMakeLists.txt")
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(KotlinDependencies.kotlin)
    implementation(AndroidXDependencies.appCompat)
    implementation(AndroidXDependencies.coreKtx)
    implementation(MaterialDesignDependencies.materialDesign)
    implementation(AndroidXDependencies.constraintLayout)

    // Hilt
    implementation(AndroidXDependencies.hilt)
    kapt(KaptDependencies.hilt)
    kapt(KaptDependencies.hiltAndroid)

    // DataStore
    implementation(AndroidXDependencies.dataStore)
    implementation(AndroidXDependencies.dataStoreCore)

    // Android KTX
    implementation(AndroidXDependencies.fragmentKtx)
    implementation(AndroidXDependencies.activityKtx)
    implementation(AndroidXDependencies.viewModelKtx)

    // Glide
    implementation(ThirdPartyDependencies.glide)
    kapt(KaptDependencies.glideCompiler)

    // Dagger2
//    implementation(ThirdPartyDependencies.dagger)
//    implementation(ThirdPartyDependencies.daggerAndroid)
//    implementation(ThirdPartyDependencies.daggerAndroidSupport)
//    kapt(KaptDependencies.dagger)
//    kapt(KaptDependencies.daggerAndroidCompiler)

    // Navigation
    implementation(AndroidXDependencies.navigation)
    implementation(AndroidXDependencies.navigationFragment)

    // Gson
    implementation(ThirdPartyDependencies.gson)

    // Okhttp
    implementation(platform(ThirdPartyDependencies.okhttpBOM))
    implementation(ThirdPartyDependencies.okhttp)
    implementation(ThirdPartyDependencies.okhttpInterceptor)

    // Retrofit
    implementation(ThirdPartyDependencies.retrofit)
    implementation(ThirdPartyDependencies.retrofitGsonConverter)

    // Reactive Extensions(Rx)
    implementation(ThirdPartyDependencies.rxJava)
    implementation(ThirdPartyDependencies.rxAndroid)

    // Androidx Security
    implementation(AndroidXDependencies.security)

    // Biometric
    implementation(AndroidXDependencies.biometric)

    // Kotlinx-Serialization
    implementation(AndroidXDependencies.kotlinxSerialization)

    testImplementation(TestDependencies.jUnit)
    androidTestImplementation(TestDependencies.androidTest)
    androidTestImplementation(TestDependencies.espresso)
}
