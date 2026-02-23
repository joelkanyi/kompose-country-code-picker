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
 * Per-country phone number validation rules.
 * Maps phone code (without "+") to a pair of (minDigits, maxDigits) for the
 * subscriber number (digits AFTER the country code).
 *
 * Used by platforms that don't have libphonenumber (iOS, JS, WasmJS).
 */
internal object PhoneValidation {

    /**
     * Maps country code (e.g. "ke", "us") to the expected total digit length
     * range of a full international number (country code digits + subscriber digits).
     * E.g. Kenya: +254 (3 digits) + 9 subscriber digits = 12 total.
     */
    private val countryPhoneLengths: Map<String, IntRange> = mapOf(
        "ad" to (8..9), // Andorra: +376 XXXXXX to XXXXXXX
        "ae" to (11..12), // UAE: +971 XX XXXXXXX
        "af" to (11..11), // Afghanistan: +93 XXXXXXXXX
        "ag" to (11..11), // Antigua: +1 XXX XXXXXXX
        "ai" to (11..11), // Anguilla: +1 XXX XXXXXXX
        "al" to (11..12), // Albania: +355 XXXXXXXX to XXXXXXXXX
        "am" to (11..11), // Armenia: +374 XXXXXXXX
        "ao" to (12..12), // Angola: +244 XXXXXXXXX
        "aq" to (12..12), // Antarctica: +672 XXXXXXXXX
        "ar" to (12..13), // Argentina: +54 XXXXXXXXXX to XXXXXXXXXXX
        "as" to (11..11), // American Samoa: +1 XXX XXXXXXX
        "at" to (11..13), // Austria: +43 XXXXXXXXX to XXXXXXXXXXX
        "au" to (11..11), // Australia: +61 XXXXXXXXX
        "aw" to (10..10), // Aruba: +297 XXXXXXX
        "ax" to (10..12), // Aland Islands: +358 XXXXXXX to XXXXXXXXX
        "az" to (12..12), // Azerbaijan: +994 XXXXXXXXX
        "ba" to (11..12), // Bosnia: +387 XXXXXXXX to XXXXXXXXX
        "bb" to (11..11), // Barbados: +1 XXX XXXXXXX
        "bd" to (12..13), // Bangladesh: +880 XXXXXXXXX to XXXXXXXXXX
        "be" to (11..12), // Belgium: +32 XXXXXXXXX to XXXXXXXXXX
        "bf" to (11..11), // Burkina Faso: +226 XXXXXXXX
        "bg" to (11..12), // Bulgaria: +359 XXXXXXXX to XXXXXXXXX
        "bh" to (11..11), // Bahrain: +973 XXXXXXXX
        "bi" to (11..11), // Burundi: +257 XXXXXXXX
        "bj" to (11..11), // Benin: +229 XXXXXXXX
        "bl" to (12..12), // Saint Barthelemy: +590 XXXXXXXXX
        "bm" to (11..11), // Bermuda: +1 XXX XXXXXXX
        "bn" to (10..10), // Brunei: +673 XXXXXXX
        "bo" to (11..11), // Bolivia: +591 XXXXXXXX
        "br" to (13..13), // Brazil: +55 XXXXXXXXXXX
        "bs" to (11..11), // Bahamas: +1 XXX XXXXXXX
        "bt" to (11..11), // Bhutan: +975 XXXXXXXX
        "bw" to (11..11), // Botswana: +267 XXXXXXXX
        "by" to (12..12), // Belarus: +375 XXXXXXXXX
        "bz" to (10..10), // Belize: +501 XXXXXXX
        "ca" to (11..11), // Canada: +1 XXX XXXXXXX
        "cc" to (11..11), // Cocos Islands: +61 XXXXXXXXX
        "cd" to (12..12), // Congo DR: +243 XXXXXXXXX
        "cf" to (11..11), // Central African: +236 XXXXXXXX
        "cg" to (12..12), // Congo: +242 XXXXXXXXX
        "ch" to (11..12), // Switzerland: +41 XXXXXXXXX to XXXXXXXXXX
        "ci" to (13..13), // Cote d'Ivoire: +225 XXXXXXXXXX
        "ck" to (8..8), // Cook Islands: +682 XXXXX
        "cl" to (11..11), // Chile: +56 XXXXXXXXX
        "cm" to (12..12), // Cameroon: +237 XXXXXXXXX
        "cn" to (13..13), // China: +86 XXXXXXXXXXX
        "co" to (12..12), // Colombia: +57 XXXXXXXXXX
        "cr" to (11..11), // Costa Rica: +506 XXXXXXXX
        "cu" to (11..12), // Cuba: +53 XXXXXXXX to XXXXXXXXX
        "cv" to (10..10), // Cape Verde: +238 XXXXXXX
        "cw" to (10..11), // Curacao: +599 XXXXXXX to XXXXXXXX
        "cx" to (11..11), // Christmas Island: +61 XXXXXXXXX
        "cy" to (11..11), // Cyprus: +357 XXXXXXXX
        "cz" to (12..12), // Czech Republic: +420 XXXXXXXXX
        "de" to (11..14), // Germany: +49 XXXXXXXXX to XXXXXXXXXXXX
        "dj" to (11..11), // Djibouti: +253 XXXXXXXX
        "dk" to (10..10), // Denmark: +45 XXXXXXXX
        "dm" to (11..11), // Dominica: +1 XXX XXXXXXX
        "do" to (11..11), // Dominican Republic: +1 XXX XXXXXXX
        "dz" to (12..12), // Algeria: +213 XXXXXXXXX
        "ec" to (12..12), // Ecuador: +593 XXXXXXXXX
        "ee" to (11..12), // Estonia: +372 XXXXXXXX to XXXXXXXXX
        "eg" to (12..13), // Egypt: +20 XXXXXXXXXX to XXXXXXXXXXX
        "er" to (10..10), // Eritrea: +291 XXXXXXX
        "es" to (11..11), // Spain: +34 XXXXXXXXX
        "et" to (12..12), // Ethiopia: +251 XXXXXXXXX
        "fi" to (10..12), // Finland: +358 XXXXXXX to XXXXXXXXX
        "fj" to (10..10), // Fiji: +679 XXXXXXX
        "fk" to (8..8), // Falkland Islands: +500 XXXXX
        "fm" to (10..10), // Micronesia: +691 XXXXXXX
        "fo" to (9..9), // Faroe Islands: +298 XXXXXX
        "fr" to (11..11), // France: +33 XXXXXXXXX
        "ga" to (10..11), // Gabon: +241 XXXXXXX to XXXXXXXX
        "gb" to (12..12), // United Kingdom: +44 XXXXXXXXXX
        "gd" to (11..11), // Grenada: +1 XXX XXXXXXX
        "ge" to (12..12), // Georgia: +995 XXXXXXXXX
        "gf" to (12..12), // French Guyana: +594 XXXXXXXXX
        "gh" to (12..12), // Ghana: +233 XXXXXXXXX
        "gi" to (11..11), // Gibraltar: +350 XXXXXXXX
        "gl" to (9..9), // Greenland: +299 XXXXXX
        "gm" to (10..10), // Gambia: +220 XXXXXXX
        "gn" to (12..12), // Guinea: +224 XXXXXXXXX
        "gp" to (12..12), // Guadeloupe: +590 XXXXXXXXX
        "gq" to (12..12), // Equatorial Guinea: +240 XXXXXXXXX
        "gr" to (12..12), // Greece: +30 XXXXXXXXXX
        "gt" to (11..11), // Guatemala: +502 XXXXXXXX
        "gu" to (11..11), // Guam: +1 XXX XXXXXXX
        "gw" to (10..10), // Guinea-Bissau: +245 XXXXXXX
        "gy" to (10..10), // Guyana: +592 XXXXXXX
        "hk" to (11..12), // Hong Kong: +852 XXXXXXXX to XXXXXXXXX
        "hn" to (11..11), // Honduras: +504 XXXXXXXX
        "hr" to (11..12), // Croatia: +385 XXXXXXXX to XXXXXXXXX
        "ht" to (11..11), // Haiti: +509 XXXXXXXX
        "hu" to (11..12), // Hungary: +36 XXXXXXXXX to XXXXXXXXXX
        "id" to (12..14), // Indonesia: +62 XXXXXXXXXX to XXXXXXXXXXXX
        "ie" to (11..12), // Ireland: +353 XXXXXXXX to XXXXXXXXX
        "il" to (12..12), // Israel: +972 XXXXXXXXX
        "im" to (12..12), // Isle of Man: +44 XXXXXXXXXX
        "in" to (12..12), // India: +91 XXXXXXXXXX
        "io" to (10..10), // British Indian Ocean: +246 XXXXXXX
        "iq" to (12..13), // Iraq: +964 XXXXXXXXX to XXXXXXXXXX
        "ir" to (12..13), // Iran: +98 XXXXXXXXXX to XXXXXXXXXXX
        "is" to (10..10), // Iceland: +354 XXXXXXX
        "it" to (12..13), // Italy: +39 XXXXXXXXXX to XXXXXXXXXXX
        "je" to (12..12), // Jersey: +44 XXXXXXXXXX
        "jm" to (11..11), // Jamaica: +1 XXX XXXXXXX
        "jo" to (12..12), // Jordan: +962 XXXXXXXXX
        "jp" to (12..13), // Japan: +81 XXXXXXXXXX to XXXXXXXXXXX
        "ke" to (12..12), // Kenya: +254 XXXXXXXXX
        "kg" to (12..12), // Kyrgyzstan: +996 XXXXXXXXX
        "kh" to (11..12), // Cambodia: +855 XXXXXXXX to XXXXXXXXX
        "ki" to (11..11), // Kiribati: +686 XXXXXXXX
        "km" to (10..10), // Comoros: +269 XXXXXXX
        "kn" to (11..11), // Saint Kitts: +1 XXX XXXXXXX
        "kp" to (13..13), // North Korea: +850 XXXXXXXXXX
        "kr" to (12..13), // South Korea: +82 XXXXXXXXXX to XXXXXXXXXXX
        "kw" to (11..11), // Kuwait: +965 XXXXXXXX
        "ky" to (11..11), // Cayman Islands: +1 XXX XXXXXXX
        "kz" to (11..11), // Kazakhstan: +7 XXXXXXXXXX
        "la" to (12..12), // Laos: +856 XXXXXXXXX
        "lb" to (11..12), // Lebanon: +961 XXXXXXXX to XXXXXXXXX
        "lc" to (11..11), // Saint Lucia: +1 XXX XXXXXXX
        "li" to (11..11), // Liechtenstein: +423 XXXXXXXX
        "lk" to (11..11), // Sri Lanka: +94 XXXXXXXXX
        "lr" to (11..12), // Liberia: +231 XXXXXXXX to XXXXXXXXX
        "ls" to (11..11), // Lesotho: +266 XXXXXXXX
        "lt" to (11..11), // Lithuania: +370 XXXXXXXX
        "lu" to (11..12), // Luxembourg: +352 XXXXXXXXX to XXXXXXXXXX
        "lv" to (11..11), // Latvia: +371 XXXXXXXX
        "ly" to (12..12), // Libya: +218 XXXXXXXXX
        "ma" to (12..12), // Morocco: +212 XXXXXXXXX
        "mc" to (11..12), // Monaco: +377 XXXXXXXX to XXXXXXXXX
        "md" to (11..11), // Moldova: +373 XXXXXXXX
        "me" to (11..12), // Montenegro: +382 XXXXXXXX to XXXXXXXXX
        "mf" to (12..12), // Saint Martin: +590 XXXXXXXXX
        "mg" to (12..12), // Madagascar: +261 XXXXXXXXX
        "mh" to (10..10), // Marshall Islands: +692 XXXXXXX
        "mk" to (11..11), // North Macedonia: +389 XXXXXXXX
        "ml" to (11..11), // Mali: +223 XXXXXXXX
        "mm" to (11..13), // Myanmar: +95 XXXXXXXXX to XXXXXXXXXXX
        "mn" to (11..12), // Mongolia: +976 XXXXXXXX to XXXXXXXXX
        "mo" to (11..11), // Macau: +853 XXXXXXXX
        "mp" to (11..11), // Northern Mariana: +1 XXX XXXXXXX
        "mq" to (12..12), // Martinique: +596 XXXXXXXXX
        "mr" to (11..11), // Mauritania: +222 XXXXXXXX
        "ms" to (11..11), // Montserrat: +1 XXX XXXXXXX
        "mt" to (11..11), // Malta: +356 XXXXXXXX
        "mu" to (11..12), // Mauritius: +230 XXXXXXXX to XXXXXXXXX
        "mv" to (10..10), // Maldives: +960 XXXXXXX
        "mw" to (12..12), // Malawi: +265 XXXXXXXXX
        "mx" to (12..12), // Mexico: +52 XXXXXXXXXX
        "my" to (12..13), // Malaysia: +60 XXXXXXXXXX to XXXXXXXXXXX
        "mz" to (12..12), // Mozambique: +258 XXXXXXXXX
        "na" to (12..12), // Namibia: +264 XXXXXXXXX
        "nc" to (9..9), // New Caledonia: +687 XXXXXX
        "ne" to (11..11), // Niger: +227 XXXXXXXX
        "nf" to (9..9), // Norfolk Island: +672 XXXXXX
        "ng" to (13..13), // Nigeria: +234 XXXXXXXXXX
        "ni" to (11..11), // Nicaragua: +505 XXXXXXXX
        "nl" to (11..11), // Netherlands: +31 XXXXXXXXX
        "no" to (10..10), // Norway: +47 XXXXXXXX
        "np" to (13..13), // Nepal: +977 XXXXXXXXXX
        "nr" to (10..10), // Nauru: +674 XXXXXXX
        "nu" to (7..7), // Niue: +683 XXXX
        "nz" to (11..12), // New Zealand: +64 XXXXXXXXX to XXXXXXXXXX
        "om" to (11..12), // Oman: +968 XXXXXXXX to XXXXXXXXX
        "pa" to (11..12), // Panama: +507 XXXXXXXX to XXXXXXXXX
        "pe" to (11..12), // Peru: +51 XXXXXXXXX to XXXXXXXXXX
        "pf" to (9..9), // French Polynesia: +689 XXXXXX
        "pg" to (11..11), // Papua New Guinea: +675 XXXXXXXX
        "ph" to (12..12), // Philippines: +63 XXXXXXXXXX
        "pk" to (12..12), // Pakistan: +92 XXXXXXXXXX
        "pl" to (11..11), // Poland: +48 XXXXXXXXX
        "pm" to (9..9), // Saint Pierre: +508 XXXXXX
        "pn" to (10..10), // Pitcairn: +870 XXXXXXX
        "pr" to (11..11), // Puerto Rico: +1 XXX XXXXXXX
        "ps" to (12..12), // Palestine: +970 XXXXXXXXX
        "pt" to (12..12), // Portugal: +351 XXXXXXXXX
        "pw" to (10..10), // Palau: +680 XXXXXXX
        "py" to (12..12), // Paraguay: +595 XXXXXXXXX
        "qa" to (11..11), // Qatar: +974 XXXXXXXX
        "re" to (12..12), // Reunion: +262 XXXXXXXXX
        "ro" to (12..12), // Romania: +40 XXXXXXXXXX
        "rs" to (12..13), // Serbia: +381 XXXXXXXXX to XXXXXXXXXX
        "ru" to (11..11), // Russia: +7 XXXXXXXXXX
        "rw" to (12..12), // Rwanda: +250 XXXXXXXXX
        "sa" to (12..12), // Saudi Arabia: +966 XXXXXXXXX
        "sb" to (10..10), // Solomon Islands: +677 XXXXXXX
        "sc" to (10..10), // Seychelles: +248 XXXXXXX
        "sd" to (12..12), // Sudan: +249 XXXXXXXXX
        "se" to (11..13), // Sweden: +46 XXXXXXXXX to XXXXXXXXXXX
        "sg" to (11..11), // Singapore: +65 XXXXXXXXX
        "sh" to (7..7), // Saint Helena: +290 XXXX
        "si" to (11..11), // Slovenia: +386 XXXXXXXX
        "sk" to (12..12), // Slovakia: +421 XXXXXXXXX
        "sl" to (11..11), // Sierra Leone: +232 XXXXXXXX
        "sm" to (12..13), // San Marino: +378 XXXXXXXXXX to XXXXXXXXXXX
        "sn" to (12..12), // Senegal: +221 XXXXXXXXX
        "so" to (11..12), // Somalia: +252 XXXXXXXX to XXXXXXXXX
        "sr" to (10..10), // Suriname: +597 XXXXXXX
        "ss" to (12..12), // South Sudan: +211 XXXXXXXXX
        "st" to (10..10), // Sao Tome: +239 XXXXXXX
        "sv" to (11..11), // El Salvador: +503 XXXXXXXX
        "sx" to (11..11), // Sint Maarten: +1 XXX XXXXXXX
        "sy" to (12..12), // Syria: +963 XXXXXXXXX
        "sz" to (11..11), // Eswatini: +268 XXXXXXXX
        "tc" to (11..11), // Turks and Caicos: +1 XXX XXXXXXX
        "td" to (11..11), // Chad: +235 XXXXXXXX
        "tg" to (11..11), // Togo: +228 XXXXXXXX
        "th" to (11..11), // Thailand: +66 XXXXXXXXX
        "tj" to (12..12), // Tajikistan: +992 XXXXXXXXX
        "tk" to (7..7), // Tokelau: +690 XXXX
        "tl" to (11..12), // Timor-Leste: +670 XXXXXXXX to XXXXXXXXX
        "tm" to (11..11), // Turkmenistan: +993 XXXXXXXX
        "tn" to (11..11), // Tunisia: +216 XXXXXXXX
        "to" to (8..8), // Tonga: +676 XXXXX
        "tr" to (12..12), // Turkey: +90 XXXXXXXXXX
        "tt" to (11..11), // Trinidad and Tobago: +1 XXX XXXXXXX
        "tv" to (9..9), // Tuvalu: +688 XXXXXX
        "tw" to (12..13), // Taiwan: +886 XXXXXXXXX to XXXXXXXXXX
        "tz" to (12..12), // Tanzania: +255 XXXXXXXXX
        "ua" to (12..12), // Ukraine: +380 XXXXXXXXX
        "ug" to (12..12), // Uganda: +256 XXXXXXXXX
        "us" to (11..11), // United States: +1 XXX XXXXXXX
        "uy" to (11..12), // Uruguay: +598 XXXXXXXXX to XXXXXXXXXX
        "uz" to (12..12), // Uzbekistan: +998 XXXXXXXXX
        "va" to (12..13), // Vatican: +379 XXXXXXXXXX to XXXXXXXXXXX
        "vc" to (11..11), // Saint Vincent: +1 XXX XXXXXXX
        "ve" to (12..12), // Venezuela: +58 XXXXXXXXXX
        "vg" to (11..11), // British Virgin Islands: +1 XXX XXXXXXX
        "vi" to (11..11), // US Virgin Islands: +1 XXX XXXXXXX
        "vn" to (12..12), // Vietnam: +84 XXXXXXXXXX
        "vu" to (10..10), // Vanuatu: +678 XXXXXXX
        "wf" to (9..9), // Wallis and Futuna: +681 XXXXXX
        "ws" to (10..12), // Samoa: +685 XXXXXXX to XXXXXXXXX
        "xk" to (12..12), // Kosovo: +383 XXXXXXXXX
        "ye" to (12..12), // Yemen: +967 XXXXXXXXX
        "yt" to (12..12), // Mayotte: +262 XXXXXXXXX
        "za" to (11..11), // South Africa: +27 XXXXXXXXX
        "zm" to (12..12), // Zambia: +260 XXXXXXXXX
        "zw" to (12..12), // Zimbabwe: +263 XXXXXXXXX
    )

    /**
     * Validates a full international phone number (e.g. "+254712345678")
     * by checking that the digit count matches the expected range for the country.
     *
     * Falls back to a generic 7-15 digit check if the country is unknown.
     */
    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val trimmed = phoneNumber.trim()
        if (!trimmed.startsWith("+")) return false

        val digits = trimmed.filter { it.isDigit() }
        if (digits.isEmpty()) return false

        // Try to match the phone number against known country codes
        val countries = PickerUtils.allCountries
        val matchedCountry = countries.find { trimmed.startsWith(it.phoneNoCode) }

        if (matchedCountry != null) {
            val range = countryPhoneLengths[matchedCountry.code.lowercase()]
            if (range != null) {
                return digits.length in range
            }
        }

        // Fallback: generic ITU-T E.164 validation (7-15 digits)
        return digits.length in 7..15
    }
}
