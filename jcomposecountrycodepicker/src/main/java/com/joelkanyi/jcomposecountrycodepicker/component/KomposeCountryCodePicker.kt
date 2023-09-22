package com.joelkanyi.jcomposecountrycodepicker.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.joelkanyi.jcomposecountrycodepicker.data.utils.allCountries
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getDefaultLangCode
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getDefaultPhoneCode
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getNumberHint
import com.joelkanyi.jcomposecountrycodepicker.data.utils.isValid
import com.joelkanyi.jcomposecountrycodepicker.data.utils.removeSpecialCharacters
import com.joelkanyi.jcomposecountrycodepicker.transformation.PhoneNumberTransformation
import java.util.Locale

private var fullNumberState: String by mutableStateOf("")
private var phoneNumberState: String by mutableStateOf("")
private var countryCodeState: String by mutableStateOf("")

/**
 * [KomposeCountryCodePicker] is a composable that displays a text field with a country code picker dialog.
 * [modifier] Modifier to be applied to the layout.
 * [text] The text to be displayed in the text field.
 * [onValueChange] Called when the value is changed.
 * [shape] The shape of the text field's outline.
 * [showCountryCode] If true, the country code will be shown in the text field.
 * [showCountryFlag] If true, the country flag will be shown in the text field.
 * [limitedCountries] If not empty, only the countries in the list will be shown in the dialog.
 * [error] If true, the text field will be displayed in the error state.
 * [placeholder] The placeholder to be displayed in the text field.
 * [colors] The colors to be used to display the text field.
 * [showOnlyCountryCodePicker] If true, only the country code picker dialog will be shown.
 * [trailingIcon] The trailing icon to be displayed in the text field.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KomposeCountryCodePicker(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (String) -> Unit = {},
    shape: Shape = MaterialTheme.shapes.medium,
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = true,
    showOnlyCountryCodePicker: Boolean = false,
    limitedCountries: List<String> = emptyList(),
    error: Boolean = false,
    placeholder: @Composable ((defaultLang: String) -> Unit) = { defaultLang ->
        Text(
            text = stringResource(id = getNumberHint(allCountries.single { it.countryCode == defaultLang }.countryCode.lowercase())),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.ExtraLight,
            ),
        )
    },
    colors: TextFieldColors = TextFieldDefaults.colors(),
    trailingIcon: @Composable (() -> Unit) = {},
) {
    val context = LocalContext.current
    var textFieldValue by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var phoneCode by rememberSaveable {
        mutableStateOf(
            getDefaultPhoneCode(
                context,
            ),
        )
    }
    var defaultLang by rememberSaveable {
        mutableStateOf(
            getDefaultLangCode(context),
        )
    }

    fullNumberState = phoneCode + textFieldValue
    phoneNumberState = textFieldValue
    countryCodeState = defaultLang

    Surface {
        if (showOnlyCountryCodePicker) {
            KomposeCountryCodePickerDialog(
                modifier = modifier,
                pickedCountry = {
                    phoneCode = it.cCountryPhoneNoCode
                    defaultLang = it.countryCode
                },
                defaultSelectedCountry = allCountries.single { it.countryCode == defaultLang },
                showCountryCode = showCountryCode,
                showFlag = showCountryFlag,
                limitedCountries = limitedCountries,
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                OutlinedTextField(
                    modifier = modifier,
                    shape = shape,
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it
                        if (text != it) {
                            onValueChange(it)
                        }
                    },
                    singleLine = true,
                    colors = colors,
                    isError = error,
                    visualTransformation = PhoneNumberTransformation(allCountries.single { it.countryCode == defaultLang }.countryCode.uppercase()),
                    placeholder = {
                        placeholder(defaultLang)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone,
                        autoCorrect = true,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        },
                    ),
                    leadingIcon = {
                        KomposeCountryCodePickerDialog(
                            pickedCountry = {
                                phoneCode = it.cCountryPhoneNoCode
                                defaultLang = it.countryCode
                            },
                            defaultSelectedCountry = allCountries.single { it.countryCode == defaultLang },
                            showCountryCode = showCountryCode,
                            showFlag = showCountryFlag,
                            limitedCountries = limitedCountries,
                        )
                    },
                    trailingIcon = trailingIcon,
                )
            }
        }
    }
}

object CountryCodePicker {

    /**
     * Returns the selected country language code.
     */
    fun getCountryCodeWithoutPrefix(): String {
        return countryCodeState
    }

    /**
     * Returns the selected country name.
     */
    fun getCountryName(): String {
        return allCountries.single { it.countryCode == countryCodeState }.cCountryName.replaceFirstChar { it.uppercase(Locale.getDefault()) }
    }

    /**
     * Returns the selected country phone code.
     */
    fun getCountryPhoneCode(): String {
        return allCountries.single { it.countryCode == countryCodeState }.cCountryPhoneNoCode
    }

    /**
     * Returns the selected country phone code without the plus sign.
     */
    fun getCountryPhoneCodeWithoutPrefix(): String {
        return allCountries.single { it.countryCode == countryCodeState }.cCountryPhoneNoCode.removePrefix(
            "+",
        )
    }

    /**
     * Returns phone number without the country phone code.
     */
    fun getPhoneNumber(): String {
        return if (phoneNumberState.startsWith("0")) {
            phoneNumberState.removeSpecialCharacters()
        } else {
            "0${phoneNumberState.removeSpecialCharacters()}"
        }
    }

    /**
     * Returns phone number without the country phone code and without the zero prefix.
     */
    fun getPhoneNumberWithoutPrefix(): String {
        return phoneNumberState.removeSpecialCharacters().removePrefix("0")
    }

    /**
     * Returns phone number with the country phone code without the + prefix.
     */
    fun getFullPhoneNumberWithoutPrefix(): String {
        return getCountryPhoneCodeWithoutPrefix() + phoneNumberState.removeSpecialCharacters()
            .removePrefix("0")
    }

    /**
     * Returns phone number with the country phone code and with the + prefix.
     */
    fun getFullPhoneNumber(): String {
        return getCountryPhoneCode() + phoneNumberState.removeSpecialCharacters()
    }

    /**
     * Returns if the phone number is valid or not.
     */
    fun isPhoneNumberValid(phoneNumber: String = getFullPhoneNumber()): Boolean {
        return isValid(phoneNumber)
    }
}
