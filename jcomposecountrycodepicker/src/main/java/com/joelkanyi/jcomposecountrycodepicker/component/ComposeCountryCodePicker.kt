package com.joelkanyi.jcomposecountrycodepicker.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.joelkanyi.ccp.transformation.PhoneNumberTransformation
import com.joelkanyi.jcomposecountrycodepicker.data.utils.checkPhoneNumber
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getDefaultLangCode
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getDefaultPhoneCode
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getLibCountries
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getNumberHint

private var fullNumberState: String by mutableStateOf("")
private var checkNumberState: Boolean by mutableStateOf(false)
private var phoneNumberState: String by mutableStateOf("")
private var countryCodeState: String by mutableStateOf("")

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ComposeCountryCodePicker(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    shape: Shape = MaterialTheme.shapes.medium,
    color: Color = MaterialTheme.colorScheme.background,
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = true,
    error: Boolean = false,
    placeholder: @Composable ((defaultLang: String) -> Unit) = { defaultLang ->
        Text(text = stringResource(id = getNumberHint(getLibCountries.single { it.countryCode == defaultLang }.countryCode.lowercase())))
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

    Surface(color = color) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
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
                    visualTransformation = PhoneNumberTransformation(getLibCountries.single { it.countryCode == defaultLang }.countryCode.uppercase()),
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
                        Row {
                            Column {
                                ComposePickerCodeDialog(
                                    pickedCountry = {
                                        phoneCode = it.countryPhoneCode
                                        defaultLang = it.countryCode
                                    },
                                    defaultSelectedCountry = getLibCountries.single { it.countryCode == defaultLang },
                                    showCountryCode = showCountryCode,
                                    showFlag = showCountryFlag,
                                )
                            }
                        }
                    },
                )
            }
        }
    }
}

fun getFullPhoneNumber(): String {
    return fullNumberState
}

fun getOnlyPhoneNumber(): String {
    return phoneNumberState
}

fun getErrorStatus(): Boolean {
    return !checkNumberState
}

fun isPhoneNumber(): Boolean {
    val check = checkPhoneNumber(
        phone = phoneNumberState,
        fullPhoneNumber = fullNumberState,
        countryCode = countryCodeState,
    )
    checkNumberState = check
    return check
}
