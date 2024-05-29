/*
 * Copyright 2023 Joel Kanyi.
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
package com.joelkanyi.jcomposecountrycodepicker.data

import com.joelkanyi.jcomposecountrycodepicker.R

/**
 * [CountryData] is a data class that holds the data of a country.
 * [cCountryCode] The code of the country.
 * [cCountryPhoneNoCode] The phone number code of the country.
 * [cCountryName] The name of the country.
 * [cCountryFlag] The flag of the country.
 * [countryCode] The code of the country in lowercase.
 */
data class CountryData(
    private var cCountryCode: String,
    val cCountryPhoneNoCode: String,
    val cCountryName: String,
    val cCountryFlag: Int = R.drawable.ke,
) {
    var countryCode = cCountryCode.lowercase()
}
