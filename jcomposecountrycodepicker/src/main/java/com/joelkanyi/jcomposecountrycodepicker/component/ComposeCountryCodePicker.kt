package com.joelkanyi.jcomposecountrycodepicker.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getLibCountries
import com.joelkanyi.ccp.transformation.PhoneNumberTransformation
import com.joelkanyi.jcomposecountrycodepicker.R
import com.joelkanyi.jcomposecountrycodepicker.data.utils.checkPhoneNumber
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getDefaultLangCode
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getDefaultPhoneCode
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
    shape: Shape = RoundedCornerShape(24.dp),
    color: Color = MaterialTheme.colorScheme.background,
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = true,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.onSecondary,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    bottomStyle: Boolean = false,
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
            if (bottomStyle) {
                ComposePickerCodeDialog(
                    pickedCountry = {
                        phoneCode = it.countryPhoneCode
                        defaultLang = it.countryCode
                    },
                    defaultSelectedCountry = getLibCountries.single { it.countryCode == defaultLang },
                    showCountryCode = showCountryCode,
                    showFlag = showCountryFlag,
                    showCountryName = true,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                OutlinedTextField(
                    modifier = modifier.fillMaxWidth(),
                    shape = shape,
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it
                        if (text != it) {
                            onValueChange(it)
                        }
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (getErrorStatus()) Color.Red else focusedBorderColor,
                        unfocusedBorderColor = if (getErrorStatus()) Color.Red else unfocusedBorderColor,
                        cursorColor = cursorColor,
                    ),
                    visualTransformation = PhoneNumberTransformation(getLibCountries.single { it.countryCode == defaultLang }.countryCode.uppercase()),
                    placeholder = { Text(text = stringResource(id = getNumberHint(getLibCountries.single { it.countryCode == defaultLang }.countryCode.lowercase()))) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword,
                        autoCorrect = true,
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    leadingIcon = {
                        if (!bottomStyle) {
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
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            textFieldValue = ""
                            onValueChange("")
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear",
                                tint = if (getErrorStatus()) Color.Red else MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    },
                )
            }
            if (getErrorStatus()) {
                Text(
                    text = stringResource(id = R.string.invalid_number),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 0.8.dp),
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
