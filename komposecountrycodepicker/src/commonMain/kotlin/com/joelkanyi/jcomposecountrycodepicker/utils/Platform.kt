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

internal expect fun getDefaultCountryCode(): String

internal expect fun isNonSeparator(c: Char): Boolean

internal expect fun isPhoneNumberValid(phoneNumber: String): Boolean

internal interface PhoneFormatter {
    fun clear()
    fun inputDigit(digit: Char): String?
    fun inputDigitAndRememberPosition(digit: Char): String?
}

internal expect fun createAsYouTypeFormatter(countryCode: String): PhoneFormatter

internal expect fun String.normalizeUnicode(): String
