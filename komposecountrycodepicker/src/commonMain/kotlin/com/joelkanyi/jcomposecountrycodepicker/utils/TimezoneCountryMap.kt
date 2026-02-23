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
 * Maps IANA timezone identifiers to ISO 3166-1 alpha-2 country codes.
 *
 * Used as a fallback for JS/WasmJS when navigator.language doesn't
 * contain a region subtag (e.g. "en" instead of "en-US").
 */
internal object TimezoneCountryMap {

    /**
     * Extracts a country code from a locale string like "en-US", "pt-BR", "fr".
     * Returns null if no region subtag is found.
     */
    fun extractRegionFromLocale(locale: String): String? {
        val parts = locale.split("-", "_")
        // A region subtag is typically 2 uppercase letters (second or last part)
        return parts.drop(1).firstOrNull { it.length == 2 && it.all { c -> c.isLetter() } }
            ?.lowercase()
    }

    /**
     * Extracts a country code from a list of locale strings.
     * Returns the first valid region subtag found, or null.
     */
    fun extractRegionFromLocales(locales: List<String>): String? {
        for (locale in locales) {
            val region = extractRegionFromLocale(locale)
            if (region != null) return region
        }
        return null
    }

    /**
     * Maps a timezone string (e.g. "America/New_York") to a country code.
     * Returns null if the timezone is not recognized.
     */
    fun getCountryFromTimezone(timezone: String): String? = timezoneToCountry[timezone]

