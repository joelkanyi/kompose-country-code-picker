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
            selectedCountryPadding = 0.dp, // adjust or remove default padding
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

!!! tip
    Use `selectedCountryPadding` to control the padding around the country selector. The default is `8.dp` on all sides. Set it to `0.dp` when embedding inside a text field's decorator box to avoid extra spacing.

## Fully Custom Phone Input

If your app has its own design system with custom text field components, you likely don't want to use `KomposeCountryCodePicker` at all — it comes with its own `OutlinedTextField` baked in. Instead, you want to use your own text field and only rely on the library for the country data (flags, phone codes) and the searchable country selection dialog.

The `state.selectedCountry` property makes this possible. It exposes the full `Country` object — including the `flag` drawable — so you can render the selected country anywhere in your own layout.

Here is a complete, production-ready example:

```kotlin
@OptIn(RestrictedApi::class)
@Composable
fun PhoneNumberField() {
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var showCountryPicker by rememberSaveable { mutableStateOf(false) }
    val state = rememberKomposeCountryCodePickerState()

    // 1. Show the country selection dialog when the user taps the country section
    if (showCountryPicker) {
        CountrySelectionDialog(
            countryList = state.countryList,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            onDismissRequest = { showCountryPicker = false },
            onSelect = { country ->
                state.setCode(country.code)
                showCountryPicker = false
            },
        )
    }

    // 2. Build your own text field — any TextField, OutlinedTextField, or BasicTextField
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = phoneNumber,
        onValueChange = { phoneNumber = it },
        placeholder = { Text("Phone Number") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        leadingIcon = {
            // 3. Use state.selectedCountry to display the flag and phone code
            Row(
                modifier = Modifier
                    .clickable { showCountryPicker = true }
                    .padding(start = 12.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier.width(28.dp).height(18.dp),
                    painter = painterResource(state.selectedCountry.flag),
                    contentDescription = state.selectedCountry.name,
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = state.selectedCountry.phoneNoCode)
            }
        },
    )

    // 4. Access phone data from the state as usual
    val fullNumber = "${state.getCountryPhoneCode()}$phoneNumber"
}
```

**What you get from `state.selectedCountry`:**

| Property | Type | Example |
|---|---|---|
| `state.selectedCountry.flag` | `DrawableResource` | The country's flag drawable |
| `state.selectedCountry.name` | `String` | `"Kenya"` |
| `state.selectedCountry.phoneNoCode` | `String` | `"+254"` |
| `state.selectedCountry.code` | `String` | `"ke"` |

!!! note
    `state.setCode()` is annotated with `@RestrictedApi` because it's intended for advanced use cases where you manage the state yourself. Add `@OptIn(RestrictedApi::class)` to your function and import `com.joelkanyi.jcomposecountrycodepicker.annotation.RestrictedApi`.

Check the **Custom** tab in the sample app for more examples, including a `BasicTextField` variant with a fully custom layout.

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
| `state.selectedCountry` | The currently selected `Country` object | `Country(code=ke, ...)` |
| `state.selectedCountry.flag` | Flag drawable resource of the selected country | `DrawableResource` |

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
