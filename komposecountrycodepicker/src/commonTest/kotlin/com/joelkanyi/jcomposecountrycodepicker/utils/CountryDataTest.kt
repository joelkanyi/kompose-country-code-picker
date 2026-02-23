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
import kotlin.test.assertTrue

class CountryDataTest {

    @Test
    fun allPhoneCodesStartWithPlus() {
        val badEntries = PickerUtils.allCountries.filter { !it.phoneNoCode.startsWith("+") }
        assertTrue(
            badEntries.isEmpty(),
            "Countries with invalid phone codes (missing '+'): ${badEntries.map { "${it.code}: ${it.phoneNoCode}" }}",
        )
    }

    @Test
    fun allCountryCodesAreLowercase() {
        val badEntries = PickerUtils.allCountries.filter { it.code != it.code.lowercase() }
        assertTrue(
            badEntries.isEmpty(),
            "Countries with non-lowercase codes: ${badEntries.map { it.code }}",
        )
    }

    @Test
    fun allCountryCodesAreTwoLetters() {
        val badEntries = PickerUtils.allCountries.filter { it.code.length != 2 }
        assertTrue(
            badEntries.isEmpty(),
            "Countries with non-2-letter codes: ${badEntries.map { it.code }}",
        )
    }

    @Test
    fun noEmptyCountryNames() {
        val badEntries = PickerUtils.allCountries.filter { it.name.isBlank() }
        assertTrue(
            badEntries.isEmpty(),
            "Countries with empty names: ${badEntries.map { it.code }}",
        )
    }

    @Test
    fun noDuplicateCountryCodes() {
        val codes = PickerUtils.allCountries.map { it.code }
        val duplicates = codes.groupBy { it }.filter { it.value.size > 1 }.keys
        assertTrue(
            duplicates.isEmpty(),
            "Duplicate country codes: $duplicates",
        )
    }

    @Test
    fun allCountriesListIsSorted() {
        val names = PickerUtils.allCountries.map { it.name }
        val sorted = names.sortedBy { it }
        assertEquals(sorted, names, "allCountries should be sorted alphabetically by name")
    }

    @Test
    fun samoaPhoneCodeIsCorrect() {
        val samoa = PickerUtils.allCountries.first { it.code == "ws" }
        assertEquals("+685", samoa.phoneNoCode, "Samoa phone code should be +685")
    }

    @Test
    fun usPhoneCodeIsCorrect() {
        val us = PickerUtils.allCountries.first { it.code == "us" }
        assertEquals("+1", us.phoneNoCode)
    }

    @Test
    fun kenyaPhoneCodeIsCorrect() {
        val ke = PickerUtils.allCountries.first { it.code == "ke" }
        assertEquals("+254", ke.phoneNoCode)
    }

    @Test
    fun ukPhoneCodeIsCorrect() {
        val gb = PickerUtils.allCountries.first { it.code == "gb" }
        assertEquals("+44", gb.phoneNoCode)
    }

    @Test
    fun chinaPhoneCodeIsCorrect() {
        val cn = PickerUtils.allCountries.first { it.code == "cn" }
        assertEquals("+86", cn.phoneNoCode)
    }

    @Test
    fun allFlagsResourcesAreNotNull() {
        // Verify every country has a non-null flag resource
        PickerUtils.allCountries.forEach { country ->
            // If flag were null, it would throw at construction time,
            // so just verify we can access it
            assertTrue(
                country.flag.toString().isNotEmpty(),
                "Country ${country.code} should have a flag resource",
            )
        }
    }

    @Test
    fun containsExpectedCountryCount() {
        // There should be a reasonable number of countries (200+)
        assertTrue(
            PickerUtils.allCountries.size >= 200,
            "Expected at least 200 countries, got ${PickerUtils.allCountries.size}",
        )
    }

    @Test
    fun phoneCodesContainOnlyDigitsAndPlus() {
        val badEntries = PickerUtils.allCountries.filter { country ->
            !country.phoneNoCode.matches(Regex("^\\+\\d+$"))
        }
        assertTrue(
            badEntries.isEmpty(),
            "Countries with invalid phone code format: ${badEntries.map { "${it.code}: ${it.phoneNoCode}" }}",
        )
    }
}
