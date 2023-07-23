package com.joelkanyi.jcomposecountrycodepicker.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.input.KeyboardType
import com.joelkanyi.ccp.transformation.PhoneNumberTransformation
import com.joelkanyi.jcomposecountrycodepicker.data.utils.allCountries
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getDefaultLangCode
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getDefaultPhoneCode
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getNumberHint
import com.joelkanyi.jcomposecountrycodepicker.data.utils.isValid
import com.joelkanyi.jcomposecountrycodepicker.data.utils.removeSpecialCharacters
import java.util.Locale

private var fullNumberState: String by mutableStateOf("")
private var phoneNumberState: String by mutableStateOf("")
private var countryCodeState: String by mutableStateOf("")

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KomposeCountryCodePicker(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    shape: Shape = MaterialTheme.shapes.medium,
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = true,
    limitedCountries: List<String> = emptyList(),
    error: Boolean = false,
    placeholder: @Composable ((defaultLang: String) -> Unit) = { defaultLang ->
        Text(text = stringResource(id = getNumberHint(allCountries.single { it.countryCode == defaultLang }.countryCode.lowercase())))
    },
    colors: TextFieldColors = TextFieldDefaults.colors(),
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
        Column {
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
                        ComposePickerCodeDialog(
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
                )
            }
        }
    }
}

object CountryCodePicker {
    fun getCountryCodeWithoutPrefix(): String {
        return countryCodeState
    }

    fun getCountryName(): String {
        return allCountries.single { it.countryCode == countryCodeState }.cCountryName.capitalize(
            Locale.getDefault(),
        )
    }

    fun getCountryPhoneCode(): String {
        return allCountries.single { it.countryCode == countryCodeState }.cCountryPhoneNoCode
    }

    fun getCountryPhoneCodeWithoutPrefix(): String {
        return allCountries.single { it.countryCode == countryCodeState }.cCountryPhoneNoCode.removePrefix(
            "+",
        )
    }

    fun getPhoneNumber(): String {
        return if (phoneNumberState.startsWith("0")) {
            phoneNumberState.removeSpecialCharacters()
        } else {
            "0${phoneNumberState.removeSpecialCharacters()}"
        }
    }

    fun getPhoneNumberWithoutPrefix(): String {
        return phoneNumberState.removeSpecialCharacters().removePrefix("0")
    }

    fun getFullPhoneNumberWithoutPrefix(): String {
        return getCountryPhoneCodeWithoutPrefix() + phoneNumberState.removeSpecialCharacters()
            .removePrefix("0")
    }

    fun getFullPhoneNumber(): String {
        return getCountryPhoneCode() + phoneNumberState.removeSpecialCharacters()
    }

    fun isPhoneNumberValid(): Boolean {
        return isValid(getFullPhoneNumber())
    }
}

/**
 * TODO: Add capability to ony have the code picker without the text field.
 * TODO: Document functions.
 * TODO: Fix the search functionality.
 * TODO: Fix phone number validation for some countries like UK.
 */
