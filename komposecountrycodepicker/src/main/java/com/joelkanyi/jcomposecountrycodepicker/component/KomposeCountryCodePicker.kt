/*
 * Copyright 2023 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joelkanyi.jcomposecountrycodepicker.component

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joelkanyi.jcomposecountrycodepicker.data.CountryData
import com.joelkanyi.jcomposecountrycodepicker.utils.PhoneNumberTransformation
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.removeSpecialCharacters

/**
 * [CountryCodePicker] is an interface that provides the different utilities for the country code picker.
 */
@Stable
interface CountryCodePicker {
    /** Returns the phone number.*/
    var phoneNumber: String

    /** Returns the country code e.g KE.*/
    var countryCode: String

    /** Returns the selected country.*/
    var country: CountryData

    /** Shows the country code in the text field if true.*/
    val showCountryCode: Boolean

    /** Shows the country flag in the text field if true.*/
    val showCountryFlag: Boolean

    /** Receives the limited countries to be displayed in the country code picker dialog.*/
    val limitedCountries: List<String>

    /** If not null, the default country code to be displayed in the text field.*/
    val defaultCountryCode: String?

    /** Returns the list of countries to be displayed in the country code picker dialog.*/
    val countryList: List<CountryData>

    /** Returns the country name. i.e Kenya.*/
    fun getCountryName(): String

    /** Returns the phone number code of the country with a prefix. e.g +254.*/
    fun getCountryPhoneCode(): String

    /** Returns the phone number code of the country without a prefix. e.g 254.*/
    fun getCountryPhoneCodeWithoutPrefix(): String

    /** Returns the phone number without the prefix. e.g 712345678.*/
    fun getPhoneNumberWithoutPrefix(): String

    /** Returns the full phone number without the prefix. e.g 254712345678.*/
    fun getFullPhoneNumberWithoutPrefix(): String

    /** Returns the full phone number with the prefix. e.g +254712345678.*/
    fun getFullPhoneNumber(): String

    /** Returns true if the phone number is valid.*/
    fun isPhoneNumberValid(phoneNumber: String = getFullPhoneNumber()): Boolean
}

/**
 * [CountryCodePickerImpl] is a class that implements the [CountryCodePicker] interface.
 * @param defaultCountryCode The default country code to be displayed in the text field.
 * @param limitedCountries The list of countries to be displayed in the country code picker dialog.
 * @param showCountryCode If true, the country code will be shown in the text field.
 * @param showCountryFlag If true, the country flag will be shown in the text field.
 * @param context The context to be used to get the default country code.
 */
