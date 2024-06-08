Create the KomposeCountryCodePicker TextField passing all the arguments needed. Check the [KomposeCountryCodePickerState](customizations.md) for more information on how to pass some customizations on the `rememberKomposeCountryCodePickerState` 

```kotlin
val phoneNumber = rememberSaveable { mutableStateOf("") }
val state = rememberKomposeCountryCodePickerState()

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
    state = state,
    interactionSource = MutableInteractionSource(),
)
```

## KomposeCountryCodePicker Composable without TextField

With the `showOnlyCountryCodePicker` parameter set to `true`, the `KomposeCountryCodePicker`
composable can be used without the `TextField`.

```kotlin
val state = rememberKomposeCountryCodePickerState()

KomposeCountryCodePicker(
    modifier = Modifier,
    showOnlyCountryCodePicker = true,
    text = phoneNumber.value,
    state = state,
)
```

## Integration with TextField

```kotlin
val phoneNumber = rememberSaveable { mutableStateOf("") }
val state = rememberKomposeCountryCodePickerState()

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
            text = phoneNumber.value,
            state = state,
        )
    },
    colors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
    ),
)
```