    private val timezoneToCountry: Map<String, String> = mapOf(
        // Americas
        "America/New_York" to "us",
        "America/Chicago" to "us",
        "America/Denver" to "us",
        "America/Los_Angeles" to "us",
        "America/Anchorage" to "us",
        "America/Phoenix" to "us",
        "America/Detroit" to "us",
        "America/Indiana/Indianapolis" to "us",
        "America/Boise" to "us",
        "America/Juneau" to "us",
        "America/Adak" to "us",
        "Pacific/Honolulu" to "us",
        "America/Toronto" to "ca",
        "America/Vancouver" to "ca",
        "America/Edmonton" to "ca",
        "America/Winnipeg" to "ca",
        "America/Halifax" to "ca",
        "America/St_Johns" to "ca",
        "America/Regina" to "ca",
        "America/Mexico_City" to "mx",
        "America/Cancun" to "mx",
        "America/Tijuana" to "mx",
        "America/Monterrey" to "mx",
        "America/Sao_Paulo" to "br",
        "America/Fortaleza" to "br",
        "America/Manaus" to "br",
        "America/Recife" to "br",
        "America/Bahia" to "br",
        "America/Argentina/Buenos_Aires" to "ar",
        "America/Argentina/Cordoba" to "ar",
        "America/Bogota" to "co",
        "America/Lima" to "pe",
        "America/Santiago" to "cl",
        "America/Caracas" to "ve",
        "America/Guayaquil" to "ec",
        "America/La_Paz" to "bo",
        "America/Asuncion" to "py",
        "America/Montevideo" to "uy",
        "America/Havana" to "cu",
        "America/Jamaica" to "jm",
        "America/Panama" to "pa",
        "America/Puerto_Rico" to "pr",
        "America/Costa_Rica" to "cr",
        "America/Guatemala" to "gt",
        "America/El_Salvador" to "sv",
        "America/Tegucigalpa" to "hn",
        "America/Managua" to "ni",

        // Europe
        "Europe/London" to "gb",
        "Europe/Paris" to "fr",
        "Europe/Berlin" to "de",
        "Europe/Rome" to "it",
        "Europe/Madrid" to "es",
        "Europe/Lisbon" to "pt",
        "Europe/Amsterdam" to "nl",
        "Europe/Brussels" to "be",
        "Europe/Zurich" to "ch",
        "Europe/Vienna" to "at",
        "Europe/Stockholm" to "se",
        "Europe/Oslo" to "no",
        "Europe/Copenhagen" to "dk",
        "Europe/Helsinki" to "fi",
        "Europe/Warsaw" to "pl",
        "Europe/Prague" to "cz",
        "Europe/Budapest" to "hu",
        "Europe/Bucharest" to "ro",
        "Europe/Sofia" to "bg",
        "Europe/Athens" to "gr",
        "Europe/Istanbul" to "tr",
        "Europe/Dublin" to "ie",
        "Europe/Zagreb" to "hr",
        "Europe/Belgrade" to "rs",
        "Europe/Ljubljana" to "si",
        "Europe/Sarajevo" to "ba",
        "Europe/Skopje" to "mk",
        "Europe/Podgorica" to "me",
        "Europe/Tirana" to "al",
        "Europe/Vilnius" to "lt",
        "Europe/Riga" to "lv",
        "Europe/Tallinn" to "ee",
        "Europe/Luxembourg" to "lu",
        "Europe/Bratislava" to "sk",
        "Europe/Kiev" to "ua",
        "Europe/Kyiv" to "ua",
        "Europe/Moscow" to "ru",
        "Europe/Minsk" to "by",
        "Europe/Chisinau" to "md",
        "Europe/Reykjavik" to "is",
        "Europe/Malta" to "mt",
        "Europe/Monaco" to "mc",
        "Europe/Andorra" to "ad",
        "Europe/Vaduz" to "li",
        "Europe/San_Marino" to "sm",

        // Asia
        "Asia/Tokyo" to "jp",
        "Asia/Shanghai" to "cn",
        "Asia/Chongqing" to "cn",
        "Asia/Hong_Kong" to "hk",
        "Asia/Seoul" to "kr",
        "Asia/Taipei" to "tw",
        "Asia/Singapore" to "sg",
        "Asia/Kuala_Lumpur" to "my",
        "Asia/Jakarta" to "id",
        "Asia/Bangkok" to "th",
        "Asia/Ho_Chi_Minh" to "vn",
        "Asia/Saigon" to "vn",
        "Asia/Manila" to "ph",
        "Asia/Kolkata" to "in",
        "Asia/Calcutta" to "in",
        "Asia/Karachi" to "pk",
        "Asia/Dhaka" to "bd",
        "Asia/Colombo" to "lk",
        "Asia/Kathmandu" to "np",
        "Asia/Phnom_Penh" to "kh",
        "Asia/Yangon" to "mm",
        "Asia/Rangoon" to "mm",
        "Asia/Macau" to "mo",
        "Asia/Ulaanbaatar" to "mn",
        "Asia/Tbilisi" to "ge",
        "Asia/Yerevan" to "am",
        "Asia/Baku" to "az",
        "Asia/Bishkek" to "kg",
        "Asia/Almaty" to "kz",
        "Asia/Tashkent" to "uz",
        "Asia/Dushanbe" to "tj",
        "Asia/Ashgabat" to "tm",
        "Asia/Kabul" to "af",
        "Asia/Tehran" to "ir",
        "Asia/Baghdad" to "iq",
        "Asia/Riyadh" to "sa",
        "Asia/Dubai" to "ae",
        "Asia/Muscat" to "om",
        "Asia/Qatar" to "qa",
        "Asia/Bahrain" to "bh",
        "Asia/Kuwait" to "kw",
        "Asia/Jerusalem" to "il",
        "Asia/Tel_Aviv" to "il",
        "Asia/Amman" to "jo",
        "Asia/Beirut" to "lb",
        "Asia/Damascus" to "sy",
        "Asia/Vientiane" to "la",

        // Africa
        "Africa/Cairo" to "eg",
        "Africa/Johannesburg" to "za",
        "Africa/Lagos" to "ng",
        "Africa/Nairobi" to "ke",
        "Africa/Addis_Ababa" to "et",
        "Africa/Dar_es_Salaam" to "tz",
        "Africa/Kampala" to "ug",
        "Africa/Accra" to "gh",
        "Africa/Casablanca" to "ma",
        "Africa/Algiers" to "dz",
        "Africa/Tunis" to "tn",
        "Africa/Tripoli" to "ly",
        "Africa/Khartoum" to "sd",
        "Africa/Kinshasa" to "cd",
        "Africa/Luanda" to "ao",
        "Africa/Maputo" to "mz",
        "Africa/Harare" to "zw",
        "Africa/Lusaka" to "zm",
        "Africa/Gaborone" to "bw",
        "Africa/Windhoek" to "na",
        "Africa/Dakar" to "sn",
        "Africa/Abidjan" to "ci",
        "Africa/Douala" to "cm",
        "Africa/Kigali" to "rw",

        // Oceania
        "Australia/Sydney" to "au",
        "Australia/Melbourne" to "au",
        "Australia/Brisbane" to "au",
        "Australia/Perth" to "au",
        "Australia/Adelaide" to "au",
        "Australia/Hobart" to "au",
        "Australia/Darwin" to "au",
        "Pacific/Auckland" to "nz",
        "Pacific/Fiji" to "fj",
    )
}
