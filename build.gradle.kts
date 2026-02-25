import org.jmailen.gradle.kotlinter.tasks.FormatTask
import org.jmailen.gradle.kotlinter.tasks.LintTask

plugins {
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.compose.multiplatform).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.kmp.library).apply(false)
    alias(libs.plugins.kotlinx.serialization).apply(false)
    alias(libs.plugins.sqlDelight).apply(false)
    alias(libs.plugins.buildConfig).apply(false)
    alias(libs.plugins.kotlinter).apply(false)
}

val isCi = System.getenv("CI")?.equals("true", ignoreCase = true) == true
val autoFormatOnBuildEnabled =
    providers.gradleProperty("kotlinter.autoFormatOnBuild")
        .orNull
        ?.toBooleanStrictOrNull()
        ?: !isCi

subprojects {
    pluginManager.withPlugin("org.jmailen.kotlinter") {
        tasks.withType<LintTask>().configureEach {
            exclude { it.file.invariantSeparatorsPath.contains("/build/") }
        }
        tasks.withType<FormatTask>().configureEach {
            exclude { it.file.invariantSeparatorsPath.contains("/build/") }
        }

        if (autoFormatOnBuildEnabled) {
            // Local/dev UX: format before Android and KMP compile lifecycles start.
            tasks
                .matching {
                    it.name == "preBuild" || it.name == "checkKotlinGradlePluginConfigurationErrors"
                }
                .configureEach {
                    dependsOn("formatKotlin")
                }
        }
    }
}
