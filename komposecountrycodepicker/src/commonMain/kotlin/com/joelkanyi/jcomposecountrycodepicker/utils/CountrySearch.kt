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

internal class SearchableCountry(
    val country: Country,
    val originalNameNormalized: String,
    val localizedNameNormalized: String?,
)

internal class CountrySearchIndex internal constructor(
    private val entries: List<SearchableCountry>,
) {
    fun search(searchStr: String): List<Country> {
        val normalizedSearch = searchStr.unaccent()
        if (normalizedSearch.isEmpty()) return entries.map { it.country }

        return entries.filter { searchableCountry ->
            searchableCountry.localizedNameNormalized?.contains(
                normalizedSearch,
                ignoreCase = true,
            ) == true ||
                searchableCountry.originalNameNormalized.contains(
                    normalizedSearch,
                    ignoreCase = true,
                ) ||
                searchableCountry.country.phoneNoCode.contains(
                    normalizedSearch,
                    ignoreCase = true,
                ) ||
                searchableCountry.country.code.contains(
                    normalizedSearch,
                    ignoreCase = true,
                )
        }.map { it.country }
    }
}

internal fun List<Country>.buildCountrySearchIndex(
    localizedNamesByCode: Map<String, String> = emptyMap(),
): CountrySearchIndex {
    val searchableCountries = map { country ->
        SearchableCountry(
            country = country,
            originalNameNormalized = country.name.unaccent(),
            localizedNameNormalized = localizedNamesByCode[country.code.lowercase()]?.unaccent(),
        )
    }
    return CountrySearchIndex(searchableCountries)
}

internal fun List<Country>.searchCountries(searchStr: String): List<Country> =
    buildCountrySearchIndex().search(searchStr)
