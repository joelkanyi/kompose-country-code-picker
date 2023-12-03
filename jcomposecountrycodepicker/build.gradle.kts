/*
 * Copyright 2023 Joel Kanyi.
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
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.joelkanyi.jcomposecountrycodepicker"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
    publishing {
        singleVariant("release")
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
}

dependencies {
    implementation(libs.material3)
    implementation(libs.libphonenumber)
    implementation(libs.core.ktx)
}

afterEvaluate {
    with(publishing) {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.joelkanyi"
                artifactId = "kompose-country-code-picker"
                version = "1.0.0"
                from(components["release"])
            }
        }
    }
}