/*
 * Copyright 2024 Joel Kanyi.
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
package com.joelkanyi.jcomposecountrycodepicker.utils

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.text.Normalizer

internal actual fun getDefaultCountryCode(): String = java.util.Locale.getDefault().country.lowercase().ifEmpty { "us" }

internal actual fun isNonSeparator(c: Char): Boolean = c.isDigit() || c == '+' || c == '*' || c == '#' ||
    c == 'N' || c == 'n' || c == 'P' || c == 'p' ||
    c == ',' || c == ';'

internal actual fun isPhoneNumberValid(phoneNumber: String): Boolean = try {
    val parsed = PhoneNumberUtil.getInstance().parse(
        phoneNumber.trim(),
        PhoneNumberUtil.REGION_CODE_FOR_NON_GEO_ENTITY,
    )
    PhoneNumberUtil.getInstance().isValidNumber(parsed)
} catch (_: NumberParseException) {
    false
}

internal actual fun createAsYouTypeFormatter(countryCode: String): PhoneFormatter {
    val formatter = PhoneNumberUtil.getInstance().getAsYouTypeFormatter(countryCode.uppercase())
    return object : PhoneFormatter {
        override fun clear() = formatter.clear()
        override fun inputDigit(digit: Char): String? = formatter.inputDigit(digit)
        override fun inputDigitAndRememberPosition(digit: Char): String? = formatter.inputDigitAndRememberPosition(digit)
    }
}

internal actual fun String.normalizeUnicode(): String = Normalizer.normalize(this, Normalizer.Form.NFD)
