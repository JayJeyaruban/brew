import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinter)
}

android {
    namespace = "com.jayjeyaruban.brew.androidApp"
    compileSdk = 36

    defaultConfig {
        minSdk = 23
        targetSdk = 36

        applicationId = "com.jayjeyaruban.brew.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
}

kotlinter {
    ktlintVersion = "1.8.0"
    ignoreFormatFailures = false
}

dependencies {
    add("ktlint", libs.compose.rules.ktlint)
    implementation(project(":sharedUI"))
    implementation(libs.androidx.activityCompose)
}
