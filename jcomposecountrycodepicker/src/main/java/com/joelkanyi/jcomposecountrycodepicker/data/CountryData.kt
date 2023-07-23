package com.joelkanyi.jcomposecountrycodepicker.data

import com.joelkanyi.jcomposecountrycodepicker.R
import java.util.*

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
    val cCountryFlag: Int = R.drawable.tr,
) {
    val countryCode = cCountryCode.lowercase(Locale.getDefault())
}
