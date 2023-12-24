@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "com.demo.converter"
    compileSdk = 34
    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }

    defaultConfig {
        applicationId = "com.demo.converter"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "BASE_URL", properties["BASE_URL"] as String)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
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

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.moshi)
    implementation(libs.moshi.codegen)
    implementation(libs.moshi.kotlin)
    implementation(libs.lifecycle.viewModel)
    implementation(libs.viewmodel.savedstate)
    implementation(libs.livedata)
    implementation(libs.lifecycle.runtime)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.datatheorom)
    implementation(libs.preference)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.constraint.layout)
    implementation(libs.navigation.compose)
    implementation(libs.constraintlayout)
    implementation(libs.room.runtime)
    implementation(libs.room)
    implementation(libs.androidx.material3)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)

    // Test
    implementation(libs.okhttp3.mockwebserver)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.mockito.kotlin)
    implementation(libs.mockito)
    implementation(libs.turbine)
    implementation(libs.koin.test.jUnit4)
    implementation(libs.koin.test)
    implementation(libs.androidx.compose.ui.test.junit4)
    implementation(libs.androidx.compose.ui.test.manifest)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
}