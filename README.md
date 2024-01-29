# Kompose Country Code Picker
[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=plastic)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![](https://jitpack.io/v/JoelKanyi/KomposeCountryCodePicker.svg)](https://jitpack.io/#JoelKanyi/KomposeCountryCodePicker)

Kompose Country Code Picker is a Jetpack Compose library based on Material 3 (M3) that provides a country code picker for Android apps.

## Preview
Picker   |   Dialog | Picker Only
-----------------   |   ----------------- | -----------------
<img src="screenshot/kompose-picker-textfield.png" width="250"/>   |   <img src="screenshot/kompose-picker-dialog.png" width="250"/> | <img src="screenshot/kompose-picker-only.png" width="250"/>

## Requirements
Jetpack compose material three dependency - `implementation("androidx.compose.material3:material3:1.1.1")`

### Setup
#### Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

### Dependency
Add the following to your `build.gradle` dependencies:

```groovy
dependencies {
    implementation("com.github.JoelKanyi:KomposeCountryCodePicker:<version>")
}
```

## Usage

```kotlin
val phoneNumber = rememberSaveable { mutableStateOf("") }

KomposeCountryCodePicker(
    modifier = Modifier
        .fillMaxWidth(),
    text = phoneNumber.value,
    onValueChange = { phoneNumber.value = it },
    placeholder = { Text(text = "Phone Number") },
    shape = MaterialTheme.shapes.medium,
    colors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
    ),
)
```

## KomposeCountryCodePicker Composable without TextField
With the `showOnlyCountryCodePicker` parameter set to `true`, the `KomposeCountryCodePicker` composable can be used without the `TextField` composable but you can't use all methods. You can only use  the following methods:
- `CountryCodePicker.getCountryPhoneCodeWithoutPrefix()`
- `CountryCodePicker.getCountryPhoneCode()`
- `CountryCodePicker.isPhoneNumberValid(<combinedPhone>)` - You will need to manually combine the country code and phone number


```kotlin
KomposeCountryCodePicker(
    modifier = Modifier,
    showOnlyCountryCodePicker = true,
    showCountryFlag = false,
    text = phoneNumber.value,
)
```

## Integration with TextField

```kotlin
val phoneNumber = rememberSaveable { mutableStateOf("") }

TextField(
    modifier = Modifier
        .fillMaxWidth(),
    value = phoneNumber.value,
    onValueChange = { phoneNumber.value = it },
    placeholder = { Text(text = "Phone Number") },
    leadingIcon = {
        KomposeCountryCodePicker(
            modifier = Modifier,
            showOnlyCountryCodePicker = true,
            showCountryFlag = false,
            text = phoneNumber.value,
        )
    },
    colors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
    ),
)
```

## KomposeCountryCodePicker Composable customizations
Customization | Description
------------     |   -------------
`limitedCountries`   | Limits the list of countries to be displayed in the country code picker by specify the country codes, country names or country phone codes i.e `listOf("KE", "UG", "TZ")`, `listOf("Kenya", "Uganda", "Tanzania")` or `listOf("+254", "+256", "+255")`
`placeholder`   | Sets the placeholder `Composable` for the country code picker
`shape`  | Sets the shape of the country code picker `TextField`
`showCountryCode`  | Shows the country code in the country code picker `TextField`
`showCountryFlag`  | Shows the country flag in the country code picker `TextField`
`error`  | Sets the error state on the `TextField` for the country code picker
`colors`  | Sets the colors for the country code picker `TextField`
`showOnlyCountryCodePicker`  | Shows only the country code picker without the `TextField`
`defaultCountryCode` | The default country code to be selected. i.e "KE", "in", "UG", "tz" etc


## Available methods
Description | Method
------------     |   -------------
Country Phone No Code    | `CountryCodePicker.getCountryPhoneCodeWithoutPrefix()`
Prefixed Country Phone No Code    | `CountryCodePicker.getCountryPhoneCode()`
Country Name    | `CountryCodePicker.getCountryName()`
Country Language Code    | `CountryCodePicker.getCountryCodeWithoutPrefix()`
Phone Number    | `CountryCodePicker.getPhoneNumber()`
Phone Number Without Prefix    | `CountryCodePicker.getPhoneNumberWithoutPrefix()`
Full Phone Number    | `CountryCodePicker.getFullPhoneNumber()`
Full Phone Number Without Prefix    | `CountryCodePicker.getFullPhoneNumberWithoutPrefix()`
Phone Number State    | `CountryCodePicker.isPhoneNumberValid()`


## Contributing

Please fork this repository and contribute back using
[pull requests](https://github.com/Shashank02051997/FancyWalkthrough-Android/pulls).

Any contributions, large or small, major features, bug fixes, are welcomed and appreciated
but will be thoroughly reviewed.

## License

```
Copyright 2023 Joel Kanyi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
