## KomposeCountryCodePickerState

`val state = rememberKomposeCountryCodePickerState()`

The `state` parameter is used to set and access different variables and methods available in the `KomposeCountryCodePicker` composable.

```kotlin
val state = rememberKomposeCountryCodePickerState(
    limitedCountries = listOf("KE", "UG", "TZ", "RW", "SS", "Togo", "+260", "250", "+211", "Mali", "Malawi"),
    priorityCountries = listOf("KE", "UG", "TZ"),
    showCountryCode = true,
    showCountryFlag = true,
    defaultCountryCode = "TZ",
)

```

## KomposeCountryCodePickerState Customizations
| Customization          | Description                                                                                                                                                                                                                                                                                      |
|------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **showCountryCode**    | If `true`, the country code will be displayed in the country code picker `TextField`.                                                                                                                                                                                                            |
| **showCountryFlag**    | If `true`, the country flag will be displayed in the country code picker `TextField`.                                                                                                                                                                                                            |
| **defaultCountryCode** | Sets the default country code to be displayed in the country code picker.                                                                                                                                                                                                                        |
| **limitedCountries**   | Limits the list of countries to be displayed in the country code picker by specifying country codes, country names, or country phone codes, e.g., `listOf("KE", "UG", "TZ")`, `listOf("Kenya", "Uganda", "Tanzania")` or `listOf("+254", "+256", "+255")`.                                       |
| **priorityCountries**   | Specifies the priority countries to be displayed at the top of the list in the country code picker. This can be ONLY a list of country codes e.g., `listOf("KE", "UG", "TZ")`. |

## Available methods/variables accessible from the state

Use the state you defined (`val state = rememberKomposeCountryCodePickerState()`) to access the following methods and variables:

```markdown
| Description                                   | Method                                     | Example                   |
|-----------------------------------------------|--------------------------------------------|---------------------------|
| **Country Code**                              | `state.getCountryCode()`                   | `KE`                      |
| **Phone Number**                              | `state.getPhoneNumber()`                   | `0712345678`              |
| **Country Phone No Code Without Prefix**      | `state.getCountryPhoneCodeWithoutPrefix()` | `254`                     |
| **Country Phone No Code**                     | `state.getCountryPhoneCode()`              | `+254`                    |
| **Country Name**                              | `state.getCountryName()`                   | `Kenya`                   |
| **Phone Number Without Prefix**               | `state.getPhoneNumberWithoutPrefix()`      | `712345678`               |
| **Full Phone Number**                         | `state.getFullPhoneNumber()`               | `+254712345678`           |
| **Full Phone Number Without Prefix**          | `state.getFullPhoneNumberWithoutPrefix()`  | `254712345678`            |
| **Fully Formatted Phone Number**              | `state.getFullyFormattedPhoneNumber()`     | `+254 712 345678`         |
| **Is Phone Number Valid**                     | `state.isPhoneNumberValid()`               | `true` / `false`          |
```

### Explanation:

- **Country Code**: Returns the country code for the selected country, e.g., `KE`.
- **Phone Number**: Returns the phone number entered by the user, e.g., `0712345678`.
- **Country Phone No Code Without Prefix**: Returns the country phone code without the prefix, e.g., `254`.
- **Country Phone No Code**: Returns the country phone code with the prefix, e.g., `+254`.
- **Country Name**: Returns the name of the country, e.g., `Kenya`.
- **Phone Number Without Prefix**: Returns the phone number without the prefix, e.g., `712345678`.
- **Full Phone Number**: Returns the full phone number with the prefix, e.g., `+254712345678`.
- **Full Phone Number Without Prefix**: Returns the full phone number without the prefix, e.g., `254712345678`.
- **Fully Formatted Phone Number**: Returns the fully formatted phone number, e.g., `+254 712 345678`.
- **Is Phone Number Valid**: Checks if the phone number is valid, returns `true` or `false`.


## KomposeCountryCodePicker Textfield Composable customizations
| Customization                            | Description                                                                                |
|------------------------------------------|--------------------------------------------------------------------------------------------|
| **placeholder**                          | Sets the placeholder `Composable` for the country code picker.                             |
| **shape**                                | Sets the shape of the country code picker `TextField`.                                     |
| **error**                                | Sets the error state on the `TextField` for the country code picker.                       |
| **colors**                               | Sets the colors for the country code picker `TextField`.                                   |
| **showOnlyCountryCodePicker**            | Shows only the country code picker without the `TextField`.                                |
| **state**                                | The state of the country code picker.                                                      |
| **countrySelectionDialogContainerColor** | The color of the country selection dialog container.                                       |
| **countrySelectionDialogContentColor**   | The color of the country selection dialog content.                                         |
| **countrySelectionDialogTitle**   | The title of the country selection dialog.                                         |
| **countrySelectionDialogSearchIcon**   | The icon to be used for the search field in the country selection dialog. |
| **countrySelectionDialogBackIcon**   | The icon to be used for the back button in the country selection dialog. |
| **textStyle**                            | The style to be used for displaying text on the `TextField` and the selected country.       |
| **interactionSource**                    | The `MutableInteractionSource` representing the stream of Interactions for this text field. |
| **selectedCountryFlagSize**              | The size of the selected country flag (width and height in `.dp`).                         |
| **enabled**                              | Controls the enabled state of the country code picker.                                     |
