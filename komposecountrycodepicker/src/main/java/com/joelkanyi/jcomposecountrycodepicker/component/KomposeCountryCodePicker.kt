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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joelkanyi.jcomposecountrycodepicker.annotation.RestrictedApi
import com.joelkanyi.jcomposecountrycodepicker.data.Country
import com.joelkanyi.jcomposecountrycodepicker.data.FlagSize
import com.joelkanyi.jcomposecountrycodepicker.utils.PhoneNumberTransformation
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.extractCountryCodeAndPhoneNumber
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.getCountry
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.removeSpecialCharacters

/**
 * [CountryCodePicker] is an interface that provides the different
 * utilities for the country code picker.
 */
@Stable
public interface CountryCodePicker {
    /** Returns the phone number. */
    public val phoneNumber: String

    /** Returns the country code e.g KE. */
    public val countryCode: String

    /** Shows the country code in the text field if true. */
    public val showCountryCode: Boolean

    /** Shows the country flag in the text field if true. */
    public val showCountryFlag: Boolean

    /**
     * Returns the list of countries to be displayed in the country code picker
     * dialog.
     */
    public val countryList: List<Country>

    /** Returns the country name. i.e Kenya. */
    public fun getCountryName(): String

    /** Returns the phone number code of the country with a prefix. e.g +254. */
    public fun getCountryPhoneCode(): String

    /** Returns the phone number code of the country without a prefix. e.g 254. */
    public fun getCountryPhoneCodeWithoutPrefix(): String

    /** Returns the phone number without the prefix. e.g 712345678. */
    public fun getPhoneNumberWithoutPrefix(): String

    /** Returns the full phone number without the prefix. e.g 254712345678. */
    public fun getFullPhoneNumberWithoutPrefix(): String

    /** Returns the full phone number with the prefix. e.g +254712345678. */
    public fun getFullPhoneNumber(): String

    /** Returns true if the phone number is valid. */
    public fun isPhoneNumberValid(phoneNumber: String = getFullPhoneNumber()): Boolean

    /** Returns fully formatted phone number. */
    public fun getFullyFormattedPhoneNumber(): String

    /** Sets the phone number. */
    @RestrictedApi
    public fun setPhoneNo(phoneNumber: String)

    /** Sets the country code. */
    @RestrictedApi
    public fun setCode(countryCode: String)
}

/**
 * [CountryCodePickerImpl] is a class that implements the
 * [CountryCodePicker] interface.
 *
 * @param defaultCountryCode The default country code to be displayed in
 *    the text field.
 * @param limitedCountries The list of countries to be displayed in the
 *    country code picker dialog.
 * @param priorityCountries The list of countries to be prioritized
 *   in the country code picker dialog.
 * @param showCode If true, the country code will be shown in the text
 *    field.
 * @param showFlag If true, the country flag will be shown in the text
 *    field.
 */
