/*
 * Copyright 2026 Joel Kanyi.
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

import com.joelkanyi.jcomposecountrycodepicker.data.Country
import com.joelkanyi.jcomposecountrycodepicker.resources.Res
import com.joelkanyi.jcomposecountrycodepicker.resources.ke
import com.joelkanyi.jcomposecountrycodepicker.resources.ug
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CountrySearchTest {

    @Test
    fun searchByCountryName() {
        val testData = listOf(
            Country("ke", "+254", "Kenya", Res.drawable.ke),
            Country("ug", "+256", "Uganda", Res.drawable.ug),
        )
        val expected = listOf(Country("ke", "+254", "Kenya", Res.drawable.ke))
        val result = testData.searchCountries("kEnYa")
        assertEquals(expected, result)
    }

    @Test
    fun searchByPhoneCode() {
        val countries = PickerUtils.allCountries
        val results = countries.searchCountries("+254")
        assertTrue(results.any { it.code == "ke" }, "Search by +254 should find Kenya")
    }

    @Test
    fun searchByCountryCode() {
        val countries = PickerUtils.allCountries
        val results = countries.searchCountries("ke")
        assertTrue(results.any { it.code == "ke" }, "Search by 'ke' should find Kenya")
    }

    @Test
    fun searchReturnsEmptyForNonexistentQuery() {
        val countries = PickerUtils.allCountries
        val results = countries.searchCountries("zzzzzzzzzzz")
        assertTrue(results.isEmpty())
    }

    @Test
    fun searchIsCaseInsensitive() {
        val countries = PickerUtils.allCountries
        val lower = countries.searchCountries("kenya")
        val upper = countries.searchCountries("KENYA")
        assertEquals(lower.size, upper.size)
    }
}
