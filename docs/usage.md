# Usage

## Country Code Picker with TextField

The primary usage pattern provides a full `OutlinedTextField` with an integrated country code picker as the leading icon.

```kotlin
var phoneNumber by rememberSaveable { mutableStateOf("") }
val state = rememberKomposeCountryCodePickerState(
    showCountryCode = true,
    showCountryFlag = true,
)

KomposeCountryCodePicker(
    modifier = Modifier.fillMaxWidth(),
    text = phoneNumber,
    onValueChange = { phoneNumber = it },
    placeholder = {
        Text(
            text = "Phone Number",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.ExtraLight,
            ),
        )
    },
    shape = MaterialTheme.shapes.medium,
    colors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
    ),
    state = state,
)
```

The `state` object holds all the phone number data. Use `text` to provide the current input and `onValueChange` to update it — the same controlled-text pattern used by standard Compose text fields.

## Country Code Picker Only

Set `showOnlyCountryCodePicker = true` to display just the country selector without a text field. This is useful when embedding the picker into a custom layout.

```kotlin
val state = rememberKomposeCountryCodePickerState()

KomposeCountryCodePicker(
    modifier = Modifier,
    showOnlyCountryCodePicker = true,
    text = phoneNumber,
    state = state,
)
```

## Integration with Custom TextField

You can use the picker-only mode as a `leadingIcon` inside your own `TextField`:

```kotlin
var phoneNumber by rememberSaveable { mutableStateOf("") }
val state = rememberKomposeCountryCodePickerState()

TextField(
    modifier = Modifier.fillMaxWidth(),
    value = phoneNumber,
    onValueChange = { phoneNumber = it },
    placeholder = { Text(text = "Phone Number") },
    leadingIcon = {
        KomposeCountryCodePicker(
            modifier = Modifier,
            showOnlyCountryCodePicker = true,
            text = phoneNumber,
            state = state,
        )
    },
    colors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
    ),
)
```

## Reading Phone Number Data

Use the `state` object to access all phone number information:

| Method / Property | Return Value | Example |
|---|---|---|
| `state.getCountryPhoneCode()` | Country phone code with prefix | `+254` |
| `state.getCountryPhoneCodeWithoutPrefix()` | Country phone code without prefix | `254` |
| `state.getCountryName()` | Country name | `Kenya` |
| `state.countryCode` | ISO country code | `ke` |
| `state.phoneNumber` | Phone number as entered | `0712345678` |
| `state.getPhoneNumberWithoutPrefix()` | Phone number without leading zero | `712345678` |
| `state.getFullPhoneNumber()` | Full number with `+` prefix | `+254712345678` |
| `state.getFullPhoneNumberWithoutPrefix()` | Full number without `+` prefix | `254712345678` |
| `state.getFullyFormattedPhoneNumber()` | Formatted full number | `+254 712 345678` |
| `state.isPhoneNumberValid()` | Whether the number is valid | `true` / `false` |

## Validation Example

Drive the error state of the text field using `isPhoneNumberValid()`:

```kotlin
var phoneNumber by rememberSaveable { mutableStateOf("") }
val state = rememberKomposeCountryCodePickerState()

KomposeCountryCodePicker(
    modifier = Modifier.fillMaxWidth(),
    text = phoneNumber,
    onValueChange = { phoneNumber = it },
    state = state,
    error = !state.isPhoneNumberValid(),
)
```

!!! note
    `isPhoneNumberValid()` uses Google's libphonenumber on Android and JVM, and built-in per-country rules on iOS, JS, and WasmJS. See the [Platforms](platforms.md) page for details.
