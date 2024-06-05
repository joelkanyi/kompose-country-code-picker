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
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compatibility)
    alias(libs.plugins.dokka)
    alias(libs.plugins.nmcp)
    alias(libs.plugins.gradleMavenPublish)
}

android {
    namespace = "com.joelkanyi.jcomposecountrycodepicker"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    kotlin {
        // for strict mode
        explicitApi = ExplicitApiMode.Strict

        // for warning mode
        explicitApi = ExplicitApiMode.Warning
    }
}

dependencies {
    implementation(libs.material3)
    implementation(libs.libphonenumber)
    implementation(libs.core.ktx)
}

group = "io.github.joelkanyi"
version = properties["version"] as String


mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

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

nmcp {
    publishAllPublications {
        username = System.getenv("MAVEN_CENTRAL_USERNAME")
        password = System.getenv("MAVEN_CENTRAL_PASSWORD")
        publicationType = "AUTOMATIC"
    }
}