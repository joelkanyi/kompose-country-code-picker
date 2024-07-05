package com.joelkanyi.jcomposecountrycodepicker.fake

import com.joelkanyi.jcomposecountrycodepicker.annotation.RestrictedApi
import com.joelkanyi.jcomposecountrycodepicker.component.CountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.data.Country

class FakeCountryCodePicker(
    phone: String,
    code: String,
    countries: List<Country>,
) : CountryCodePicker {
    override var phoneNumber: String = phone
    override var countryCode: String = code
    override var countryList: List<Country> = countries
    override val showCountryCode: Boolean
        get() {
            TODO()
        }
    override val showCountryFlag: Boolean
        get() {
            TODO()
        }

    override fun getCountryName(): String {
        return countryList.first().name
    }

    override fun getCountryPhoneCode(): String {
        return countryList.first().phoneNoCode
    }

    override fun getCountryPhoneCodeWithoutPrefix(): String {
        return countryList.first().phoneNoCode.substring(1)
    }

    override fun getPhoneNumberWithoutPrefix(): String {
        return phoneNumber
    }

    override fun getFullPhoneNumberWithoutPrefix(): String {
        val phoneCode = getCountryPhoneCodeWithoutPrefix()
        return "$phoneCode$phoneNumber"
    }

    override fun getFullPhoneNumber(): String {
        return "${getCountryPhoneCode()}$phoneNumber"
    }

    override fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return true
    }

    override fun getFullyFormattedPhoneNumber(): String {
        return getFullPhoneNumber()
    }

    @RestrictedApi
    override fun setPhoneNo(phoneNumber: String) {
        TODO("Not yet implemented")
    }

    @RestrictedApi
    override fun setCode(countryCode: String) {
        TODO("Not yet implemented")
    }
}
