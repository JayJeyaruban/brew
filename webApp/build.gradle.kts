plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlinter)
}

kotlin {
    js {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":sharedUI"))
        }
    }
}

kotlinter {
    ktlintVersion = "1.8.0"
    ignoreFormatFailures = false
}

dependencies {
    add("ktlint", libs.compose.rules.ktlint)
}
