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

import com.google.common.truth.Truth.assertThat
import com.joelkanyi.jcomposecountrycodepicker.data.Country
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.getCountry
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.removeSpecialCharacters
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.searchForAnItem
import org.junit.Test

class PickerUtilsTest {
    @Test
    fun getCountry() {
        // Given
        val testData = "kE"

        // When
        val result = testData.getCountry()

        // Then
        assertThat(result).isEqualTo(Country("ke", "+254", "Kenya", 2131230853))
    }

    @Test
    fun getCountry_default() {
        // Given
        val testData = "does_not_exist"

        // When
        val result = testData.getCountry()

        // Then
        assertThat(result).isEqualTo(Country("us", "+1", "United States", 2131230986))
    }

    @Test
    fun testIsValid() {
        // Given
        val testData = "+254712345678"

        // When
        val result = PickerUtils.isValid(testData)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun testRemoveSpecialCharacters() {
        // Given
        val testData = "+-,`˜@/.!#%^&*()254712345678_+$"

        // When
        val result = testData.removeSpecialCharacters()

        // Then
        assertThat(result).isEqualTo("254712345678")
    }

    @Test
    fun testSearchForAnItem() {
        // Given
        val testData = listOf(
            Country("ke", "+254", "Kenya", 0),
            Country("ug", "+256", "Uganda", 0),
        )
        val correctResult = listOf(Country("ke", "+254", "Kenya", 0))

        // When
        val result = testData.searchForAnItem("kEnYa")

        // Then
        assertThat(result).isEqualTo(correctResult)
    }

    @Test
    fun testReturnCountryCodeAndPhoneNumberCorrectly() {
        // Given
        val wholePhoneNumber = "+254712345678"
        val expectedCountryCode = "ke"
        val expectedPhoneNo = "712345678"

        // When
        val (countryCode, phoneNo) = PickerUtils.extractCountryCodeAndPhoneNumber(wholePhoneNumber)

        // Then
        assertThat(countryCode).isEqualTo(expectedCountryCode)
        assertThat(phoneNo).isEqualTo(expectedPhoneNo)
    }

    @Test
    fun testReturnCorrectPhoneNumberWhenCountryCodeIsNotPresent() {
        // Given
        val wholePhoneNumber = "712345678"

        // When
        val phoneNumber = PickerUtils.extractCountryCodeAndPhoneNumber(wholePhoneNumber).second

        // Then
        assertThat(phoneNumber).isEqualTo(wholePhoneNumber)
    }

    @Test
    fun testReturnsLimitedCountries() {
        // Given
        val limitedCountriesString = listOf(
            "UG",
            "255",
            "+260",
            "Kenya",
            "ng",
        )

        // When
        val result = PickerUtils.getLimitedCountries(limitedCountriesString).map { it.name }

        // Then
        assertThat(result).hasSize(5)
    }

    @Test
    fun testSortCountriesWithPriorityCountries() {
        // Given
        val priorityCountries = listOf("ug", "ke", "tz")
        val countries = PickerUtils.allCountries

        // When
        val sortedCountries = PickerUtils.sortCountriesWithPriority(countries, priorityCountries)

        // Then
        assertThat(sortedCountries.map { it.phoneNoCode }.take(3)).isEqualTo(
            listOf("+256", "+254", "+255"),
        )
    }
}
