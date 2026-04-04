import com.android.build.api.dsl.ApplicationExtension

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

val abis = setOf("arm64-v8a")

configure<ApplicationExtension> {
    namespace = "io.github.dovecoteescapee.byedpi"
    compileSdk = 36

    defaultConfig {
        applicationId = "io.github.romanvht.byedpi"
        minSdk = 23
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1707
        versionName = "1.7.7"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters.addAll(abis)
        }
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        compose = true
    }

    buildTypes {
        release {
            buildConfigField("String", "VERSION_NAME",  "\"${defaultConfig.versionName}\"")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            isMinifyEnabled = true
            isShrinkResources = true
        }
        debug {
            buildConfigField("String", "VERSION_NAME",  "\"${defaultConfig.versionName}-debug\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lint {
        checkReleaseBuilds = false
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    // https://android.izzysoft.de/articles/named/iod-scan-apkchecks?lang=en#blobs
    dependenciesInfo {
        // Disables dependency metadata when building APKs.
        includeInApk = false
        // Disables dependency metadata when building Android App Bundles.
        includeInBundle = false
    }

    splits {
        abi {
            isEnable = true
            reset()
            include(*abis.toTypedArray())
            isUniversalApk = true
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.google.material)
    implementation(libs.google.gson)
    implementation(libs.androidx.compose.material3)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    // TV dependencies
    implementation(libs.androidx.tv.foundation)
    implementation(libs.androidx.tv.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}