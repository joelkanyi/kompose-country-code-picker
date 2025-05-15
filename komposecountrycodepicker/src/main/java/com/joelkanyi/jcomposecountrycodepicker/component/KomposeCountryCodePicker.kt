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

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.buildAnnotatedString
import com.joelkanyi.jcomposecountrycodepicker.annotation.RestrictedApi
import com.joelkanyi.jcomposecountrycodepicker.data.Country
import com.joelkanyi.jcomposecountrycodepicker.utils.PhoneNumberTransformation
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils
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
                    it.showCode,
                    it.showFlag,
                )
            },
            restore = {
                CountryCodePickerImpl(
                    defaultCountryCode = it[0] as String,
                    limitedCountries = it[1] as List<String>,
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
            showCode = showCountryCode,
            showFlag = showCountryFlag,
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
