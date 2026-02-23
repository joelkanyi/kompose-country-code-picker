/*
 * Copyright 2025 Joel Kanyi.
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
    override val showCountryCode: Boolean = true
    override val showCountryFlag: Boolean = true

    override fun getCountryName(): String = countryList.first().name

    override fun getCountryPhoneCode(): String = countryList.first().phoneNoCode

    override fun getCountryPhoneCodeWithoutPrefix(): String = countryList.first().phoneNoCode.substring(1)

    override fun getPhoneNumberWithoutPrefix(): String = phoneNumber

    override fun getFullPhoneNumberWithoutPrefix(): String {
        val phoneCode = getCountryPhoneCodeWithoutPrefix()
        return "$phoneCode$phoneNumber"
    }

    override fun getFullPhoneNumber(): String = "${getCountryPhoneCode()}$phoneNumber"

    override fun isPhoneNumberValid(phoneNumber: String): Boolean = true

    override fun getFullyFormattedPhoneNumber(): String = getFullPhoneNumber()

    @RestrictedApi
    override fun setPhoneNo(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }

    @RestrictedApi
    override fun setCode(countryCode: String) {
        this.countryCode = countryCode
    }
}
