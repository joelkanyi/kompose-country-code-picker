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

import kotlin.js.ExperimentalWasmJsInterop
import kotlinx.browser.window

@OptIn(ExperimentalWasmJsInterop::class)
private fun jsGetLanguages(): JsArray<JsString>? = js("navigator.languages")

@OptIn(ExperimentalWasmJsInterop::class)
private fun jsGetTimezone(): JsString? = js("Intl.DateTimeFormat().resolvedOptions().timeZone")

internal actual fun getDefaultCountryCode(): String {
    // 1. Try navigator.languages (preferred, returns array like ["en-US", "en"])
    try {
        @OptIn(ExperimentalWasmJsInterop::class)
        val jsLangs = jsGetLanguages()
        if (jsLangs != null) {
            val languages = mutableListOf<String>()
            for (i in 0 until jsLangs.length) {
                languages.add(jsLangs[i].toString())
            }
            if (languages.isNotEmpty()) {
                val region = TimezoneCountryMap.extractRegionFromLocales(languages)
                if (region != null) return region
            }
        }
    } catch (_: Throwable) { /* ignore */ }

    // 2. Try navigator.language (single locale like "en-US")
    val region = TimezoneCountryMap.extractRegionFromLocale(window.navigator.language)
    if (region != null) return region

    // 3. Try Intl.DateTimeFormat timezone as fallback
    try {
        @OptIn(ExperimentalWasmJsInterop::class)
        val timezone = jsGetTimezone()?.toString()
        if (timezone != null) {
            val country = TimezoneCountryMap.getCountryFromTimezone(timezone)
            if (country != null) return country
        }
    } catch (_: Throwable) { /* ignore */ }

    return "us"
}

internal actual fun isNonSeparator(c: Char): Boolean = c.isDigit() || c == '+' || c == '*' || c == '#' ||
    c == 'N' || c == 'n' || c == 'P' || c == 'p' ||
    c == ',' || c == ';'

internal actual fun isPhoneNumberValid(phoneNumber: String): Boolean = PhoneValidation.isValidPhoneNumber(phoneNumber)

internal actual fun createAsYouTypeFormatter(countryCode: String): PhoneFormatter = CommonPhoneFormatter(countryCode)

@OptIn(ExperimentalWasmJsInterop::class)
internal actual fun String.normalizeUnicode(): String {
    val jsStr: JsString = this.toJsString()
    val normalized: JsString = jsNormalize(jsStr, "NFD".toJsString())
    return normalized.toString()
}

@OptIn(ExperimentalWasmJsInterop::class)
private fun jsNormalize(str: JsString, form: JsString): JsString = js("str.normalize(form)")
