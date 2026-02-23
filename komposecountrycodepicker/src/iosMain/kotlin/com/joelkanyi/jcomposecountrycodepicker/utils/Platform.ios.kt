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

import platform.Foundation.NSLocale
import platform.Foundation.NSString
import platform.Foundation.countryCode
import platform.Foundation.currentLocale
import platform.Foundation.decomposedStringWithCanonicalMapping

internal actual fun getDefaultCountryCode(): String = NSLocale.currentLocale.countryCode?.lowercase() ?: "us"

internal actual fun isNonSeparator(c: Char): Boolean = c.isDigit() || c == '+' || c == '*' || c == '#' ||
    c == 'N' || c == 'n' || c == 'P' || c == 'p' ||
    c == ',' || c == ';'

internal actual fun isPhoneNumberValid(phoneNumber: String): Boolean = PhoneValidation.isValidPhoneNumber(phoneNumber)

internal actual fun createAsYouTypeFormatter(countryCode: String): PhoneFormatter = CommonPhoneFormatter(countryCode)

internal actual fun String.normalizeUnicode(): String {
    @Suppress("CAST_NEVER_SUCCEEDS")
    val nsString = this as NSString
    return nsString.decomposedStringWithCanonicalMapping
}
