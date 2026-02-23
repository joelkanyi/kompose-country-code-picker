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
package com.joelkanyi.jcomposecountrycodepicker.component

import com.joelkanyi.jcomposecountrycodepicker.data.Country
import com.joelkanyi.jcomposecountrycodepicker.fake.FakeCountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.resources.Res
import com.joelkanyi.jcomposecountrycodepicker.resources.ke
import kotlin.test.Test
import kotlin.test.assertEquals

class CountryCodePickerTest {
    private val countryCodePicker = FakeCountryCodePicker(
        phone = "712345678",
        code = "KE",
        countries = listOf(
            Country(
                code = "KE",
                phoneNoCode = "+254",
                name = "Kenya",
                flag = Res.drawable.ke,
            ),
        ),
    )

    @Test
    fun testGetCountryName() {
        val expected = "Kenya"
        val result = countryCodePicker.getCountryName()
        assertEquals(expected, result)
    }

    @Test
    fun testGetCountryPhoneCode() {
        val expected = "+254"
        val result = countryCodePicker.getCountryPhoneCode()
        assertEquals(expected, result)
    }

    @Test
    fun testGetCountryPhoneCodeWithoutPrefix() {
        val expected = "254"
        val result = countryCodePicker.getCountryPhoneCodeWithoutPrefix()
        assertEquals(expected, result)
    }

    @Test
    fun testGetPhoneNumberWithoutPrefix() {
        val expected = "712345678"
        val result = countryCodePicker.getPhoneNumberWithoutPrefix()
        assertEquals(expected, result)
    }

    @Test
    fun testGetFullPhoneNumberWithoutPrefix() {
        val expected = "254712345678"
        val result = countryCodePicker.getFullPhoneNumberWithoutPrefix()
        assertEquals(expected, result)
    }

    @Test
    fun testIsPhoneNumberValid() {
        val result = countryCodePicker.isPhoneNumberValid()
        assertEquals(true, result)
    }

    @Test
    fun testGetFullyFormattedPhoneNumber() {
        val expected = "+254712345678"
        val result = countryCodePicker.getFullyFormattedPhoneNumber()
        assertEquals(expected, result)
    }
}
