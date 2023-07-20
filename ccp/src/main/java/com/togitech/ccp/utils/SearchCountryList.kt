package com.togitech.ccp.utils

import android.content.Context
import com.togitech.ccp.data.CountryData
import com.togitech.ccp.data.utils.getCountryName

fun List<CountryData>.searchCountry(key: String,context: Context): MutableList<CountryData> {
    val tempList = mutableListOf<CountryData>()
    this.forEach {
        if (context.resources.getString(getCountryName(it.countryCode)).lowercase().contains(key.lowercase())) {
            tempList.add(it)
        }
    }
    return tempList
}