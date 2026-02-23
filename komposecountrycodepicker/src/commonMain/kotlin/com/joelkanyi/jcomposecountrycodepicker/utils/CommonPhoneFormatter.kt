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

/**
 * A common phone number formatter that works on all platforms.
 * Uses per-country format patterns to add spaces as the user types.
 *
 * Used by iOS, JS, and WasmJS where libphonenumber is not available.
 */
internal class CommonPhoneFormatter(countryCode: String) : PhoneFormatter {
    private val buffer = StringBuilder()
    private val pattern: List<Int> = formatPatterns[countryCode.lowercase()] ?: defaultPattern

    override fun clear() {
        buffer.clear()
    }

    override fun inputDigit(digit: Char): String {
        buffer.append(digit)
        return formatBuffer()
    }

    override fun inputDigitAndRememberPosition(digit: Char): String {
        buffer.append(digit)
        return formatBuffer()
    }

    private fun formatBuffer(): String {
        val digits = buffer.toString()
        if (digits.isEmpty()) return ""

        val result = StringBuilder()
        var digitIndex = 0

        for (groupSize in pattern) {
            if (digitIndex >= digits.length) break

            if (result.isNotEmpty()) {
                result.append(' ')
            }

            val end = minOf(digitIndex + groupSize, digits.length)
            result.append(digits.substring(digitIndex, end))
            digitIndex = end
        }

        // Append any remaining digits
        if (digitIndex < digits.length) {
            if (result.isNotEmpty()) {
                result.append(' ')
            }
            result.append(digits.substring(digitIndex))
        }

        return result.toString()
    }

