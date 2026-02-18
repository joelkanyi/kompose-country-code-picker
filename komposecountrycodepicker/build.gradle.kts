/*
 * Copyright 2024 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compatibility)
    alias(libs.plugins.dokka)
    alias(libs.plugins.nmcp.aggregation)
    alias(libs.plugins.gradleMavenPublish)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    applyDefaultHierarchyTemplate()

    explicitApi() // https://kotlinlang.org/docs/whatsnew14.html#explicit-api-mode-for-library-authors

    // for strict mode
    explicitApi = ExplicitApiMode.Strict

    // for warning mode
    explicitApi = ExplicitApiMode.Warning

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
        publishLibraryVariants("release")

        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)

        dependencies {
            androidTestImplementation(libs.androidx.ui.test.junit4.android)
            debugImplementation(libs.androidx.ui.test.manifest)
        }
    }

    jvm()

    js {
        browser()
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material3)
        }

        // Adds common test dependencies
        commonTest.dependencies {
            implementation(kotlin("test"))

            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }

        // Adds the desktop test dependency
        jvmTest.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

dependencies {
    implementation(libs.material3)
    implementation(libs.libphonenumber)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.truth)
}

android {
    namespace = "com.joelkanyi.jcomposecountrycodepicker"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
}

group = "io.github.joelkanyi"
version = properties["version"] as String


mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    pom {
        name.set("KomposeCountryCodePicker")
        description.set("Kompose Country Code Picker is a Jetpack Compose library based on Material 3 (M3) that provides a country code picker for Android apps.")
        url.set("https://github.com/joelkanyi/KomposeCountryCodePicker")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        issueManagement {
            system.set("GitHub Issues")
            url.set("https://github.com/joelkanyi/KomposeCountryCodePicker/issues")
        }

        developers {
            developer {
                id.set("joelkanyi")
                name.set("Joel Kanyi")
                email.set("joelkanyi98@gmail.com")
            }
        }

        scm {
            connection.set("scm:git:git://github.com:joelkanyi/KomposeCountryCodePicker.git")
            developerConnection.set("scm:git:ssh://github.com:joelkanyi/KomposeCountryCodePicker.git")
            url.set("https://github.com/joelkanyi/KomposeCountryCodePicker")
        }
    }
}
