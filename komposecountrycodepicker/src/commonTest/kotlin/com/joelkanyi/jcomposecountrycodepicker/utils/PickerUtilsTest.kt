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

import com.joelkanyi.jcomposecountrycodepicker.data.Country
import com.joelkanyi.jcomposecountrycodepicker.resources.Res
import com.joelkanyi.jcomposecountrycodepicker.resources.ke
import com.joelkanyi.jcomposecountrycodepicker.resources.ug
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.getCountry
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.removeSpecialCharacters
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.searchForAnItem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PickerUtilsTest {

    // --- getCountry edge cases ---
    @Test
    fun getCountry() {
        val testData = "kE"
        val expected = "Kenya"
        val result = testData.getCountry()
        assertEquals(expected, result.name)
    }

    @Test
    fun getCountry_default() {
        val testData = "does_not_exist"
        val expected = "United States"
        val result = testData.getCountry()
        assertEquals(expected, result.name)
    }

    @Test
    fun testIsValid() {
        val testData = "+254712345678"
        val result = PickerUtils.isValid(testData)
        assertTrue(result)
    }

    @Test
    fun testRemoveSpecialCharacters() {
        val testData = "+-,`˜@/.!#%^&*()254712345678_+$"
        val result = testData.removeSpecialCharacters()
        assertEquals("254712345678", result)
    }

    @Test
    fun testSearchForAnItem() {
        val testData = listOf(
            Country("ke", "+254", "Kenya", Res.drawable.ke),
            Country("ug", "+256", "Uganda", Res.drawable.ug),
        )
        val correctResult = listOf(Country("ke", "+254", "Kenya", Res.drawable.ke))
        val result = testData.searchForAnItem("kEnYa")
        assertEquals(correctResult, result)
    }

    @Test
    fun testReturnCountryCodeAndPhoneNumberCorrectly() {
        val wholePhoneNumber = "+254712345678"
        val expectedCountryCode = "ke"
        val expectedPhoneNo = "712345678"
        val (countryCode, phoneNo) = PickerUtils.extractCountryCodeAndPhoneNumber(wholePhoneNumber)
        assertEquals(expectedCountryCode, countryCode)
        assertEquals(expectedPhoneNo, phoneNo)
    }

    @Test
    fun testReturnCorrectPhoneNumberWhenCountryCodeIsNotPresent() {
        val wholePhoneNumber = "712345678"
        val phoneNumber = PickerUtils.extractCountryCodeAndPhoneNumber(wholePhoneNumber).second
        assertEquals(wholePhoneNumber, phoneNumber)
    }

    @Test
    fun testReturnsLimitedCountries() {
        val limitedCountriesString = listOf(
            "UG",
            "255",
            "+260",
            "Kenya",
            "ng",
        )
        val result = PickerUtils.getLimitedCountries(limitedCountriesString).map { it.name }
        assertEquals(5, result.size)
    }

    @Test
    fun testSortCountriesWithPriorityCountries() {
        val priorityCountries = listOf("ug", "ke", "tz")
        val countries = PickerUtils.allCountries
        val sortedCountries = PickerUtils.sortCountriesWithPriority(countries, priorityCountries)
        assertEquals(
            listOf("+256", "+254", "+255"),
            sortedCountries.map { it.phoneNoCode }.take(3),
        )
    }

    // --- Additional edge cases ---

    @Test
    fun getCountryWithEmptyString() {
        val result = "".getCountry()
        assertEquals("United States", result.name)
    }

    @Test
    fun getCountryIsCaseInsensitive() {
        assertEquals("ke".getCountry().name, "KE".getCountry().name)
        assertEquals("ke".getCountry().name, "Ke".getCountry().name)
    }

    @Test
    fun extractCountryCodeForKenyaNumber() {
        val (code, phone) = PickerUtils.extractCountryCodeAndPhoneNumber("+254712345678")
        assertEquals("ke", code)
        assertEquals("712345678", phone)
    }

    @Test
    fun extractCountryCodeForGermanyNumber() {
        val (code, phone) = PickerUtils.extractCountryCodeAndPhoneNumber("+4915112345678")
        assertEquals("de", code)
        assertEquals("15112345678", phone)
    }

    @Test
    fun extractCountryCodeReturnsNullForLocalNumber() {
        val (code, phone) = PickerUtils.extractCountryCodeAndPhoneNumber("0712345678")
        assertNull(code)
        assertEquals("0712345678", phone)
    }

    @Test
    fun extractCountryCodeHandlesEmptyString() {
        val (code, phone) = PickerUtils.extractCountryCodeAndPhoneNumber("")
        assertNull(code)
        assertEquals("", phone)
    }

    @Test
    fun searchByPhoneCode() {
        val countries = PickerUtils.allCountries
        val results = countries.searchForAnItem("+254")
        assertTrue(results.any { it.code == "ke" }, "Search by +254 should find Kenya")
    }

    @Test
    fun searchByCountryCode() {
        val countries = PickerUtils.allCountries
        val results = countries.searchForAnItem("ke")
        assertTrue(results.any { it.code == "ke" }, "Search by 'ke' should find Kenya")
    }

    @Test
    fun searchReturnsEmptyForNonexistentQuery() {
        val countries = PickerUtils.allCountries
        val results = countries.searchForAnItem("zzzzzzzzzzz")
        assertTrue(results.isEmpty())
    }

    @Test
    fun searchIsCaseInsensitive() {
        val countries = PickerUtils.allCountries
        val lower = countries.searchForAnItem("kenya")
        val upper = countries.searchForAnItem("KENYA")
        assertEquals(lower.size, upper.size)
    }

    @Test
    fun sortWithEmptyPriorityReturnsAlphabetical() {
        val countries = PickerUtils.allCountries
        val sorted = PickerUtils.sortCountriesWithPriority(countries, emptyList())
        // Should be sorted alphabetically by name
        val names = sorted.map { it.name.lowercase() }
        assertEquals(names.sorted(), names)
    }

    @Test
    fun getLimitedCountriesWithSingleCode() {
        val result = PickerUtils.getLimitedCountries(listOf("KE"))
        assertEquals(1, result.size)
        assertEquals("ke", result.first().code)
    }

    @Test
    fun getLimitedCountriesWithEmptyListReturnsEmpty() {
        val result = PickerUtils.getLimitedCountries(emptyList())
        assertTrue(result.isEmpty())
    }

    @Test
    fun getCountryForAllKnownCodes() {
        // Verify that getCountry works for every country code in the list
        PickerUtils.allCountries.forEach { country ->
            val result = country.code.getCountry()
            assertEquals(country.code, result.code, "getCountry(${country.code}) returned wrong code")
        }
    }

    @Test
    fun getFlagsForAllKnownCodes() {
        // Verify getFlags doesn't throw for any known country code
        PickerUtils.allCountries.forEach { country ->
            val flag = PickerUtils.getFlags(country.code)
            assertNotNull(flag, "getFlags(${country.code}) returned null")
        }
    }

    @Test
    fun getCountryNameForAllKnownCodes() {
        // Verify getCountryName doesn't throw for any known country code
        PickerUtils.allCountries.forEach { country ->
            val name = PickerUtils.getCountryName(country.code.lowercase())
            assertNotNull(name, "getCountryName(${country.code}) returned null")
        }
    }

    // --- Shared phone code (NANP) ambiguity tests ---

    @Test
    fun extractCountryCodePrefersUSForPlusOne() {
        val (code, phone) = PickerUtils.extractCountryCodeAndPhoneNumber("+12025551234")
        assertEquals("us", code, "+1 should resolve to US, not Antigua or other NANP countries")
        assertEquals("2025551234", phone)
    }

    @Test
    fun extractCountryCodePrefersGBForPlusFortyFour() {
        val (code, phone) = PickerUtils.extractCountryCodeAndPhoneNumber("+447911123456")
        assertEquals("gb", code, "+44 should resolve to GB, not Guernsey/Jersey/IoM")
        assertEquals("7911123456", phone)
    }

    @Test
    fun extractCountryCodePrefersRUForPlusSeven() {
        val (code, phone) = PickerUtils.extractCountryCodeAndPhoneNumber("+79161234567")
        assertEquals("ru", code, "+7 should resolve to Russia, not Kazakhstan")
        assertEquals("9161234567", phone)
    }

    @Test
    fun extractCountryCodePrefersAUForPlusSixtyOne() {
        val (code, phone) = PickerUtils.extractCountryCodeAndPhoneNumber("+61412345678")
        assertEquals("au", code, "+61 should resolve to Australia, not Christmas/Cocos Islands")
        assertEquals("412345678", phone)
    }

    @Test
    fun extractCountryCodePrefersNOForPlusFortySevenIfShared() {
        val (code, phone) = PickerUtils.extractCountryCodeAndPhoneNumber("+4712345678")
        assertEquals("no", code, "+47 should resolve to Norway")
        assertEquals("12345678", phone)
    }

    @Test
    fun extractCountryCodeStillWorksForUniqueCode() {
        // +254 is unique to Kenya, should still work
        val (code, phone) = PickerUtils.extractCountryCodeAndPhoneNumber("+254712345678")
        assertEquals("ke", code)
        assertEquals("712345678", phone)
    }

    @Test
    fun extractCountryCodePrefersLongestMatch() {
        // +49 (Germany) should be preferred over +4 if any code starts with +4
        val (code, _) = PickerUtils.extractCountryCodeAndPhoneNumber("+4915112345678")
        assertEquals("de", code, "Should match +49 (Germany) not +4")
    }
}