@OptIn(RestrictedApi::class)
@Stable
internal class CountryCodePickerImpl(
    val defaultCountryCode: String,
    val limitedCountries: List<String>,
    val priorityCountries: List<String>,
    val showCode: Boolean,
    val showFlag: Boolean,
) : CountryCodePicker {
    /** A mutable state of [_phoneNumber] that holds the phone number. */
    private val _phoneNumber = mutableStateOf("")
    override val phoneNumber: String
        get() = if (_phoneNumber.value.startsWith("0")) {
            _phoneNumber.value.removeSpecialCharacters()
        } else {
            "0${_phoneNumber.value.removeSpecialCharacters()}"
        }

    /** A mutable state of [_countryCode] that holds the country code. */
    private val _countryCode = mutableStateOf(
        defaultCountryCode,
    )
    override val countryCode: String
        get() = _countryCode.value

    /**
     * A mutable state of [_showCountryCode] that holds the value of
     * [showCountryCode].
     */
    private val _showCountryCode = mutableStateOf(showCode)
    override val showCountryCode: Boolean
        get() = _showCountryCode.value

    /**
     * A mutable state of [_showCountryFlag] that holds the value of
     * [showCountryFlag].
     */
    private val _showCountryFlag = mutableStateOf(showFlag)
    override val showCountryFlag: Boolean
        get() = _showCountryFlag.value

    /**
     * A mutable state of [_countryList] that holds the list of countries to be
     * displayed in the country code picker dialog.
     */
    private val _countryList = mutableStateOf(
        if (limitedCountries.isEmpty()) {
            PickerUtils.allCountries
        } else {
            PickerUtils.getLimitedCountries(limitedCountries)
        }.let {
            if (priorityCountries.isNotEmpty()) {
                PickerUtils.sortCountriesWithPriority(
                    countries = it,
                    priorityCountries = priorityCountries,
                )
            } else {
                it
            }
        },
    )
    override val countryList: List<Country>
        get() = _countryList.value

    override fun getCountryName(): String = countryCode.getCountry().name.replaceFirstChar {
        it.uppercase()
    }

    override fun getCountryPhoneCode(): String = countryCode.getCountry().phoneNoCode

    override fun getCountryPhoneCodeWithoutPrefix(): String = countryCode.getCountry().phoneNoCode.removePrefix("+")

    override fun getPhoneNumberWithoutPrefix(): String = phoneNumber.removeSpecialCharacters().removePrefix("0")

    override fun getFullPhoneNumberWithoutPrefix(): String = getCountryPhoneCodeWithoutPrefix() + phoneNumber.removeSpecialCharacters()
        .removePrefix("0")

    override fun getFullPhoneNumber(): String = getCountryPhoneCode() + phoneNumber.removeSpecialCharacters()
        .removePrefix("0")

    override fun isPhoneNumberValid(phoneNumber: String): Boolean = PickerUtils.isValid(phoneNumber)

    override fun getFullyFormattedPhoneNumber(): String = PhoneNumberTransformation(countryCode).filter(
        buildAnnotatedString {
            append(getFullPhoneNumber())
        },
    ).text.toString()

    override fun setPhoneNo(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    override fun setCode(countryCode: String) {
        _countryCode.value = countryCode
    }

    companion object {
        val Saver: Saver<CountryCodePickerImpl, *> = listSaver(
            save = {
                listOf(
                    it.countryCode,
                    it.limitedCountries,
                    it.priorityCountries,
                    it.showCode,
                    it.showFlag,
                )
            },
            restore = {
                CountryCodePickerImpl(
                    defaultCountryCode = it[0] as String,
                    limitedCountries = it[1] as List<String>,
                    priorityCountries = it[1] as List<String>,
                    showCode = it[2] as Boolean,
                    showFlag = it[3] as Boolean,
                )
            },
        )
    }
}

/**
 * [KomposeCountryCodePickerDefaults] is a class that holds the default
 * values for the country code picker.
 *
 * @param context The context to be used to get the default country code.
 */
public class KomposeCountryCodePickerDefaults(
    context: Context,
) {
    public val selectedCountryCode: String = PickerUtils.getDefaultLangCode(context)
}

/**
 * Creates a [CountryCodePicker] that is remembered across compositions.
 *
 * @param defaultCountryCode The default country code to be displayed in
 *    the text field.
 * @param limitedCountries The list of countries to be displayed in the
 *    country code picker dialog.
 * @param showCountryCode If true, the country code will be shown in the
 *    text field.
 * @param showCountryFlag If true, the country flag will be shown in the
 *    text field.
 * @return A [CountryCodePicker] that holds the different utilities for the
 *    country code picker.
 * @see CountryCodePicker.getCountryPhoneCode
 * @see CountryCodePicker.getCountryPhoneCodeWithoutPrefix
 * @see CountryCodePicker.getCountryName
 * @see CountryCodePicker.getFullPhoneNumber
 * @see CountryCodePicker.getFullPhoneNumberWithoutPrefix
 * @see CountryCodePicker.getPhoneNumberWithoutPrefix
 * @see CountryCodePicker.isPhoneNumberValid
 */
@Composable
public fun rememberKomposeCountryCodePickerState(
    defaultCountryCode: String? = null,
    limitedCountries: List<String> = emptyList(),
    priorityCountries: List<String> = emptyList(),
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = true,
): CountryCodePicker {
    val context = LocalContext.current

    return rememberSaveable(saver = CountryCodePickerImpl.Saver) {
        val countryCode =
            defaultCountryCode ?: KomposeCountryCodePickerDefaults(context).selectedCountryCode
        CountryCodePickerImpl(
            defaultCountryCode = countryCode.lowercase(),
            limitedCountries = limitedCountries,
            priorityCountries = priorityCountries,
            showCode = showCountryCode,
            showFlag = showCountryFlag,
        )
    }
}

/**
 * [KomposeCountryCodePicker] is a composable that displays a text field
 * with a country code picker dialog.
 *
 * @param state The state of the country code picker.
 * @param text The text to be displayed in the text field.
 * @param modifier Modifier to be applied to the layout.
 * @param onValueChange Called when the value is changed.
 * @param error If true, the text field will be displayed in the error
 *    state.
 * @param showOnlyCountryCodePicker If true, only the country code picker
 *    will be displayed.
 * @param shape The shape of the text field's outline.
 * @param placeholder The placeholder to be displayed in the text field.
 * @param colors The colors to be used to display the text field.
 * @param trailingIcon The trailing icon to be displayed in the text field.
 * @param countrySelectionDialogContainerColor The color to be used to
 *    display the country selection dialog container.
 * @param countrySelectionDialogContentColor The color to be used to
 *    display the country selection dialog content. text.
 * @param interactionSource The MutableInteractionSource representing the
 *    stream of Interactions for this text field.
 * @param selectedCountryFlagSize The size of the selected country flag
 *    (width and height in `.dp`).
 * @param textStyle The style to be used for displaying text on the
 *    `TextField` and the selected country.
 * @param enabled Controls the enabled state of the text field.
 * @param keyboardOptions The keyboard options to be used to display the
 *   keyboard.
 *   @param keyboardActions The keyboard actions to be used to display
 *   the keyboard.
 */
@OptIn(RestrictedApi::class)
@Composable
public fun KomposeCountryCodePicker(
    state: CountryCodePicker,
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    error: Boolean = false,
    showOnlyCountryCodePicker: Boolean = false,
    shape: Shape = MaterialTheme.shapes.medium,
    placeholder: @Composable ((defaultLang: String) -> Unit) = { defaultLang ->
        DefaultPlaceholder(defaultLang)
    },
    colors: TextFieldColors = TextFieldDefaults.colors(),
    trailingIcon: @Composable (() -> Unit)? = null,
    countrySelectionDialogContainerColor: Color = MaterialTheme.colorScheme.background,
    countrySelectionDialogContentColor: Color = MaterialTheme.colorScheme.onBackground,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    selectedCountryFlagSize: FlagSize = FlagSize(28.dp, 18.dp),
    textStyle: TextStyle = LocalTextStyle.current,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Phone,
        imeAction = ImeAction.Next,
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    var openCountrySelectionDialog by rememberSaveable { mutableStateOf(false) }

    val phoneTextPair = extractCountryCodeAndPhoneNumber(text)
    val countryCode = phoneTextPair.first
    val phoneNo = phoneTextPair.second

    LaunchedEffect(countryCode) {
        countryCode?.let { state.setCode(it) }
    }

    LaunchedEffect(phoneNo) {
        state.setPhoneNo(phoneNo)
    }

    if (openCountrySelectionDialog) {
        CountrySelectionDialog(
            countryList = state.countryList,
            onDismissRequest = {
                openCountrySelectionDialog = false
            },
            onSelect = { countryItem ->
                state.setCode(countryItem.code)
                openCountrySelectionDialog = false
            },
            containerColor = countrySelectionDialogContainerColor,
            contentColor = countrySelectionDialogContentColor,
        )
    }

    /**
     * if [showOnlyCountryCodePicker] is true, only the country code picker
     * will be displayed.
     */
    if (showOnlyCountryCodePicker) {
        SelectedCountryComponent(
            modifier = modifier,
            selectedCountry = state.countryCode.getCountry(),
            showCountryCode = state.showCountryCode,
            showFlag = state.showCountryFlag,
            onClickSelectedCountry = {
                if (enabled) {
                    openCountrySelectionDialog = true
                }
            },
            selectedCountryFlagSize = selectedCountryFlagSize,
            textStyle = textStyle,
        )
    } else {
        OutlinedTextField(
            modifier = modifier
                .qaAutomationTestTag("countryCodePickerTextField"),
            shape = shape,
            value = phoneNo,
            onValueChange = {
                if (text != it && showOnlyCountryCodePicker.not()) {
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
                state.countryCode.uppercase(),
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            leadingIcon = {
                SelectedCountryComponent(
                    selectedCountry = state.countryCode.getCountry(),
                    showCountryCode = state.showCountryCode,
                    showFlag = state.showCountryFlag,
                    onClickSelectedCountry = {
                        if (enabled) {
                            openCountrySelectionDialog = true
                        }
                    },
                    selectedCountryFlagSize = selectedCountryFlagSize,
                    textStyle = textStyle,
                )
            },
            trailingIcon = trailingIcon,
            interactionSource = interactionSource,
            textStyle = textStyle,
            enabled = enabled,
        )
    }
}

/**
 * [DefaultPlaceholder] is a composable that displays the default
 * placeholder.
 *
 * @param defaultLang The default language code.
 * @param modifier Modifier to be applied to the layout.
 */
@Composable
private fun DefaultPlaceholder(
    defaultLang: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.qaAutomationTestTag("defaultPlaceholder"),
        text = stringResource(id = PickerUtils.getNumberHint(PickerUtils.allCountries.single { it.code == defaultLang }.code.lowercase())),
        style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.ExtraLight,
        ),
    )
}

/**
 * [SelectedCountryComponent] is a composable that displays the
 *
 * @param selectedCountry] The selected country.
 * @param selectedCountryFlagSize] The size of the selected country flag.
 * @param textStyle The style to be used to display the text.
 * @param onClickSelectedCountry Called when the selected country is
 *    clicked.
 * @param modifier Modifier to be applied to the layout.
 * @param selectedCountryPadding The padding to be applied to the selected
 *    country.
 * @param showCountryCode If true, the country code will be shown.
 * @param showFlag] If true, the country flag will be shown.
 * @param showCountryName If true, the country name will be shown.
 */
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
private fun SelectedCountryComponent(
    selectedCountry: Country,
    selectedCountryFlagSize: FlagSize,
    textStyle: TextStyle,
    onClickSelectedCountry: () -> Unit,
    modifier: Modifier = Modifier,
    selectedCountryPadding: Dp = 8.dp,
    showCountryCode: Boolean = true,
    showFlag: Boolean = true,
    showCountryName: Boolean = false,
) {
    Row(
        modifier = modifier
            .padding(selectedCountryPadding)
            .qaAutomationTestTag("selectedCountryComponent")
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
                modifier = Modifier
                    .width(selectedCountryFlagSize.width)
                    .height(selectedCountryFlagSize.height)
                    .qaAutomationTestTag("countryFlag"),
                painter = painterResource(
                    id = PickerUtils.getFlags(
                        selectedCountry.code,
                    ),
                ),
                contentDescription = selectedCountry.name,
            )
        }
        if (showCountryCode) {
            Text(
                text = selectedCountry.phoneNoCode,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .qaAutomationTestTag("countryCode"),
                style = textStyle,
            )
        }
        if (showCountryName) {
            Text(
                text = stringResource(id = PickerUtils.getCountryName(selectedCountry.code.lowercase())),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .qaAutomationTestTag("countryName"),
                style = textStyle,
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier.qaAutomationTestTag("countryDropDown"),
        )
    }
}

/**
 * [qaAutomationTestTag] is a composable that adds a test tag to important
 * components in the country code picker, to be used for QA automation.
 * @param tag The tag to be added to the component.
 * @return A [Modifier] that adds a test tag to the component.
 */
@OptIn(ExperimentalComposeUiApi::class)
public fun Modifier.qaAutomationTestTag(tag: String): Modifier = this.then(
    Modifier.semantics {
        this.testTag = tag
        this.testTagsAsResourceId = true
    },
)
