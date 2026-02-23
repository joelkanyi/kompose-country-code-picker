# Migration Guide

## Migrating from Android-only to Compose Multiplatform

This guide is for existing users of the Android-only version of Kompose Country Code Picker who want to upgrade to the Compose Multiplatform version.

## Dependency Change

**Before** (Android-only, in `app/build.gradle.kts`):

```kotlin
dependencies {
    implementation("io.github.joelkanyi:komposecountrycodepicker:x.y.z")
}
```

**After** (Compose Multiplatform, in your KMP module's `build.gradle.kts`):

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.joelkanyi:komposecountrycodepicker:x.y.z")
        }
    }
}
```

The Maven coordinates remain the same — only the placement in your Gradle configuration changes.

## API Changes

### Country.flag type

The `Country.flag` property type has changed:

| Before | After |
|--------|-------|
| `Int` (Android `R.drawable` resource ID) | `DrawableResource` (Compose Multiplatform resource) |

If you were accessing `Country.flag` directly (e.g. for custom UI), update your code to use `painterResource(country.flag)` from `org.jetbrains.compose.resources`.

### No more Android Context dependency

The library no longer depends on Android framework APIs. Everything is pure Compose Multiplatform — no `Context`, no `R.drawable`, no Android-specific imports.

### Phone validation

The `isPhoneNumberValid()` API is unchanged. The underlying engine differs by platform:

- **Android / JVM**: Google's libphonenumber (same as before)
- **iOS / JS / WasmJS**: Built-in per-country digit-length rules

### State API

`rememberKomposeCountryCodePickerState()` has the same parameters and return type. No changes needed.

## What's New

- **iOS, Desktop, JS, and WasmJS support** — write once, run on all platforms
- **Responsive dialog** — adapts between full-screen (mobile) and popup (desktop/web)
- **Keyboard navigation** — Arrow keys, Enter, and Escape in the country selection dialog
- **Per-country phone validation and formatting** on all platforms
- **Improved search** with accent/diacritic normalization
- **New parameters**: `trailingIcon`, `keyboardOptions`, `keyboardActions`, `enabled`

## Breaking Changes

- **`Country.flag` type**: Changed from `Int` to `DrawableResource`. If you consume this property directly, update your code.
- **No Android framework dependency**: The library no longer uses Android-specific APIs. Imports from `android.*` or `R.drawable.*` related to this library should be removed.
- **Resource system**: Flag images and string resources now use Compose Multiplatform resources (`org.jetbrains.compose.resources`) instead of Android resources.
