# Platforms

## Supported Platforms

| Platform | Targets | Phone Validation | Phone Formatting |
|----------|---------|-----------------|-----------------|
| **Android** | `android` | Google libphonenumber | libphonenumber |
| **JVM Desktop** | `jvm` | Google libphonenumber | libphonenumber |
| **iOS** | `iosArm64`, `iosSimulatorArm64`, `iosX64` | Built-in per-country rules | Built-in patterns |
| **JS Browser** | `js(IR)` browser | Built-in per-country rules | Built-in patterns |
| **WasmJS Browser** | `wasmJs` browser | Built-in per-country rules | Built-in patterns |

## Default Country Detection

The library automatically detects the user's country on all platforms:

| Platform | Detection Method |
|----------|-----------------|
| **Android / JVM** | `Locale.getDefault().country` |
| **iOS** | `NSLocale.currentLocale.countryCode` |
| **JS / WasmJS** | `navigator.languages` → `navigator.language` → timezone fallback |

You can always override auto-detection by passing `defaultCountryCode` to `rememberKomposeCountryCodePickerState()`.

## Running the Sample Apps

The repository includes sample applications for each platform under the `sample/` directory.

### Android

```bash
./gradlew :sample:android:installDebug
```

### Desktop (JVM)

```bash
./gradlew :sample:desktop:run
```

### Web (JS)

```bash
./gradlew :sample:web-js:jsBrowserDevelopmentRun
```

### Web (WasmJS)

```bash
./gradlew :sample:web-wasm:wasmJsBrowserDevelopmentRun
```

### iOS

Open `sample/iosApp/iosApp.xcodeproj` in Xcode and run on a simulator or device.

## Platform Notes

- **Keyboard navigation**: Arrow keys, Enter, and Escape work in the country selection dialog on Desktop and Web platforms.
- **Responsive dialog**: The country selection dialog adapts to screen size — full-screen on compact screens (width < 600dp), popup on expanded screens.
- **Phone validation engines**: Android and JVM use Google's libphonenumber for accurate validation. iOS, JS, and WasmJS use built-in per-country digit-length rules that cover the majority of cases.
- **Phone formatting**: On Android/JVM, formatting uses libphonenumber's `AsYouTypeFormatter`. On other platforms, formatting follows built-in country-specific patterns.
