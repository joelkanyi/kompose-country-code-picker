package com.joelkanyi.jcomposecountrycodepicker.data.utils

import android.content.Context
import android.telephony.TelephonyManager
import androidx.compose.ui.text.intl.Locale
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
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
    val defaultCode: CountryData = allCountries.first { it.countryCode == defaultCountry }
    return defaultCode.cCountryPhoneNoCode.ifBlank { "+90" }
}

fun isValid(phoneNumberStr: String): Boolean {
    return try {
        val phoneNumber =
            PhoneNumberUtil.getInstance().parse(
                phoneNumberStr.trim(),
                PhoneNumberUtil.REGION_CODE_FOR_NON_GEO_ENTITY,
            )
        PhoneNumberUtil.getInstance().isValidNumber(phoneNumber)
    } catch (e: NumberParseException) {
        e.printStackTrace()
        false
    }
}

fun String.removeSpecialCharacters(): String {
    return this.replace("[^0-9]".toRegex(), "")
}