@Stable
internal class CountryCodePickerImpl(
    defaultCountryCode: String?,
    limitedCountries: List<String>,
    showCountryCode: Boolean,
    showCountryFlag: Boolean,
    context: Context,
) : CountryCodePicker {
    /** A mutable state of [_phoneNumber] that holds the phone number.*/
    private val _phoneNumber = mutableStateOf("")
    override var phoneNumber: String
        get() = if (_phoneNumber.value.startsWith("0")) {
            _phoneNumber.value.removeSpecialCharacters()
        } else {
            "0${_phoneNumber.value.removeSpecialCharacters()}"
        }
        set(value) {
            _phoneNumber.value = value
        }


    /** A mutable state of [_countryCode] that holds the country code.*/
    private val _countryCode = mutableStateOf(
        defaultCountryCode ?: KomposeCountryCodePickerDefaults(context).selectedCountryCode
    )
    override var countryCode: String
        get() = _countryCode.value
        set(value) {
            _countryCode.value = value
        }


    /** A mutable state of [_selectedCountry] that holds the selected country.*/
    private var _selectedCountry = mutableStateOf(
        PickerUtils.allCountries.single { it.countryCode == countryCode }
    )
    override var country: CountryData
        get() = _selectedCountry.value
        set(value) {
            _selectedCountry.value = value
            _countryCode.value = value.countryCode
        }


    /** A mutable state of [_showCountryCode] that holds the value of [showCountryCode].*/
    private val _showCountryCode = mutableStateOf(showCountryCode)
    override val showCountryCode: Boolean
        get() = _showCountryCode.value


    /** A mutable state of [_showCountryFlag] that holds the value of [showCountryFlag].*/
    private val _showCountryFlag = mutableStateOf(showCountryFlag)
    override val showCountryFlag: Boolean
        get() = _showCountryFlag.value


    /** A mutable state of [_limitedCountries] that holds the value of [limitedCountries].*/
    private val _limitedCountries = mutableStateOf(limitedCountries)
    override val limitedCountries: List<String>
        get() = _limitedCountries.value


    /** A mutable state of [_defaultCountryCode] that holds the value of [defaultCountryCode].*/
    private val _defaultCountryCode = mutableStateOf(defaultCountryCode)
    override val defaultCountryCode: String?
        get() = _defaultCountryCode.value


    /** A mutable state of [_countryList] that holds the list of countries to be displayed in the country code picker dialog.*/
    private val _countryList = mutableStateOf(
        if (limitedCountries.isEmpty()) {
            PickerUtils.allCountries
        } else {
            PickerUtils.allCountries.filter { country ->
                limitedCountries
                    .map { it.lowercase() }
                    .map { it.trim() }
                    .contains(country.countryCode) ||
                        limitedCountries.contains(country.cCountryPhoneNoCode) ||
                        limitedCountries.contains(country.cCountryName)
            }
        }
    )
    override val countryList: List<CountryData>
        get() = _countryList.value


    override fun getCountryName(): String {
        return country.cCountryName.replaceFirstChar {
            it.uppercase()
        }
    }

    override fun getCountryPhoneCode(): String {
        return country.cCountryPhoneNoCode
    }

    override fun getCountryPhoneCodeWithoutPrefix(): String {
        return country.cCountryPhoneNoCode.removePrefix("+")
    }

    override fun getPhoneNumberWithoutPrefix(): String {
        return phoneNumber.removeSpecialCharacters().removePrefix("0")
    }

    override fun getFullPhoneNumberWithoutPrefix(): String {
        return getCountryPhoneCodeWithoutPrefix() + phoneNumber.removeSpecialCharacters()
            .removePrefix("0")
    }

    override fun getFullPhoneNumber(): String {
        return getCountryPhoneCode() + phoneNumber.removeSpecialCharacters()
            .removePrefix("0")
    }

    override fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return PickerUtils.isValid(phoneNumber)
    }
}

class KomposeCountryCodePickerDefaults(
    context: Context,
) {
    val selectedCountryCode: String = PickerUtils.getDefaultLangCode(context)
}

/**
 * Creates a [CountryCodePicker] that is remembered across compositions.
 * @param defaultCountryCode The default country code to be displayed in the text field.
 * @param limitedCountries The list of countries to be displayed in the country code picker dialog.
 * @param showCountryCode If true, the country code will be shown in the text field.
 * @param showCountryFlag If true, the country flag will be shown in the text field.
 * @return A [CountryCodePicker] that holds the different utilities for the country code picker.
 * @see CountryCodePicker.getCountryPhoneCode
 * @see CountryCodePicker.getCountryPhoneCodeWithoutPrefix
 * @see CountryCodePicker.getCountryName
 * @see CountryCodePicker.getFullPhoneNumber
 * @see CountryCodePicker.getFullPhoneNumberWithoutPrefix
 * @see CountryCodePicker.getPhoneNumberWithoutPrefix
 * @see CountryCodePicker.isPhoneNumberValid
 */
@Composable
fun rememberKomposeCountryCodePickerState(
    defaultCountryCode: String? = null,
    limitedCountries: List<String> = emptyList(),
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = true,
): CountryCodePicker {
    val context = LocalContext.current
    return remember {
        CountryCodePickerImpl(
            defaultCountryCode = defaultCountryCode?.lowercase(),
            limitedCountries = limitedCountries,
            showCountryCode = showCountryCode,
            showCountryFlag = showCountryFlag,
            context = context,
        )
    }
}

/**
 * [KomposeCountryCodePicker] is a composable that displays a text field with a country code picker dialog.
 * [modifier] Modifier to be applied to the layout.
 * [text] The text to be displayed in the text field.
 * [onValueChange] Called when the value is changed.
 * [shape] The shape of the text field's outline.
 * [error] If true, the text field will be displayed in the error state.
 * [placeholder] The placeholder to be displayed in the text field.
 * [colors] The colors to be used to display the text field.
 * [trailingIcon] The trailing icon to be displayed in the text field.
 * [interactionSource] The MutableInteractionSource representing the stream of Interactions for this text field.
 *
 */
