# Customizations

## State Configuration

Use `rememberKomposeCountryCodePickerState()` to create and configure the picker state:

```kotlin
val state = rememberKomposeCountryCodePickerState(
    defaultCountryCode = "KE",
    limitedCountries = listOf("KE", "UG", "TZ", "RW"),
    priorityCountries = listOf("KE", "UG", "TZ"),
    showCountryCode = true,
    showCountryFlag = true,
)
```

### State Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `defaultCountryCode` | `String?` | Auto-detected | The default country code (ISO 3166-1 alpha-2). When `null`, the library auto-detects the user's country on all platforms. |
| `limitedCountries` | `List<String>` | `emptyList()` | Limits which countries appear in the picker. Accepts country codes (`"KE"`), country names (`"Kenya"`), or phone codes (`"+254"`). An empty list shows all countries. |
| `priorityCountries` | `List<String>` | `emptyList()` | Country codes to display at the top of the list (e.g. `listOf("KE", "UG", "TZ")`). Must be ISO country codes. |
| `showCountryCode` | `Boolean` | `true` | Whether to display the dialling code (e.g. `+254`) next to the flag. |
| `showCountryFlag` | `Boolean` | `true` | Whether to display the country flag. |

!!! tip
    Default country detection works on all platforms: it uses `Locale` on Android/JVM, `NSLocale` on iOS, and browser language/timezone on JS/WasmJS.

## TextField Customizations

The `KomposeCountryCodePicker` composable accepts the following parameters:

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `state` | `CountryCodePicker` | — | The state object from `rememberKomposeCountryCodePickerState()`. **Required.** |
| `text` | `String` | — | The current phone number text. **Required.** |
| `modifier` | `Modifier` | `Modifier` | Modifier applied to the text field layout. |
| `onValueChange` | `(String) -> Unit` | `{}` | Called when the text field value changes. |
| `error` | `Boolean` | `false` | Whether to display the text field in an error state. |
| `showOnlyCountryCodePicker` | `Boolean` | `false` | If `true`, renders only the country picker without the text field. |
| `shape` | `Shape` | `MaterialTheme.shapes.medium` | The shape of the text field outline. |
| `placeholder` | `@Composable (String) -> Unit` | Default hint | A composable to display as the placeholder. Receives the current country code. |
| `colors` | `TextFieldColors` | `TextFieldDefaults.colors()` | Colors for the text field. |
| `trailingIcon` | `@Composable (() -> Unit)?` | `null` | An optional trailing icon composable. |
| `interactionSource` | `MutableInteractionSource` | `MutableInteractionSource()` | The interaction source for the text field. |
| `selectedCountryFlagSize` | `FlagSize` | `FlagSize(28.dp, 18.dp)` | The width and height of the selected country flag. |
| `textStyle` | `TextStyle` | `LocalTextStyle.current` | The text style for the text field and selected country display. |
| `enabled` | `Boolean` | `true` | Whether the text field and country picker are enabled. |
| `keyboardOptions` | `KeyboardOptions` | Phone / Next | Keyboard options for the text field. Defaults to phone keyboard with `ImeAction.Next`. |
| `keyboardActions` | `KeyboardActions` | `KeyboardActions.Default` | Keyboard actions for the text field. |

## Dialog Customizations

The country selection dialog can be customized through additional parameters on `KomposeCountryCodePicker`:

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `countrySelectionDialogContainerColor` | `Color` | `MaterialTheme.colorScheme.background` | Background color of the dialog. |
| `countrySelectionDialogContentColor` | `Color` | `MaterialTheme.colorScheme.onBackground` | Content/text color of the dialog. |
| `countrySelectionDialogTitle` | `@Composable () -> Unit` | "Select Country" | A composable for the dialog title. |
| `countrySelectionDialogBackIcon` | `@Composable () -> Unit` | Back arrow icon | A composable for the back/dismiss button icon. |
| `countrySelectionDialogSearchIcon` | `@Composable () -> Unit` | Search icon | A composable for the search toggle icon. |

The dialog is **responsive**: it displays full-screen on compact screens (width < 600dp) and as a popup on larger screens.

## CountrySelectionDialog (Standalone)

The `CountrySelectionDialog` composable is part of the public API and can be used independently if you need full control over when the dialog is shown:

```kotlin
CountrySelectionDialog(
    countryList = state.countryList,
    containerColor = MaterialTheme.colorScheme.background,
    contentColor = MaterialTheme.colorScheme.onBackground,
    onDismissRequest = { /* handle dismiss */ },
    onSelect = { country -> /* handle selection */ },
)
```

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `countryList` | `List<Country>` | — | The list of countries to display. **Required.** |
| `containerColor` | `Color` | — | Background color of the dialog. **Required.** |
| `contentColor` | `Color` | — | Content/text color of the dialog. **Required.** |
| `onDismissRequest` | `() -> Unit` | — | Called when the user dismisses the dialog. **Required.** |
| `onSelect` | `(Country) -> Unit` | — | Called when a country is selected. **Required.** |
| `modifier` | `Modifier` | `Modifier` | Modifier applied to the dialog layout. |
| `properties` | `DialogProperties` | Full-width | Dialog window properties. |
| `title` | `@Composable () -> Unit` | "Select Country" | A composable for the dialog title. |
| `backIcon` | `@Composable () -> Unit` | Back arrow icon | A composable for the back button icon. |
| `searchIcon` | `@Composable () -> Unit` | Search icon | A composable for the search icon. |
