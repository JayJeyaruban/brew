import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kreplica)
    alias(libs.plugins.kotlinter)
}

kotlin {
    androidLibrary {
        namespace = "com.jayjeyaruban.brew"
        compileSdk = 36
        minSdk = 23
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3ExpressiveApi")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
        }

        commonMain.dependencies {
            api(libs.compose.runtime)
            api(libs.compose.ui)
            api(libs.compose.foundation)
            api(libs.compose.resources)
            api(libs.compose.ui.tooling.preview)
            api(libs.compose.material3)
            implementation(libs.kermit)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.serialization.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.multiplatformSettings)
            implementation(libs.kotlinx.datetime)
            implementation(libs.materialKolor)

            implementation(libs.sqlDelight.coroutines.extension)

            implementation(libs.jetbrains.navigation3.ui)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.compose.ui.test)
            implementation(libs.kotlinx.coroutines.test)
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqlDelight.driver.android)

            implementation(libs.compose.ui.tooling)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqlDelight.driver.native)
        }

    }

    targets
        .withType<KotlinNativeTarget>()
        .matching { it.konanTarget.family.isAppleFamily }
        .configureEach {
            binaries {
                framework {
                    baseName = "SharedUI"
                    isStatic = false
                }
            }
        }
}

buildConfig {
    // BuildConfig configuration here.
    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}

kotlinter {
    ktlintVersion = "1.8.0"
    ignoreFormatFailures = false
}

dependencies {
    add("ktlint", libs.compose.rules.ktlint)
}

sqldelight {
    databases {
        create("BrewDatabase") {
            // Database configuration here.
            // https://cashapp.github.io/sqldelight
            packageName.set("com.jayjeyaruban.brew.data.database")
        }
    }
}