@Composable
fun KomposeCountryCodePicker(
    modifier: Modifier = Modifier,
    text: String = "",
    showOnlyCountryCodePicker: Boolean = false,
    onValueChange: (String) -> Unit = {},
    shape: Shape = MaterialTheme.shapes.medium,
    error: Boolean = false,
    placeholder: @Composable ((defaultLang: String) -> Unit) = { defaultLang ->
        DefaultPlaceholder(defaultLang)
    },
    colors: TextFieldColors = TextFieldDefaults.colors(),
    trailingIcon: @Composable (() -> Unit) = {},
    state: CountryCodePicker,
    countrySelectionDialogContainerColor: Color = MaterialTheme.colorScheme.background,
    countrySelectionDialogContentColor: Color = MaterialTheme.colorScheme.onBackground,
    pickerContentColor: Color = MaterialTheme.colorScheme.onBackground,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
) {
    val localTextInputService = LocalTextInputService.current
    var openCountrySelectionDialog by rememberSaveable { mutableStateOf(false) }

    state.phoneNumber = text

    if (openCountrySelectionDialog) {
        CountrySelectionDialog(
            countryList = state.countryList,
            onDismissRequest = {
                openCountrySelectionDialog = false
            },
            onSelected = { countryItem ->
                state.country = countryItem
                openCountrySelectionDialog = false
            },
            containerColor = countrySelectionDialogContainerColor,
            contentColor = countrySelectionDialogContentColor,
        )
    }

    /**
     * if [showOnlyCountryCodePicker] is true, only the country code picker will be displayed.
     */
    if (showOnlyCountryCodePicker) {
        SelectedCountryComponent(
            selectedCountry = state.country,
            showCountryCode = state.showCountryCode,
            showFlag = state.showCountryFlag,
            onClickSelectedCountry = {
                openCountrySelectionDialog = true
            },
            pickerContentColor = pickerContentColor,
        )
    } else {
        OutlinedTextField(
            modifier = modifier,
            shape = shape,
            value = text,
            onValueChange = {
                if (text != it) {
                    onValueChange(it)
                }
            },
            placeholder = {
                placeholder(state.countryCode)
            },
            singleLine = true,
            colors = colors,
            isError = error,
            visualTransformation = PhoneNumberTransformation(
                state.country.countryCode.uppercase()
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
                autoCorrect = true,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    localTextInputService?.hideSoftwareKeyboard()
                },
            ),
            leadingIcon = {
                SelectedCountryComponent(
                    selectedCountry = state.country,
                    showCountryCode = state.showCountryCode,
                    showFlag = state.showCountryFlag,
                    onClickSelectedCountry = {
                        openCountrySelectionDialog = true
                    },
                    pickerContentColor = pickerContentColor,
                )
            },
            trailingIcon = trailingIcon,
            interactionSource = interactionSource
        )
    }
}

@Composable
private fun DefaultPlaceholder(defaultLang: String) {
    Text(
        text = stringResource(id = PickerUtils.getNumberHint(PickerUtils.allCountries.single { it.countryCode == defaultLang }.countryCode.lowercase())),
        style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.ExtraLight,
        ),
    )
}

/**
 * [SelectedCountryComponent] is a composable that displays a dialog with a list of countries.
 * [modifier] Modifier to be applied to the layout.
 * [selectedCountryPadding] The padding to be applied to the selected country.
 * [selectedCountry] The selected country.
 * [showCountryCode] If true, the country code will be shown in the text field.
 * [showFlag] If true, the country flag will be shown in the text field.
 * [showCountryName] If true, the country name will be shown in the text field.
 */
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun SelectedCountryComponent(
    modifier: Modifier = Modifier,
    selectedCountry: CountryData,
    selectedCountryPadding: Dp = 8.dp,
    showCountryCode: Boolean = true,
    showFlag: Boolean = true,
    showCountryName: Boolean = false,
    onClickSelectedCountry: () -> Unit,
    pickerContentColor: Color,
) {
    Row(
        modifier = modifier
            .padding(selectedCountryPadding)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
            ) {
                onClickSelectedCountry()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showFlag) {
            Image(
                modifier = modifier
                    .width(28.dp)
                    .height(18.dp),
                painter = painterResource(
                    id = PickerUtils.getFlags(
                        selectedCountry.countryCode,
                    ),
                ),
                contentDescription = null,
            )
        }
        if (showCountryCode) {
            Text(
                text = selectedCountry.cCountryPhoneNoCode,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp),
                fontSize = 18.sp,
                color = pickerContentColor,
            )
        }
        if (showCountryName) {
            Text(
                text = stringResource(id = PickerUtils.getCountryName(selectedCountry.countryCode.lowercase())),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 6.dp),
                fontSize = 18.sp,
                color = pickerContentColor,
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            tint = pickerContentColor,
        )
    }
}
