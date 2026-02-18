import java.net.URI
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier

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

            ktlint(
                "1.8.0"
            ).customRuleSets(
                listOf(
                    "io.nlopez.compose.rules:ktlint:0.5.6",
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

dokka {
    moduleName.set("Kompose Country Code Picker")

    dokkaPublications.html {
        // Output all aggregated docs here
        outputDirectory.set(rootDir.resolve("docs/kdoc"))
        suppressInheritedMembers.set(true)
        failOnWarning.set(true)
    }

    dokkaSourceSets.configureEach {
        includes.from("README.md")
        documentedVisibilities.set(setOf(VisibilityModifier.Public))

        sourceLink {
            localDirectory.set(file("src/main/kotlin"))
            remoteUrl.set(URI("https://github.com/joelkanyi/kompose-country-code-picker"))
            remoteLineSuffix.set("#L")
        }

        externalDocumentationLinks.register("kotlin") {
            url.set(URI("https://kotlinlang.org/api/latest/jvm/stdlib/"))
        }
    }
}

dependencies {
    dokka(project(":komposecountrycodepicker"))
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


