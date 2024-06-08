>Note:
>* You need Jetpack compose material three
dependency - `implementation("androidx.compose.material3:material3:<version>")`.
>* The library is now hosted on Maven Central. If you were using the previous version hosted on Jitpack, please update your dependencies to the latest version.
>* The last version hosted on Jitpack was : `implementation("com.github.JoelKanyi:KomposeCountryCodePicker:1.1.2")`.

### Including it in your project:

#### Add the Maven Central repository if it is not already there:
```gradle
repositories {
    mavenCentral()
}
```

#### Add the dependency to your dependencies block in your app's build.gradle file:
```kotlin
dependencies {
    implementation("io.github.joelkanyi:komposecountrycodepicker:<latest-version>")
}
```

#### For those using Gradle Version Catalog, you can add the dependency as follows:
```libs.version.toml
[versions]
komposecountrycodepicker = "<latest-version>"

[libraries]
komposecountrycodepicker = { module = "io.github.joelkanyi:komposecountrycodepicker", version.ref = "komposecountrycodepicker" }
```
