/*
 * Copyright 2025 Joel Kanyi.
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

import kotlin.test.Test
import kotlin.test.assertEquals

class CommonPhoneFormatterTest {

    @Test
    fun usFormatterGroupsCorrectly() {
        val formatter = CommonPhoneFormatter("us")
        "2025551234".forEach { formatter.inputDigit(it) }
        formatter.clear()

        // Re-format from scratch
        var result = ""
        "2025551234".forEach { result = formatter.inputDigit(it) }
        assertEquals("202 555 1234", result)
    }

    @Test
    fun kenyaFormatterGroupsCorrectly() {
        val formatter = CommonPhoneFormatter("ke")
        var result = ""
        "712345678".forEach { result = formatter.inputDigit(it) }
        assertEquals("712 345 678", result)
    }

    @Test
    fun ukFormatterGroupsCorrectly() {
        val formatter = CommonPhoneFormatter("gb")
        var result = ""
        "79111234567".forEach { result = formatter.inputDigit(it) }
        // UK pattern: 4, 3, 4 -> "7911 123 4567"
        assertEquals("7911 123 4567", result)
    }

    @Test
    fun singaporeFormatterGroupsCorrectly() {
        val formatter = CommonPhoneFormatter("sg")
        var result = ""
        "91234567".forEach { result = formatter.inputDigit(it) }
        // Singapore pattern: 4, 4 -> "9123 4567"
        assertEquals("9123 4567", result)
    }

    @Test
    fun clearResetsFormatter() {
        val formatter = CommonPhoneFormatter("us")
        "12345".forEach { formatter.inputDigit(it) }
        formatter.clear()
        var result = ""
        "67890".forEach { result = formatter.inputDigit(it) }
        assertEquals("678 90", result)
    }

    @Test
    fun singleDigitReturnsJustDigit() {
        val formatter = CommonPhoneFormatter("us")
        val result = formatter.inputDigit('1')
        assertEquals("1", result)
    }

    @Test
    fun unknownCountryUsesDefaultPattern() {
        val formatter = CommonPhoneFormatter("zz")
        var result = ""
        "1234567890".forEach { result = formatter.inputDigit(it) }
        // Default pattern: 3, 3, 4 -> "123 456 7890"
        assertEquals("123 456 7890", result)
    }

    @Test
    fun inputDigitAndRememberPositionWorks() {
        val formatter = CommonPhoneFormatter("us")
        var result = ""
        "2025551234".forEach { result = formatter.inputDigitAndRememberPosition(it) }
        assertEquals("202 555 1234", result)
    }

    @Test
    fun franceFormatterGroupsCorrectly() {
        val formatter = CommonPhoneFormatter("fr")
        var result = ""
        "612345678".forEach { result = formatter.inputDigit(it) }
        // France pattern: 1, 2, 2, 2, 2 -> "6 12 34 56 78"
        assertEquals("6 12 34 56 78", result)
    }

    @Test
    fun indiaFormatterGroupsCorrectly() {
        val formatter = CommonPhoneFormatter("in")
        var result = ""
        "9876543210".forEach { result = formatter.inputDigit(it) }
        // India pattern: 5, 5 -> "98765 43210"
        assertEquals("98765 43210", result)
    }
}
