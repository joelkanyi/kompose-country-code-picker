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

import com.google.common.truth.Truth.assertThat
import com.joelkanyi.jcomposecountrycodepicker.data.Country
import com.joelkanyi.jcomposecountrycodepicker.fake.FakeCountryCodePicker
import org.junit.Test

class CountryCodePickerTest {
    private val countryCodePicker = FakeCountryCodePicker(
        phone = "712345678",
        code = "KE",
        countries = listOf(
            Country(
                code = "KE",
                phoneNoCode = "+254",
                name = "Kenya",
                flag = 0,
            ),
        ),
    )

    @Test
    fun testGetCountryName() {
        // Given
        val expected = "Kenya"

        // When
        val result = countryCodePicker.getCountryName()

        // Then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testGetCountryPhoneCode() {
        // Given
        val expected = "+254"

        // When
        val result = countryCodePicker.getCountryPhoneCode()

        // Then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testGetCountryPhoneCodeWithoutPrefix() {
        // Given
        val expected = "254"

        // When
        val result = countryCodePicker.getCountryPhoneCodeWithoutPrefix()

        // Then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testGetPhoneNumberWithoutPrefix() {
        // Given
        val expected = "712345678"

        // When
        val result = countryCodePicker.getPhoneNumberWithoutPrefix()

        // Then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testGetFullPhoneNumberWithoutPrefix() {
        // Given
        val expected = "254712345678"

        // When
        val result = countryCodePicker.getFullPhoneNumberWithoutPrefix()

        // Then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testIsPhoneNumberValid() {
        // Given
        val expected = true

        // When
        val result = countryCodePicker.isPhoneNumberValid()

        // Then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testGetFullyFormattedPhoneNumber() {
        // Given
        val expected = "+254712345678"

        // When
        val result = countryCodePicker.getFullyFormattedPhoneNumber()

        // Then
        assertThat(result).isEqualTo(expected)
    }
}
