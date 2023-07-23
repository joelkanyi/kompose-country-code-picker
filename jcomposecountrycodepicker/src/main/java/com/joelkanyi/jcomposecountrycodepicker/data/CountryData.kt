package com.joelkanyi.jcomposecountrycodepicker.data

import com.joelkanyi.jcomposecountrycodepicker.R
import java.util.*

data class CountryData(
    private var cCountryCode: String,
    val cCountryPhoneNoCode: String,
    val cCountryName: String,
    val cCountryFlag: Int = R.drawable.tr,
) {
    val countryCode = cCountryCode.lowercase(Locale.getDefault())
}
