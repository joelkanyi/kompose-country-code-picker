package com.joelkanyi.jcomposecountrycodepicker.utils

import android.content.Context
import com.joelkanyi.jcomposecountrycodepicker.data.CountryData
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getCountryName

fun List<CountryData>.searchCountry(key: String, context: Context): MutableList<CountryData> {
    /*val tempList = mutableListOf<CountryData>()
    this.forEach { country ->
        if (
            // context.resources.getString(getCountryName(country.countryCode)).contains(key, ignoreCase = true) ||
            country.countryPhoneCode.contains(key, ignoreCase = true)
            //country.countryCode.contains(key, ignoreCase = true) ||
            //country.cNames.contains(key, ignoreCase = true)
        ) {
            tempList.add(country)
        }
    }
    return tempList*/
    return this.filter { countryData ->
        countryData.countryPhoneCode.contains(key, ignoreCase = true) ||
            context.resources.getString(getCountryName(countryData.countryCode)).contains(key, ignoreCase = true) ||
            countryData.countryCode.contains(key, ignoreCase = true) ||
            countryData.cNames.contains(key, ignoreCase = true)
    }.toMutableList()
}
