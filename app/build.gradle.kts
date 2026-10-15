plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

kotlin {
    jvmToolchain(25)
}

val abis = setOf("arm64-v8a", "x86")

android {
    namespace = "io.github.romanvht.byedpi"
    compileSdk = 37

    defaultConfig {
        applicationId = "io.github.romanvht.byedpi"
        minSdk = 24
        targetSdk = 37
        versionCode = 1709
        versionName = "1.7.9"
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

    lint {
        checkReleaseBuilds = false
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "4.1.2"
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
            //noinspection ChromeOsAbiSupport
            include(*abis.toTypedArray())
            isUniversalApk = true
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.google.material)
    implementation(libs.google.gson)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    // TV dependencies
    implementation(libs.androidx.tv.foundation)
    implementation(libs.androidx.tv.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

val buildHevSocks = tasks.register<Exec>("buildHevSocks") {
    group = "build"
    description = "Build libhev-socks5-tunnel.so via WSL NDK and copy into jniLibs"

    inputs.dir(layout.projectDirectory.dir("src/main/jni"))
        .withPropertyName("jniSources")
        .withPathSensitivity(PathSensitivity.RELATIVE)

    inputs.file(layout.projectDirectory.file("build.gradle.kts"))
        .withPropertyName("buildScript")
        .withPathSensitivity(PathSensitivity.RELATIVE)

    inputs.property("abis", "arm64-v8a x86")

    outputs.file(layout.projectDirectory.file("src/main/jniLibs/arm64-v8a/libhev-socks5-tunnel.so"))
        .withPropertyName("hevSoArm64")
    outputs.file(layout.projectDirectory.file("src/main/jniLibs/x86/libhev-socks5-tunnel.so"))
        .withPropertyName("hevSoX86")

    commandLine(
        "wsl.exe",
        "bash",
        "-lc",
        "~/BBD-build/build-hev.sh"
    )

    standardOutput = System.out
    errorOutput = System.err
}

tasks.matching { it.name == "preBuild" }.configureEach {
    dependsOn(buildHevSocks)
}