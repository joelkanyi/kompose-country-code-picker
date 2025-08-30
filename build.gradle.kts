plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.kotlin) apply false
    alias(libs.plugins.multiplatform) apply false
    alias(libs.plugins.jvm) apply false
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.spotless)
    alias(libs.plugins.kotlin.compatibility) apply false
    alias(libs.plugins.dokka)
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.nmcp.aggregation)
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            licenseHeaderFile(
                rootProject.file("${project.rootDir}/spotless/copyright.kt"),
                "^(package|object|import|interface)",
            )
            trimTrailingWhitespace()
            endWithNewline()

            ktlint().customRuleSets(
                listOf(
                    "io.nlopez.compose.rules:ktlint:0.4.26",
                ),
            )
        }
        format("kts") {
            target("**/*.kts")
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"), "(^(?![\\/ ]\\*).*$)")
        }
        format("misc") {
            target("**/*.md", "**/.gitignore")
            trimTrailingWhitespace()
            leadingTabsToSpaces()
            endWithNewline()
        }
    }
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaMultiModuleTask>().configureEach {
    outputDirectory.set(file("$rootDir/docs/kdoc"))
}

nmcpAggregation {
    centralPortal {
        username = System.getenv("MAVEN_CENTRAL_USERNAME")
        password = System.getenv("MAVEN_CENTRAL_PASSWORD")
        publishingType = "AUTOMATIC"
    }

    publishAllProjectsProbablyBreakingProjectIsolation()
}

dependencies {
    nmcpAggregation(project(":komposecountrycodepicker"))
}


