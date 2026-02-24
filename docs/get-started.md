# Getting Started

## Prerequisites

- A **Kotlin Multiplatform** or **Android** project with the **Compose Multiplatform** plugin applied
- **Material 3** dependency (`compose.material3`)

## Add Repository

Ensure `mavenCentral()` is in your repositories block (this is the default for most projects):

```kotlin
repositories {
    mavenCentral()
}
```

## Add Dependency

### Multiplatform Projects

Add the dependency to your `commonMain` source set dependencies:

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

    Add to your `libs.versions.toml`:

    ```toml
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

If you are working on an Android-only project, add the dependency directly to your app's `build.gradle.kts`:

=== "Kotlin DSL"

    ```kotlin
    dependencies {
        implementation("io.github.joelkanyi:komposecountrycodepicker:<latest-version>")
    }
    ```

=== "Version Catalog"

    Add to your `libs.versions.toml`:

    ```toml
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

## Platform Notes

| Platform | Status |
|----------|--------|
| **Android** | Works out of the box |
| **iOS** | Requires Compose Multiplatform iOS support enabled in your project |
| **Desktop (JVM)** | Works out of the box with JVM target |
| **Web (JS)** | Works with `js(IR)` browser target |
| **Web (WasmJS)** | Works with `wasmJs` browser target |

For platform-specific details (validation engines, default country detection, running sample apps), see the [Platforms](platforms.md) page.

## Next Steps

Head over to the [Usage](usage.md) page to learn how to use the country code picker in your app.