    companion object {
        // Default pattern: groups of 3
        private val defaultPattern = listOf(3, 3, 4)

        /**
         * Format patterns define how digits are grouped with spaces.
         * E.g. [3, 3, 4] means "XXX XXX XXXX" (like US numbers).
         *
         * These patterns are for the subscriber number AFTER the country code.
         */
        private val formatPatterns: Map<String, List<Int>> = mapOf(
            // NANP countries (US, Canada, Caribbean, etc.): XXX XXX XXXX
            "us" to listOf(3, 3, 4),
            "ca" to listOf(3, 3, 4),
            "ag" to listOf(3, 3, 4),
            "ai" to listOf(3, 3, 4),
            "as" to listOf(3, 3, 4),
            "bb" to listOf(3, 3, 4),
            "bm" to listOf(3, 3, 4),
            "bs" to listOf(3, 3, 4),
            "dm" to listOf(3, 3, 4),
            "do" to listOf(3, 3, 4),
            "gd" to listOf(3, 3, 4),
            "gu" to listOf(3, 3, 4),
            "jm" to listOf(3, 3, 4),
            "kn" to listOf(3, 3, 4),
            "ky" to listOf(3, 3, 4),
            "lc" to listOf(3, 3, 4),
            "mp" to listOf(3, 3, 4),
            "ms" to listOf(3, 3, 4),
            "pr" to listOf(3, 3, 4),
            "sx" to listOf(3, 3, 4),
            "tc" to listOf(3, 3, 4),
            "tt" to listOf(3, 3, 4),
            "vc" to listOf(3, 3, 4),
            "vg" to listOf(3, 3, 4),
            "vi" to listOf(3, 3, 4),

            // Europe
            "gb" to listOf(4, 3, 4), // UK: XXXX XXX XXXX
            "im" to listOf(4, 3, 4),
            "je" to listOf(4, 3, 4),
            "de" to listOf(3, 4, 4), // Germany: XXX XXXX XXXX
            "fr" to listOf(1, 2, 2, 2, 2), // France: X XX XX XX XX
            "it" to listOf(3, 3, 4), // Italy: XXX XXX XXXX
            "es" to listOf(3, 3, 3), // Spain: XXX XXX XXX
            "pt" to listOf(3, 3, 3), // Portugal: XXX XXX XXX
            "nl" to listOf(2, 3, 4), // Netherlands: XX XXX XXXX
            "be" to listOf(3, 2, 2, 2), // Belgium: XXX XX XX XX
            "at" to listOf(3, 3, 4), // Austria
            "ch" to listOf(2, 3, 2, 2), // Switzerland: XX XXX XX XX
            "se" to listOf(2, 3, 4), // Sweden: XX XXX XXXX
            "no" to listOf(3, 2, 3), // Norway: XXX XX XXX
            "dk" to listOf(2, 2, 2, 2), // Denmark: XX XX XX XX
            "fi" to listOf(2, 3, 4), // Finland
            "pl" to listOf(3, 3, 3), // Poland: XXX XXX XXX
            "cz" to listOf(3, 3, 3), // Czech: XXX XXX XXX
            "sk" to listOf(3, 3, 3), // Slovakia: XXX XXX XXX
            "hu" to listOf(2, 3, 4), // Hungary: XX XXX XXXX
            "ro" to listOf(3, 3, 4), // Romania
            "bg" to listOf(3, 3, 3), // Bulgaria
            "hr" to listOf(2, 3, 4), // Croatia
            "rs" to listOf(2, 3, 4), // Serbia
            "si" to listOf(2, 3, 3), // Slovenia
            "ba" to listOf(2, 3, 3), // Bosnia
            "me" to listOf(2, 3, 4), // Montenegro
            "mk" to listOf(2, 3, 3), // North Macedonia
            "al" to listOf(2, 3, 4), // Albania
            "gr" to listOf(3, 3, 4), // Greece
            "tr" to listOf(3, 3, 2, 2), // Turkey: XXX XXX XX XX
            "ie" to listOf(2, 3, 4), // Ireland
            "is" to listOf(3, 4), // Iceland: XXX XXXX
            "lt" to listOf(3, 2, 3), // Lithuania
            "lv" to listOf(2, 3, 3), // Latvia
            "ee" to listOf(3, 4), // Estonia
            "lu" to listOf(3, 3, 3), // Luxembourg
            "li" to listOf(3, 2, 2), // Liechtenstein

            // Russia / CIS
            "ru" to listOf(3, 3, 2, 2), // Russia: XXX XXX XX XX
            "kz" to listOf(3, 3, 2, 2), // Kazakhstan
            "ua" to listOf(2, 3, 2, 2), // Ukraine: XX XXX XX XX
            "by" to listOf(2, 3, 2, 2), // Belarus
            "md" to listOf(2, 3, 3), // Moldova
            "ge" to listOf(3, 2, 2, 2), // Georgia
            "am" to listOf(2, 3, 3), // Armenia
            "az" to listOf(2, 3, 2, 2), // Azerbaijan

            // Asia
            "cn" to listOf(3, 4, 4), // China: XXX XXXX XXXX
            "jp" to listOf(2, 4, 4), // Japan: XX XXXX XXXX
            "kr" to listOf(2, 4, 4), // South Korea: XX XXXX XXXX
            "in" to listOf(5, 5), // India: XXXXX XXXXX
            "pk" to listOf(3, 3, 4), // Pakistan
            "bd" to listOf(4, 3, 3), // Bangladesh
            "lk" to listOf(2, 3, 4), // Sri Lanka
            "np" to listOf(3, 3, 4), // Nepal
            "th" to listOf(2, 3, 4), // Thailand
            "vn" to listOf(2, 3, 2, 2), // Vietnam
            "my" to listOf(2, 4, 4), // Malaysia
            "sg" to listOf(4, 4), // Singapore: XXXX XXXX
            "id" to listOf(3, 4, 4), // Indonesia
            "ph" to listOf(3, 3, 4), // Philippines
            "tw" to listOf(3, 3, 3), // Taiwan
            "hk" to listOf(4, 4), // Hong Kong: XXXX XXXX
            "kh" to listOf(2, 3, 4), // Cambodia
            "mm" to listOf(3, 3, 4), // Myanmar
            "la" to listOf(2, 3, 4), // Laos

            // Middle East
            "sa" to listOf(2, 3, 4), // Saudi Arabia: XX XXX XXXX
            "ae" to listOf(2, 3, 4), // UAE
            "il" to listOf(2, 3, 4), // Israel
            "jo" to listOf(1, 4, 4), // Jordan
            "lb" to listOf(1, 3, 3), // Lebanon
            "iq" to listOf(3, 3, 4), // Iraq
            "ir" to listOf(3, 3, 4), // Iran
            "kw" to listOf(4, 4), // Kuwait: XXXX XXXX
            "bh" to listOf(4, 4), // Bahrain: XXXX XXXX
            "qa" to listOf(4, 4), // Qatar: XXXX XXXX
            "om" to listOf(4, 4), // Oman: XXXX XXXX
            "ye" to listOf(3, 3, 3), // Yemen
            "sy" to listOf(3, 3, 3), // Syria
            "ps" to listOf(3, 3, 3), // Palestine

            // Africa
            "eg" to listOf(2, 4, 4), // Egypt: XX XXXX XXXX
            "za" to listOf(2, 3, 4), // South Africa: XX XXX XXXX
            "ng" to listOf(3, 3, 4), // Nigeria: XXX XXX XXXX
            "ke" to listOf(3, 3, 3), // Kenya: XXX XXX XXX
            "et" to listOf(2, 3, 4), // Ethiopia
            "tz" to listOf(3, 3, 3), // Tanzania
            "ug" to listOf(3, 3, 3), // Uganda
            "gh" to listOf(2, 3, 4), // Ghana
            "ci" to listOf(2, 2, 2, 2, 2), // Cote d'Ivoire: XX XX XX XX XX
            "cm" to listOf(3, 3, 3), // Cameroon
            "sn" to listOf(2, 3, 2, 2), // Senegal
            "mg" to listOf(2, 2, 3, 2), // Madagascar
            "dz" to listOf(3, 2, 2, 2), // Algeria
            "ma" to listOf(3, 2, 2, 2), // Morocco
            "tn" to listOf(2, 3, 3), // Tunisia
            "ly" to listOf(2, 3, 4), // Libya
            "sd" to listOf(2, 3, 4), // Sudan
            "rw" to listOf(3, 3, 3), // Rwanda
            "mz" to listOf(2, 3, 4), // Mozambique
            "zm" to listOf(2, 3, 4), // Zambia
            "zw" to listOf(2, 3, 4), // Zimbabwe
            "bw" to listOf(2, 3, 3), // Botswana
            "na" to listOf(2, 3, 4), // Namibia
            "mw" to listOf(1, 4, 4), // Malawi
            "ls" to listOf(4, 4), // Lesotho
            "sz" to listOf(4, 4), // Eswatini

            // South America
            "br" to listOf(2, 5, 4), // Brazil: XX XXXXX XXXX
            "ar" to listOf(2, 4, 4), // Argentina
            "co" to listOf(3, 3, 4), // Colombia
            "cl" to listOf(1, 4, 4), // Chile
            "pe" to listOf(3, 3, 3), // Peru
            "ve" to listOf(3, 3, 4), // Venezuela
            "ec" to listOf(2, 3, 4), // Ecuador
            "bo" to listOf(1, 3, 4), // Bolivia
            "py" to listOf(3, 3, 3), // Paraguay
            "uy" to listOf(1, 3, 2, 2), // Uruguay
            "mx" to listOf(2, 4, 4), // Mexico

            // Oceania
            "au" to listOf(3, 3, 3), // Australia: XXX XXX XXX
            "nz" to listOf(2, 3, 4), // New Zealand
            "fj" to listOf(3, 4), // Fiji
        )
    }
}
