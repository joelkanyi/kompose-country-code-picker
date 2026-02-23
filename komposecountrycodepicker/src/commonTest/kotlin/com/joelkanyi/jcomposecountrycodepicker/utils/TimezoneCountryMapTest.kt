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
import kotlin.test.assertNull

class TimezoneCountryMapTest {

    // --- extractRegionFromLocale ---

    @Test
    fun extractsRegionFromEnUS() {
        assertEquals("us", TimezoneCountryMap.extractRegionFromLocale("en-US"))
    }

    @Test
    fun extractsRegionFromPtBR() {
        assertEquals("br", TimezoneCountryMap.extractRegionFromLocale("pt-BR"))
    }

    @Test
    fun extractsRegionFromZhHansCN() {
        // zh-Hans-CN has 3 parts, region is "CN"
        assertEquals("cn", TimezoneCountryMap.extractRegionFromLocale("zh-Hans-CN"))
    }

    @Test
    fun returnsNullForLanguageOnly() {
        assertNull(TimezoneCountryMap.extractRegionFromLocale("en"))
    }

    @Test
    fun returnsNullForEmptyString() {
        assertNull(TimezoneCountryMap.extractRegionFromLocale(""))
    }

    @Test
    fun handlesUnderscoreSeparator() {
        assertEquals("gb", TimezoneCountryMap.extractRegionFromLocale("en_GB"))
    }

    // --- extractRegionFromLocales ---

    @Test
    fun extractsRegionFromFirstValidLocale() {
        val locales = listOf("en", "en-US", "fr-FR")
        assertEquals("us", TimezoneCountryMap.extractRegionFromLocales(locales))
    }

    @Test
    fun returnsNullIfNoRegionInAnyLocale() {
        val locales = listOf("en", "fr", "de")
        assertNull(TimezoneCountryMap.extractRegionFromLocales(locales))
    }

    @Test
    fun handlesEmptyList() {
        assertNull(TimezoneCountryMap.extractRegionFromLocales(emptyList()))
    }

    // --- getCountryFromTimezone ---

    @Test
    fun mapsNewYorkToUS() {
        assertEquals("us", TimezoneCountryMap.getCountryFromTimezone("America/New_York"))
    }

    @Test
    fun mapsLondonToGB() {
        assertEquals("gb", TimezoneCountryMap.getCountryFromTimezone("Europe/London"))
    }

    @Test
    fun mapsTokyoToJP() {
        assertEquals("jp", TimezoneCountryMap.getCountryFromTimezone("Asia/Tokyo"))
    }

    @Test
    fun mapsNairobiToKE() {
        assertEquals("ke", TimezoneCountryMap.getCountryFromTimezone("Africa/Nairobi"))
    }

    @Test
    fun mapsSydneyToAU() {
        assertEquals("au", TimezoneCountryMap.getCountryFromTimezone("Australia/Sydney"))
    }

    @Test
    fun returnsNullForUnknownTimezone() {
        assertNull(TimezoneCountryMap.getCountryFromTimezone("Unknown/Timezone"))
    }

    @Test
    fun mapsSaoPauloToBR() {
        assertEquals("br", TimezoneCountryMap.getCountryFromTimezone("America/Sao_Paulo"))
    }

    @Test
    fun mapsKolkataToIN() {
        assertEquals("in", TimezoneCountryMap.getCountryFromTimezone("Asia/Kolkata"))
    }

    @Test
    fun mapsBerlinToDE() {
        assertEquals("de", TimezoneCountryMap.getCountryFromTimezone("Europe/Berlin"))
    }

    @Test
    fun mapsLagosToNG() {
        assertEquals("ng", TimezoneCountryMap.getCountryFromTimezone("Africa/Lagos"))
    }
}
