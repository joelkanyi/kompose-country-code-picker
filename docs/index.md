# Kompose Country Code Picker

A Compose Multiplatform country code picker built with Material 3 for **Android**, **iOS**, **Desktop (JVM)**, **JS Browser**, and **WasmJS Browser**.

## Preview

<div class="grid" markdown>

| Android Picker | Android Dialog |
|:--------------:|:--------------:|
| ![Android Picker](./assets/android-picker.png){: style="height:350px;width:250px"} | ![Android Dialog](./assets/android-dialog.png){: style="height:350px;width:250px"} |

| iOS Picker | iOS Dialog |
|:----------:|:----------:|
| ![iOS Picker](./assets/ios-picker.png){: style="height:350px;width:250px"} | ![iOS Dialog](./assets/ios-dialog.png){: style="height:350px;width:250px"} |

| Desktop Picker | Desktop Dialog |
|:--------------:|:--------------:|
| ![Desktop Picker](./assets/desktop-picker.png){: style="max-height:350px"} | ![Desktop Dialog](./assets/desktop-dialog.png){: style="max-height:350px"} |

| Web Picker | Web Dialog |
|:----------:|:----------:|
| ![Web Picker](./assets/web-picker.png){: style="max-height:350px"} | ![Web Dialog](./assets/web-dialog.png){: style="max-height:350px"} |

</div>

## Features

- **250+ countries** with flags, dialling codes, and localized names
- **Phone number validation & formatting** — powered by libphonenumber on Android/JVM, built-in rules on iOS/JS/WasmJS
- **Accent-normalized search** — find countries regardless of diacritics
- **Responsive dialog** — full-screen on compact screens, popup on expanded screens
- **13 language translations** — Arabic, German, Spanish, French, Hindi, Indonesian, Italian, Japanese, Dutch, Russian, Swahili, Turkish, Vietnamese
- **Fully customizable** — colors, shapes, icons, text styles, and flag sizes
- **Keyboard navigation** — Arrow keys, Enter, and Escape support on desktop and web

## Installation

Add the Maven Central repository if it is not already there:

```kotlin
repositories {
    mavenCentral()
}
```

### Multiplatform Projects

Add the dependency to your `commonMain` source set:

=== "Kotlin DSL"

    ```kotlin
    kotlin {
        sourceSets {
            commonMain {
                dependencies {
                    implementation("io.github.joelkanyi:komposecountrycodepicker:<latest-version>")
                }
            }
        }
    }
    ```

=== "Version Catalog"

    ```toml title="libs.versions.toml"
    [versions]
    komposecountrycodepicker = "<latest-version>"

    [libraries]
    komposecountrycodepicker = { module = "io.github.joelkanyi:komposecountrycodepicker", version.ref = "komposecountrycodepicker" }
    ```

    Then in your `build.gradle.kts`:

    ```kotlin
    kotlin {
        sourceSets {
            commonMain {
                dependencies {
                    implementation(libs.komposecountrycodepicker)
                }
            }
        }
    }
    ```

### Android Projects

Add the dependency to your app's `build.gradle.kts`:

=== "Kotlin DSL"

    ```kotlin
    dependencies {
        implementation("io.github.joelkanyi:komposecountrycodepicker:<latest-version>")
    }
    ```

=== "Version Catalog"

    ```toml title="libs.versions.toml"
    [versions]
    komposecountrycodepicker = "<latest-version>"

    [libraries]
    komposecountrycodepicker = { module = "io.github.joelkanyi:komposecountrycodepicker", version.ref = "komposecountrycodepicker" }
    ```

    Then in your `build.gradle.kts`:

    ```kotlin
    dependencies {
        implementation(libs.komposecountrycodepicker)
    }
    ```

---

[Get Started :material-arrow-right:](get-started.md){ .md-button .md-button--primary }
[View on GitHub :material-github:](https://github.com/joelkanyi/kompose-country-code-picker){ .md-button }
