package com.joelkanyi.jcomposecountrycodepicker.data.utils

import android.content.Context
import android.telephony.TelephonyManager
import androidx.compose.ui.text.intl.Locale
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import com.joelkanyi.jcomposecountrycodepicker.data.CountryData

fun getDefaultLangCode(context: Context): String {
    val localeCode: TelephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val countryCode = localeCode.networkCountryIso
    val defaultLocale = Locale.current.language
    return countryCode.ifBlank { defaultLocale }
}

fun getDefaultPhoneCode(context: Context): String {
    val defaultCountry = getDefaultLangCode(context)
    val defaultCode: CountryData = getLibCountries.first { it.countryCode == defaultCountry }
    return defaultCode.countryPhoneCode.ifBlank { "+90" }
}

fun checkPhoneNumber(phone: String, fullPhoneNumber: String, countryCode: String): Boolean {
    val number: Phonenumber.PhoneNumber?
    if (phone.length > 6) {
        return try {
            number = PhoneNumberUtil.getInstance().parse(
                fullPhoneNumber,
                Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name
            )
            PhoneNumberUtil.getInstance().isValidNumberForRegion(number, countryCode.uppercase())
        } catch (ex: Exception) {
            false
        }
    }
    return false
}
