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
        val testData = "+254712345678_"

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
}
