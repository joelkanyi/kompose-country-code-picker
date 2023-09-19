package com.joelkanyi.jcomposecountrycodepicker.data.utils

import android.content.Context
import android.telephony.TelephonyManager
import android.util.Log
import androidx.compose.ui.text.intl.Locale
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.joelkanyi.jcomposecountrycodepicker.data.CountryData

/**
 * [getDefaultLangCode] Returns the default language code of the device.
 * [context] The context of the activity or fragment.
 */
fun getDefaultLangCode(context: Context): String {
    return try {
        val localeCode: TelephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val countryCode = localeCode.networkCountryIso
        val defaultLocale = Locale.current.language
        return countryCode ?: defaultLocale
    } catch (e: Exception) {
        Log.e("TAG", "getDefaultLangCode: ${e.message}")
        "US"
    }
}

/**
 * [getDefaultPhoneCode] Returns the default phone code of the device.
 * [context] The context of the activity or fragment.
 */
fun getDefaultPhoneCode(context: Context): String {
    return try {
        val defaultCountry = getDefaultLangCode(context)
        val defaultCode: CountryData? =
            allCountries.firstOrNull { it.countryCode == defaultCountry }
        return defaultCode?.cCountryPhoneNoCode ?: "+1"
    } catch (e: NumberParseException) {
        Log.e("TAG", "getDefaultPhoneCode: ${e.message}")
        "+1"
    }
}

/**
 * [isValid] Returns true if the phone number is valid.
 * [phoneNumberStr] The phone number to be checked.
 */
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

/**
 * [removeSpecialCharacters] Returns the phone number without special characters.
 */
fun String.removeSpecialCharacters(): String {
    return this.replace("[^0-9]".toRegex(), "")
}
