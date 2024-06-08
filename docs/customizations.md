## KomposeCountryCodePickerState

`val state = rememberKomposeCountryCodePickerState()`

The `state` parameter is used to set and access different variables and methods available in the `KomposeCountryCodePicker` composable.

```kotlin
val state = rememberKomposeCountryCodePickerState(
    limitedCountries = listOf("KE", "UG", "TZ", "RW", "SS"),
    showCountryCode = true,
    showCountryFlag = true,
    defaultCountryCode = "TZ",
)

```

## KomposeCountryCodePickerState Customizations
Customization                          | Description
----------------------------------------|---------------------------------------------------------------------
showCountryCode                        | If `true`, the country code will be displayed in the country code picker `TextField`
showCountryFlag                        | If `true`, the country flag will be displayed in the country code picker `TextField`
defaultCountryCode                     | Sets the default country code to be displayed in the country code picker.
limitedCountries                       | Limits the list of countries to be displayed in the country code picker by specifying country codes, country names, or country phone codes, e.g., `listOf("KE", "UG", "TZ")`, `listOf("Kenya", "Uganda", "Tanzania")` or `listOf("+254", "+256", "+255")`


## Available methods/variables accessible from the state

Use the state you defined (`val state = rememberKomposeCountryCodePickerState()`) to access the following methods and variables:

 Description                      | Method                                     
----------------------------------|--------------------------------------------
 `countryCode`                    | Returns the country code for the selected country                   
 `phoneNumber`                    | Returns the phone number entered by the user  
 Country Phone No Code            | `state.getCountryPhoneCodeWithoutPrefix()` 
 Country Name                     | `state.getCountryName()`                   
 Country Language Code            | `state.getCountryCodeWithoutPrefix()`      
 Phone Number Without Prefix      | `state.getPhoneNumberWithoutPrefix()`      
 Full Phone Number                | `state.getFullPhoneNumber()`               
 Full Phone Number Without Prefix | `state.getFullPhoneNumberWithoutPrefix()`  
 Phone Number State               | `state.isPhoneNumberValid()`               


## KomposeCountryCodePicker Textfield Composable customizations

 Customization                          | Description                                                         
----------------------------------------|---------------------------------------------------------------------                      
 `placeholder`                          | Sets the placeholder `Composable` for the country code picker       
 `shape`                                | Sets the shape of the country code picker `TextField`               
 `error`                                | Sets the error state on the `TextField` for the country code picker 
 `colors`                               | Sets the colors for the country code picker `TextField`             
 `showOnlyCountryCodePicker`            | Shows only the country code picker without the `TextField`          
 `state`                                | The state of the country code picker                                
 `countrySelectionDialogContainerColor` | The color of the country selection dialog container                 
 `countrySelectionDialogContentColor`   | The color of the country selection dialog content  
 `pickerContentColor`                   | The color of the country code picker content i.e country name, code, and dropdown icon
 `interactionSource`                    | The `MutableInteractionSource` representing the stream of Interactions for this text field.
