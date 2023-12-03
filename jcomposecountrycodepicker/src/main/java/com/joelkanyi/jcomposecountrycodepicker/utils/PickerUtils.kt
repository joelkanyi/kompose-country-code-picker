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
package com.joelkanyi.jcomposecountrycodepicker.utils

import android.content.Context
import android.telephony.TelephonyManager
import android.util.Log
import androidx.compose.ui.text.intl.Locale
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.joelkanyi.jcomposecountrycodepicker.R
import com.joelkanyi.jcomposecountrycodepicker.data.CountryData

/**
 * [getDefaultLangCode] Returns the default language code of the device.
 * [context] The context of the activity or fragment.
 */
internal fun getDefaultLangCode(context: Context): String {
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
internal fun getDefaultPhoneCode(context: Context): String {
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
internal fun String.removeSpecialCharacters(): String {
    return this.replace("[^0-9]".toRegex(), "")
}

/**
 * [searchForAnItem] Returns a list of items that match the search string.
 * [searchStr] The search string.
 */
fun List<CountryData>.searchForAnItem(
    searchStr: String,
): List<CountryData> {
    val filteredItems = filter {
        it.cCountryName.contains(
            searchStr,
            ignoreCase = true,
        ) ||
            it.cCountryPhoneNoCode.contains(
                searchStr,
                ignoreCase = true,
            ) ||
            it.countryCode.contains(
                searchStr,
                ignoreCase = true,
            )
    }
    println(filteredItems)
    return filteredItems.toList()
}

/**
 * [getFlags] Returns the flag of the country.
 * [countryName] The name of the country.
 */
internal fun getFlags(countryName: String): Int {
    return when (countryName) {
        "ad" -> R.drawable.ad
        "ae" -> R.drawable.ae
        "af" -> R.drawable.af
        "ag" -> R.drawable.ag
        "ai" -> R.drawable.ai
        "al" -> R.drawable.al
        "am" -> R.drawable.am
        "ao" -> R.drawable.ao
        "aq" -> R.drawable.aq
        "ar" -> R.drawable.ar
        "as" -> R.drawable.`as`
        "at" -> R.drawable.at
        "au" -> R.drawable.au
        "aw" -> R.drawable.aw
        "ax" -> R.drawable.ax
        "az" -> R.drawable.az
        "ba" -> R.drawable.ba
        "bb" -> R.drawable.bb
        "bd" -> R.drawable.bd
        "be" -> R.drawable.be
        "bf" -> R.drawable.bf
        "bg" -> R.drawable.bg
        "bh" -> R.drawable.bh
        "bi" -> R.drawable.bi
        "bj" -> R.drawable.bj
        "bl" -> R.drawable.bl
        "bm" -> R.drawable.bm
        "bn" -> R.drawable.bn
        "bo" -> R.drawable.bo
        "br" -> R.drawable.br
        "bs" -> R.drawable.bs
        "bt" -> R.drawable.bt
        "bw" -> R.drawable.bw
        "by" -> R.drawable.by
        "bz" -> R.drawable.bz
        "ca" -> R.drawable.ca
        "cc" -> R.drawable.cc
        "cd" -> R.drawable.cd
        "cf" -> R.drawable.cf
        "cg" -> R.drawable.cg
        "ch" -> R.drawable.ch
        "ci" -> R.drawable.ci
        "ck" -> R.drawable.ck
        "cl" -> R.drawable.cl
        "cm" -> R.drawable.cm
        "cn" -> R.drawable.cn
        "co" -> R.drawable.co
        "cr" -> R.drawable.cr
        "cu" -> R.drawable.cu
        "cv" -> R.drawable.cv
        "cw" -> R.drawable.cw
        "cx" -> R.drawable.cx
        "cy" -> R.drawable.cy
        "cz" -> R.drawable.cz
        "de" -> R.drawable.de
        "dj" -> R.drawable.dj
        "dk" -> R.drawable.dk
        "dm" -> R.drawable.dm
        "do" -> R.drawable.ic_do
        "dz" -> R.drawable.dz
        "ec" -> R.drawable.ec
        "ee" -> R.drawable.ee
        "eg" -> R.drawable.eg
        "er" -> R.drawable.er
        "es" -> R.drawable.es
        "et" -> R.drawable.et
        "fi" -> R.drawable.fi
        "fj" -> R.drawable.fj
        "fk" -> R.drawable.fk
        "fm" -> R.drawable.fm
        "fo" -> R.drawable.fo
        "fr" -> R.drawable.fr
        "ga" -> R.drawable.ga
        "gb" -> R.drawable.gb
        "gd" -> R.drawable.gd
        "ge" -> R.drawable.ge
        "gf" -> R.drawable.gf
        "gg" -> R.drawable.gg
        "gh" -> R.drawable.gh
        "gi" -> R.drawable.gi
        "gl" -> R.drawable.gl
        "gm" -> R.drawable.gm
        "gn" -> R.drawable.gn
        "gp" -> R.drawable.gp
        "gq" -> R.drawable.gq
        "gr" -> R.drawable.gr
        "gt" -> R.drawable.gt
        "gu" -> R.drawable.gu
        "gw" -> R.drawable.gw
        "gy" -> R.drawable.gy
        "hk" -> R.drawable.hk
        "hn" -> R.drawable.hn
        "hr" -> R.drawable.hr
        "ht" -> R.drawable.ht
        "hu" -> R.drawable.hu
        "id" -> R.drawable.id
        "ie" -> R.drawable.ie
        "il" -> R.drawable.il
        "im" -> R.drawable.im
        "is" -> R.drawable.`is`
        "in" -> R.drawable.`in`
        "io" -> R.drawable.io
        "iq" -> R.drawable.iq
        "ir" -> R.drawable.ir
        "it" -> R.drawable.it
        "je" -> R.drawable.je
        "jm" -> R.drawable.jm
        "jo" -> R.drawable.jo
        "jp" -> R.drawable.jp
        "ke" -> R.drawable.ke
        "kg" -> R.drawable.kg
        "kh" -> R.drawable.kh
        "ki" -> R.drawable.ki
        "km" -> R.drawable.km
        "kn" -> R.drawable.kn
        "kp" -> R.drawable.kp
        "kr" -> R.drawable.kr
        "kw" -> R.drawable.kw
        "ky" -> R.drawable.ky
        "kz" -> R.drawable.kz
        "la" -> R.drawable.la
        "lb" -> R.drawable.lb
        "lc" -> R.drawable.lc
        "li" -> R.drawable.li
        "lk" -> R.drawable.lk
        "lr" -> R.drawable.lr
        "ls" -> R.drawable.ls
        "lt" -> R.drawable.lt
        "lu" -> R.drawable.lu
        "lv" -> R.drawable.lv
        "ly" -> R.drawable.ly
        "ma" -> R.drawable.ma
        "mc" -> R.drawable.mc
        "md" -> R.drawable.md
        "me" -> R.drawable.me
        "mf" -> R.drawable.mf
        "mg" -> R.drawable.mg
        "mh" -> R.drawable.mh
        "mk" -> R.drawable.mk
        "ml" -> R.drawable.ml
        "mm" -> R.drawable.mm
        "mn" -> R.drawable.mn
        "mo" -> R.drawable.mo
        "mp" -> R.drawable.mp
        "mq" -> R.drawable.mq
        "mr" -> R.drawable.mr
        "ms" -> R.drawable.ms
        "mt" -> R.drawable.mt
        "mu" -> R.drawable.mu
        "mv" -> R.drawable.mv
        "mw" -> R.drawable.mw
        "mx" -> R.drawable.mx
        "my" -> R.drawable.my
        "mz" -> R.drawable.mz
        "na" -> R.drawable.na
        "nc" -> R.drawable.nc
        "ne" -> R.drawable.ne
        "nf" -> R.drawable.nf
        "ng" -> R.drawable.ng
        "ni" -> R.drawable.ni
        "nl" -> R.drawable.nl
        "no" -> R.drawable.no
        "np" -> R.drawable.np
        "nr" -> R.drawable.nr
        "nu" -> R.drawable.nu
        "nz" -> R.drawable.nz
        "om" -> R.drawable.om
        "pa" -> R.drawable.pa
        "pe" -> R.drawable.pe
        "pf" -> R.drawable.pf
        "pg" -> R.drawable.pg
        "ph" -> R.drawable.ph
        "pk" -> R.drawable.pk
        "pl" -> R.drawable.pl
        "pm" -> R.drawable.pm
        "pn" -> R.drawable.pn
        "pr" -> R.drawable.pr
        "ps" -> R.drawable.ps
        "pt" -> R.drawable.pt
        "pw" -> R.drawable.pw
        "py" -> R.drawable.py
        "qa" -> R.drawable.qa
        "re" -> R.drawable.re
        "ro" -> R.drawable.ro
        "rs" -> R.drawable.rs
        "ru" -> R.drawable.ru
        "rw" -> R.drawable.rw
        "sa" -> R.drawable.sa
        "sb" -> R.drawable.sb
        "sc" -> R.drawable.sc
        "sd" -> R.drawable.sd
        "se" -> R.drawable.se
        "sg" -> R.drawable.sg
        "sh" -> R.drawable.sh
        "si" -> R.drawable.si
        "sk" -> R.drawable.sk
        "sl" -> R.drawable.sl
        "sm" -> R.drawable.sm
        "sn" -> R.drawable.sn
        "so" -> R.drawable.so
        "sr" -> R.drawable.sr
        "ss" -> R.drawable.ss
        "st" -> R.drawable.st
        "sv" -> R.drawable.sv
        "sx" -> R.drawable.sx
        "sy" -> R.drawable.sy
        "sz" -> R.drawable.sz
        "tc" -> R.drawable.tc
        "td" -> R.drawable.td
        "tg" -> R.drawable.tg
        "th" -> R.drawable.th
        "tj" -> R.drawable.tj
        "tk" -> R.drawable.tk
        "tl" -> R.drawable.tl
        "tm" -> R.drawable.tm
        "tn" -> R.drawable.tn
        "to" -> R.drawable.to
        "tr" -> R.drawable.tr
        "tt" -> R.drawable.tt
        "tv" -> R.drawable.tv
        "tw" -> R.drawable.tw
        "tz" -> R.drawable.tz
        "ua" -> R.drawable.ua
        "ug" -> R.drawable.ug
        "us" -> R.drawable.us
        "uy" -> R.drawable.uy
        "uz" -> R.drawable.uz
        "va" -> R.drawable.va
        "vc" -> R.drawable.vc
        "ve" -> R.drawable.ve
        "vg" -> R.drawable.vg
        "vi" -> R.drawable.vi
        "vn" -> R.drawable.vn
        "vu" -> R.drawable.vu
        "wf" -> R.drawable.wf
        "ws" -> R.drawable.ws
        "xk" -> R.drawable.xk
        "ye" -> R.drawable.ye
        "yt" -> R.drawable.yt
        "za" -> R.drawable.za
        "zm" -> R.drawable.zm
        "zw" -> R.drawable.zw
        else -> R.drawable.tr
    }
}

/**
 * [getCountryName] Returns the name of the country.
 * [countryName] The name of the country.
 */
internal fun getCountryName(countryName: String): Int {
    return when (countryName) {
        "ad" -> R.string.andorra
        "ae" -> R.string.united_arab_emirates
        "af" -> R.string.afghanistan
        "ag" -> R.string.antigua_and_barbuda
        "ai" -> R.string.anguilla
        "al" -> R.string.albania
        "am" -> R.string.armenia
        "ao" -> R.string.angola
        "aq" -> R.string.antarctica
        "ar" -> R.string.argentina
        "as" -> R.string.american_samoa
        "at" -> R.string.austria
        "au" -> R.string.australia
        "aw" -> R.string.aruba
        "ax" -> R.string.aland_islands
        "az" -> R.string.azerbaijan
        "ba" -> R.string.bosnia
        "bb" -> R.string.barbados
        "bd" -> R.string.bangladesh
        "be" -> R.string.belgium
        "bf" -> R.string.burkina_faso
        "bg" -> R.string.bulgaria
        "bh" -> R.string.bahrain
        "bi" -> R.string.burundi
        "bj" -> R.string.benin
        "bl" -> R.string.saint_barhelemy
        "bm" -> R.string.bermuda
        "bn" -> R.string.brunei_darussalam
        "bo" -> R.string.bolivia
        "br" -> R.string.brazil
        "bs" -> R.string.bahamas
        "bt" -> R.string.bhutan
        "bw" -> R.string.botswana
        "by" -> R.string.belarus
        "bz" -> R.string.belize
        "ca" -> R.string.canada
        "cc" -> R.string.cocos
        "cd" -> R.string.congo_democratic
        "cf" -> R.string.central_african
        "cg" -> R.string.congo
        "ch" -> R.string.switzerland
        "ci" -> R.string.cote_dlvoire
        "ck" -> R.string.cook_islands
        "cl" -> R.string.chile
        "cm" -> R.string.cameroon
        "cn" -> R.string.china
        "co" -> R.string.colombia
        "cr" -> R.string.costa_rica
        "cu" -> R.string.cuba
        "cv" -> R.string.cape_verde
        "cw" -> R.string.curacao
        "cx" -> R.string.christmas_island
        "cy" -> R.string.cyprus
        "cz" -> R.string.czech_republic
        "de" -> R.string.germany
        "dj" -> R.string.djibouti
        "dk" -> R.string.denmark
        "dm" -> R.string.dominica
        "do" -> R.string.dominician_republic
        "dz" -> R.string.algeria
        "ec" -> R.string.ecuador
        "ee" -> R.string.estonia
        "eg" -> R.string.egypt
        "er" -> R.string.eritrea
        "es" -> R.string.spain
        "et" -> R.string.ethiopia
        "fi" -> R.string.finland
        "fj" -> R.string.fiji
        "fk" -> R.string.falkland_islands
        "fm" -> R.string.micro
        "fo" -> R.string.faroe_islands
        "fr" -> R.string.france
        "ga" -> R.string.gabon
        "gb" -> R.string.united_kingdom
        "gd" -> R.string.grenada
        "ge" -> R.string.georgia
        "gf" -> R.string.french_guyana
        "gg" -> R.string.guernsey
        "gh" -> R.string.ghana
        "gi" -> R.string.gibraltar
        "gl" -> R.string.greenland
        "gm" -> R.string.gambia
        "gn" -> R.string.guinea
        "gp" -> R.string.guadeloupe
        "gq" -> R.string.equatorial_guinea
        "gr" -> R.string.greece
        "gt" -> R.string.guatemala
        "gu" -> R.string.guam
        "gw" -> R.string.guinea_bissau
        "gy" -> R.string.guyana
        "hk" -> R.string.hong_kong
        "hn" -> R.string.honduras
        "hr" -> R.string.croatia
        "ht" -> R.string.haiti
        "hu" -> R.string.hungary
        "id" -> R.string.indonesia
        "ie" -> R.string.ireland
        "il" -> R.string.israil
        "im" -> R.string.isle_of_man
        "is" -> R.string.iceland
        "in" -> R.string.india
        "io" -> R.string.british_indian_ocean
        "iq" -> R.string.iraq
        "ir" -> R.string.iran
        "it" -> R.string.italia
        "je" -> R.string.jersey
        "jm" -> R.string.jamaica
        "jo" -> R.string.jordan
        "jp" -> R.string.japan
        "ke" -> R.string.kenya
        "kg" -> R.string.kyrgyzstan
        "kh" -> R.string.cambodia
        "ki" -> R.string.kiribati
        "km" -> R.string.comoros
        "kn" -> R.string.saint_kitts
        "kp" -> R.string.north_korea
        "kr" -> R.string.south_korea
        "kw" -> R.string.kuwait
        "ky" -> R.string.cayman_islands
        "kz" -> R.string.kazakhstan
        "la" -> R.string.laos
        "lb" -> R.string.lebanon
        "lc" -> R.string.saint_lucia
        "li" -> R.string.liechtenstein
        "lk" -> R.string.siri_lanka
        "lr" -> R.string.liberia
        "ls" -> R.string.lesotho
        "lt" -> R.string.lithuania
        "lu" -> R.string.luxembourg
        "lv" -> R.string.latvia
        "ly" -> R.string.libya
        "ma" -> R.string.marocco
        "mc" -> R.string.monaco
        "md" -> R.string.moldova
        "me" -> R.string.montenegro
        "mf" -> R.string.saint_martin
        "mg" -> R.string.madagascar
        "mh" -> R.string.marshall_islands
        "mk" -> R.string.north_macedonia
        "ml" -> R.string.mali
        "mm" -> R.string.myanmar
        "mn" -> R.string.mongolia
        "mo" -> R.string.macau
        "mp" -> R.string.northern_mariana
        "mq" -> R.string.martinique
        "mr" -> R.string.mauriatana
        "ms" -> R.string.montserrat
        "mt" -> R.string.malta
        "mu" -> R.string.mauritius
        "mv" -> R.string.maldives
        "mw" -> R.string.malawi
        "mx" -> R.string.mexico
        "my" -> R.string.malaysia
        "mz" -> R.string.mozambique
        "na" -> R.string.namibia
        "nc" -> R.string.new_caledonia
        "ne" -> R.string.niger
        "nf" -> R.string.norfolk
        "ng" -> R.string.nigeria
        "ni" -> R.string.nicaragua
        "nl" -> R.string.netherlands
        "no" -> R.string.norway
        "np" -> R.string.nepal
        "nr" -> R.string.nauru
        "nu" -> R.string.niue
        "nz" -> R.string.new_zealand
        "om" -> R.string.oman
        "pa" -> R.string.panama
        "pe" -> R.string.peru
        "pf" -> R.string.french_polynesia
        "pg" -> R.string.papua_new_guinea
        "ph" -> R.string.philippinies
        "pk" -> R.string.pakistan
        "pl" -> R.string.poland
        "pm" -> R.string.saint_pierre
        "pn" -> R.string.pitcairn
        "pr" -> R.string.puerto_rico
        "ps" -> R.string.state_of_palestine
        "pt" -> R.string.portugal
        "pw" -> R.string.palau
        "py" -> R.string.paraguay
        "qa" -> R.string.qatar
        "re" -> R.string.reunion
        "ro" -> R.string.romania
        "rs" -> R.string.serbia
        "ru" -> R.string.russia
        "rw" -> R.string.rwanda
        "sa" -> R.string.saudi_arabia
        "sb" -> R.string.solomon_islands
        "sc" -> R.string.seychelles
        "sd" -> R.string.sudan
        "se" -> R.string.sweden
        "sg" -> R.string.singapore
        "sh" -> R.string.saint_helena
        "si" -> R.string.slovenia
        "sk" -> R.string.slovakia
        "sl" -> R.string.sierra_leone
        "sm" -> R.string.san_marino
        "sn" -> R.string.senegal
        "so" -> R.string.somali
        "sr" -> R.string.suriname
        "ss" -> R.string.south_sudan
        "st" -> R.string.sao_tome
        "sv" -> R.string.el_salvador
        "sx" -> R.string.sint_maarten
        "sy" -> R.string.syrian
        "sz" -> R.string.swaziland
        "tc" -> R.string.turks_and_caicos
        "td" -> R.string.chad
        "tg" -> R.string.togo
        "th" -> R.string.thailand
        "tj" -> R.string.taijikistan
        "tk" -> R.string.tokelau
        "tl" -> R.string.timor_leste
        "tm" -> R.string.turkmenistan
        "tn" -> R.string.tunisia
        "to" -> R.string.tonga
        "tr" -> R.string.turkey
        "tt" -> R.string.trinidad_and_tobago
        "tv" -> R.string.tuvalu
        "tw" -> R.string.taiwan
        "tz" -> R.string.tazmania
        "ua" -> R.string.ukraina
        "ug" -> R.string.uganda
        "us" -> R.string.united_states_america
        "uy" -> R.string.uruguay
        "uz" -> R.string.uzbekistan
        "va" -> R.string.holy_see
        "vc" -> R.string.saint_vincent
        "ve" -> R.string.venezuela
        "vg" -> R.string.virgin_islands
        "vi" -> R.string.virgin_island_us
        "vn" -> R.string.vietnam
        "vu" -> R.string.vanuatu
        "wf" -> R.string.walli_and_fatuna
        "ws" -> R.string.samoa
        "xk" -> R.string.kosovo
        "ye" -> R.string.yemen
        "yt" -> R.string.mayotte
        "za" -> R.string.south_africa
        "zm" -> R.string.zambia
        "zw" -> R.string.zimbabwe
        else -> R.string.unkown
    }
}

/**
 * [allCountries] is a list of all countries in the world sorted alphabetically.
 */
internal val allCountries: List<CountryData>
    get() = listOf(
        CountryData(
            cCountryCode = "ad",
            cCountryPhoneNoCode = "+376",
            cCountryName = "Andorra",
            cCountryFlag = R.drawable.ad,
        ),
        CountryData(
            cCountryCode = "ae",
            cCountryPhoneNoCode = "+971",
            cCountryName = "United Arab Emirates (UAE)",
            cCountryFlag = R.drawable.ae,
        ),
        CountryData(
            cCountryCode = "af",
            cCountryPhoneNoCode = "+93",
            cCountryName = "Afghanistan",
            cCountryFlag = R.drawable.af,
        ),
        CountryData(
            cCountryCode = "ag",
            cCountryPhoneNoCode = "+1",
            cCountryName = "Antigua and Barbuda",
            cCountryFlag = R.drawable.ag,
        ),
        CountryData(
            cCountryCode = "ai",
            cCountryPhoneNoCode = "+1",
            cCountryName = "Anguilla",
            cCountryFlag = R.drawable.ai,
        ),
        CountryData(
            "al",
            "+355",
            "Albania",
            R.drawable.al,
        ),
        CountryData(
            "am",
            "+374",
            "Armenia",
            R.drawable.am,
        ),
        CountryData(
            "ao",
            "+244",
            "Angola",
            R.drawable.ao,
        ),
        CountryData(
            "aq",
            "+672",
            "Antarctica",
            R.drawable.aq,
        ),
        CountryData(
            "ar",
            "+54",
            "Argentina",
            R.drawable.ar,
        ),
        CountryData(
            "as",
            "+1",
            "American Samoa",
        ),
        CountryData(
            "at",
            "+43",
            "Austria",
            R.drawable.at,
        ),
        CountryData(
            "au",
            "+61",
            "Australia",
            R.drawable.au,
        ),
        CountryData(
            "aw",
            "+297",
            "Aruba",
            R.drawable.aw,
        ),
        CountryData(
            "ax",
            "+358",
            "Åland Islands",
        ),
        CountryData(
            "az",
            "+994",
            "Azerbaijan",
        ),
        CountryData(
            "ba",
            "+387",
            "Bosnia And Herzegovina",
        ),
        CountryData(
            "bb",
            "+1",
            "Barbados",
        ),
        CountryData(
            "bd",
            "+880",
            "Bangladesh",
        ),
        CountryData(
            "be",
            "+32",
            "Belgium",
        ),
        CountryData(
            "bf",
            "+226",
            "Burkina Faso",
        ),
        CountryData(
            "bg",
            "+359",
            "Bulgaria",
        ),
        CountryData(
            "bh",
            "+973",
            "Bahrain",
        ),
        CountryData(
            "bi",
            "+257",
            "Burundi",
        ),
        CountryData(
            "bj",
            "+229",
            "Benin",
        ),
        CountryData(
            "bl",
            "+590",
            "Saint Barthélemy",
        ),
        CountryData(
            "bm",
            "+1",
            "Bermuda",
        ),
        CountryData(
            "bn",
            "+673",
            "Brunei Darussalam",
        ),
        CountryData(
            "bo",
            "+591",
            "Bolivia, Plurinational State Of",
        ),
        CountryData(
            "br",
            "+55",
            "Brazil",
        ),
        CountryData(
            "bs",
            "+1",
            "Bahamas",
        ),
        CountryData(
            "bt",
            "+975",
            "Bhutan",
        ),
        CountryData(
            "bw",
            "+267",
            "Botswana",
        ),
        CountryData(
            "by",
            "+375",
            "Belarus",
        ),
        CountryData(
            "bz",
            "+501",
            "Belize",
        ),
        CountryData(
            "ca",
            "+1",
            "Canada",
        ),
        CountryData(
            "cc",
            "+61",
            "Cocos (keeling) Islands",
        ),
        CountryData(
            "cd",
            "+243",
            "Congo, The Democratic Republic Of The",
        ),
        CountryData(
            "cf",
            "+236",
            "Central African Republic",
        ),
        CountryData(
            "cg",
            "+242",
            "Congo",
        ),
        CountryData(
            "ch",
            "+41",
            "Switzerland",
        ),
        CountryData(
            "ci",
            "+225",
            "Côte D'ivoire",
        ),
        CountryData(
            "ck",
            "+682",
            "Cook Islands",
        ),
        CountryData(
            "cl",
            "+56",
            "Chile",
        ),
        CountryData(
            "cm",
            "+237",
            "Cameroon",
        ),
        CountryData(
            "cn",
            "+86",
            "China",
        ),
        CountryData(
            "co",
            "+57",
            "Colombia",
        ),
        CountryData(
            "cr",
            "+506",
            "Costa Rica",
        ),
        CountryData(
            "cu",
            "+53",
            "Cuba",
        ),
        CountryData(
            "cv",
            "+238",
            "Cape Verde",
        ),
        CountryData(
            "cw",
            "+599",
            "Curaçao",
        ),
        CountryData(
            "cx",
            "+61",
            "Christmas Island",
        ),
        CountryData(
            "cy",
            "+357",
            "Cyprus",
        ),
        CountryData(
            "cz",
            "+420",
            "Czech Republic",
        ),
        CountryData(
            "de",
            "+49",
            "Germany",
        ),
        CountryData(
            "dj",
            "+253",
            "Djibouti",
        ),
        CountryData(
            "dk",
            "+45",
            "Denmark",
        ),
        CountryData(
            "dm",
            "+1",
            "Dominica",
        ),
        CountryData(
            "do",
            "+1",
            "Dominican Republic",
        ),
        CountryData(
            "dz",
            "+213",
            "Algeria",
        ),
        CountryData(
            "ec",
            "+593",
            "Ecuador",
        ),
        CountryData(
            "ee",
            "+372",
            "Estonia",
        ),
        CountryData(
            "eg",
            "+20",
            "Egypt",
        ),
        CountryData(
            "er",
            "+291",
            "Eritrea",
        ),
        CountryData(
            "es",
            "+34",
            "Spain",
        ),
        CountryData(
            "et",
            "+251",
            "Ethiopia",
        ),
        CountryData(
            "fi",
            "+358",
            "Finland",
        ),
        CountryData(
            "fj",
            "+679",
            "Fiji",
        ),
        CountryData(
            "fk",
            "+500",
            "Falkland Islands (malvinas)",
        ),
        CountryData(
            "fm",
            "+691",
            "Micronesia, Federated States Of",
        ),
        CountryData(
            "fo",
            "+298",
            "Faroe Islands",
        ),
        CountryData(
            "fr",
            "+33",
            "France",
        ),
        CountryData(
            "ga",
            "+241",
            "Gabon",
        ),
        CountryData(
            "gb",
            "+44",
            "United Kingdom",
        ),
        CountryData(
            "gd",
            "+1",
            "Grenada",
        ),
        CountryData(
            "ge",
            "+995",
            "Georgia",
        ),
        CountryData(
            "gf",
            "+594",
            "French Guyana",
        ),
        CountryData(
            "gh",
            "+233",
            "Ghana",
        ),
        CountryData(
            "gi",
            "+350",
            "Gibraltar",
        ),
        CountryData(
            "gl",
            "+299",
            "Greenland",
        ),
        CountryData(
            "gm",
            "+220",
            "Gambia",
        ),
        CountryData(
            "gn",
            "+224",
            "Guinea",
        ),
        CountryData(
            "gp",
            "+450",
            "Guadeloupe",
        ),
        CountryData(
            "gq",
            "+240",
            "Equatorial Guinea",
        ),
        CountryData(
            "gr",
            "+30",
            "Greece",
        ),
        CountryData(
            "gt",
            "+502",
            "Guatemala",
        ),
        CountryData(
            "gu",
            "+1",
            "Guam",
        ),
        CountryData(
            "gw",
            "+245",
            "Guinea-bissau",
        ),
        CountryData(
            "gy",
            "+592",
            "Guyana",
        ),
        CountryData(
            "hk",
            "+852",
            "Hong Kong",
        ),
        CountryData(
            "hn",
            "+504",
            "Honduras",
        ),
        CountryData(
            "hr",
            "+385",
            "Croatia",
        ),
        CountryData(
            "ht",
            "+509",
            "Haiti",
        ),
        CountryData(
            "hu",
            "+36",
            "Hungary",
        ),
        CountryData(
            "id",
            "+62",
            "Indonesia",
        ),
        CountryData(
            "ie",
            "+353",
            "Ireland",
        ),
        CountryData(
            "il",
            "+972",
            "Israel",
        ),
        CountryData(
            "im",
            "+44",
            "Isle Of Man",
        ),
        CountryData(
            "is",
            "+354",
            "Iceland",
        ),
        CountryData(
            "in",
            "+91",
            "India",
        ),
        CountryData(
            "io",
            "+246",
            "British Indian Ocean Territory",
        ),
        CountryData(
            "iq",
            "+964",
            "Iraq",
        ),
        CountryData(
            "ir",
            "+98",
            "Iran, Islamic Republic Of",
        ),
        CountryData(
            "it",
            "+39",
            "Italy",
        ),
        CountryData(
            "je",
            "+44",
            "Jersey ",
        ),
        CountryData(
            "jm",
            "+1",
            "Jamaica",
        ),
        CountryData(
            "jo",
            "+962",
            "Jordan",
        ),
        CountryData(
            "jp",
            "+81",
            "Japan",
        ),
        CountryData(
            "ke",
            "+254",
            "Kenya",
        ),
        CountryData(
            "kg",
            "+996",
            "Kyrgyzstan",
        ),
        CountryData(
            "kh",
            "+855",
            "Cambodia",
        ),
        CountryData(
            "ki",
            "+686",
            "Kiribati",
        ),
        CountryData(
            "km",
            "+269",
            "Comoros",
        ),
        CountryData(
            "kn",
            "+1",
            "Saint Kitts and Nevis",
        ),
        CountryData(
            "kp",
            "+850",
            "North Korea",
        ),
        CountryData(
            "kr",
            "+82",
            "South Korea",
        ),
        CountryData(
            "kw",
            "+965",
            "Kuwait",
        ),
        CountryData(
            "ky",
            "+1",
            "Cayman Islands",
        ),
        CountryData(
            "kz",
            "+7",
            "Kazakhstan",
        ),
        CountryData(
            "la",
            "+856",
            "Lao People's Democratic Republic",
        ),
        CountryData(
            "lb",
            "+961",
            "Lebanon",
        ),
        CountryData(
            "lc",
            "+1",
            "Saint Lucia",
        ),
        CountryData(
            "li",
            "+423",
            "Liechtenstein",
        ),
        CountryData(
            "lk",
            "+94",
            "Sri Lanka",
        ),
        CountryData(
            "lr",
            "+231",
            "Liberia",
        ),
        CountryData(
            "ls",
            "+266",
            "Lesotho",
        ),
        CountryData(
            "lt",
            "+370",
            "Lithuania",
        ),
        CountryData(
            "lu",
            "+352",
            "Luxembourg",
        ),
        CountryData(
            "lv",
            "+371",
            "Latvia",
        ),
        CountryData(
            "ly",
            "+218",
            "Libya",
        ),
        CountryData(
            "ma",
            "+212",
            "Morocco",
        ),
        CountryData(
            "mc",
            "+377",
            "Monaco",
        ),
        CountryData(
            "md",
            "+373",
            "Moldova, Republic Of",
        ),
        CountryData(
            "me",
            "+382",
            "Montenegro",
        ),
        CountryData(
            "mf",
            "+590",
            "Saint Martin",
        ),
        CountryData(
            "mg",
            "+261",
            "Madagascar",
        ),
        CountryData(
            "mh",
            "+692",
            "Marshall Islands",
        ),
        CountryData(
            "mk",
            "+389",
            "Macedonia (FYROM)",
        ),
        CountryData(
            "ml",
            "+223",
            "Mali",
        ),
        CountryData(
            "mm",
            "+95",
            "Myanmar",
        ),
        CountryData(
            "mn",
            "+976",
            "Mongolia",
        ),
        CountryData(
            "mo",
            "+853",
            "Macau",
        ),
        CountryData(
            "mp",
            "+1",
            "Northern Mariana Islands",
        ),
        CountryData(
            "mq",
            "+596",
            "Martinique",
        ),
        CountryData(
            "mr",
            "+222",
            "Mauritania",
        ),
        CountryData(
            "ms",
            "+1",
            "Montserrat",
        ),
        CountryData(
            "mt",
            "+356",
            "Malta",
        ),
        CountryData(
            "mu",
            "+230",
            "Mauritius",
        ),
        CountryData(
            "mv",
            "+960",
            "Maldives",
        ),
        CountryData(
            "mw",
            "+265",
            "Malawi",
        ),
        CountryData(
            "mx",
            "+52",
            "Mexico",
        ),
        CountryData(
            "my",
            "+60",
            "Malaysia",
        ),
        CountryData(
            "mz",
            "+258",
            "Mozambique",
        ),
        CountryData(
            "na",
            "+264",
            "Namibia",
        ),
        CountryData(
            "nc",
            "+687",
            "New Caledonia",
        ),
        CountryData(
            "ne",
            "+227",
            "Niger",
        ),
        CountryData(
            "nf",
            "+672",
            "Norfolk Islands",
        ),
        CountryData(
            "ng",
            "+234",
            "Nigeria",
        ),
        CountryData(
            "ni",
            "+505",
            "Nicaragua",
        ),
        CountryData(
            "nl",
            "+31",
            "Netherlands",
        ),
        CountryData(
            "no",
            "+47",
            "Norway",
        ),
        CountryData(
            "np",
            "+977",
            "Nepal",
        ),
        CountryData(
            "nr",
            "+674",
            "Nauru",
        ),
        CountryData(
            "nu",
            "+683",
            "Niue",
        ),
        CountryData(
            "nz",
            "+64",
            "New Zealand",
        ),
        CountryData(
            "om",
            "+968",
            "Oman",
        ),
        CountryData(
            "pa",
            "+507",
            "Panama",
        ),
        CountryData(
            "pe",
            "+51",
            "Peru",
        ),
        CountryData(
            "pf",
            "+689",
            "French Polynesia",
        ),
        CountryData(
            "pg",
            "+675",
            "Papua New Guinea",
        ),
        CountryData(
            "ph",
            "+63",
            "Philippines",
        ),
        CountryData(
            "pk",
            "+92",
            "Pakistan",
        ),
        CountryData(
            "pl",
            "+48",
            "Poland",
        ),
        CountryData(
            "pm",
            "+508",
            "Saint Pierre And Miquelon",
        ),
        CountryData(
            "pn",
            "+870",
            "Pitcairn Islands",
        ),
        CountryData(
            "pr",
            "+1",
            "Puerto Rico",
        ),
        CountryData(
            "ps",
            "+970",
            "Palestine",
        ),
        CountryData(
            "pt",
            "+351",
            "Portugal",
        ),
        CountryData(
            "pw",
            "+680",
            "Palau",
        ),
        CountryData(
            "py",
            "+595",
            "Paraguay",
        ),
        CountryData(
            "qa",
            "+974",
            "Qatar",
        ),
        CountryData(
            "re",
            "+262",
            "Réunion",
        ),
        CountryData(
            "ro",
            "+40",
            "Romania",
        ),
        CountryData(
            "rs",
            "+381",
            "Serbia",
        ),
        CountryData(
            "ru",
            "+7",
            "Russian Federation",
        ),
        CountryData(
            "rw",
            "+250",
            "Rwanda",
        ),
        CountryData(
            "sa",
            "+966",
            "Saudi Arabia",
        ),
        CountryData(
            "sb",
            "+677",
            "Solomon Islands",
        ),
        CountryData(
            "sc",
            "+248",
            "Seychelles",
        ),
        CountryData(
            "sd",
            "+249",
            "Sudan",
        ),
        CountryData(
            "se",
            "+46",
            "Sweden",
        ),
        CountryData(
            "sg",
            "+65",
            "Singapore",
        ),
        CountryData(
            "sh",
            "+290",
            "Saint Helena, Ascension And Tristan Da Cunha",
        ),
        CountryData(
            "si",
            "+386",
            "Slovenia",
        ),
        CountryData(
            "sk",
            "+421",
            "Slovakia",
        ),
        CountryData(
            "sl",
            "+232",
            "Sierra Leone",
        ),
        CountryData(
            "sm",
            "+378",
            "San Marino",
        ),
        CountryData(
            "sn",
            "+221",
            "Senegal",
        ),
        CountryData(
            "so",
            "+252",
            "Somalia",
        ),
        CountryData(
            "sr",
            "+597",
            "Suriname",
        ),
        CountryData(
            "ss",
            "+211",
            "South Sudan",
        ),
        CountryData(
            "st",
            "+239",
            "Sao Tome And Principe",
        ),
        CountryData(
            "sv",
            "+503",
            "El Salvador",
        ),
        CountryData(
            "sx",
            "+1",
            "Sint Maarten",
        ),
        CountryData(
            "sy",
            "+963",
            "Syrian Arab Republic",
        ),
        CountryData(
            "sz",
            "+268",
            "Swaziland",
        ),
        CountryData(
            "tc",
            "+1",
            "Turks and Caicos Islands",
        ),
        CountryData(
            "td",
            "+235",
            "Chad",
        ),
        CountryData(
            "tg",
            "+228",
            "Togo",
        ),
        CountryData(
            "th",
            "+66",
            "Thailand",
        ),
        CountryData(
            "tj",
            "+992",
            "Tajikistan",
        ),
        CountryData(
            "tk",
            "+690",
            "Tokelau",
        ),
        CountryData(
            "tl",
            "+670",
            "Timor-leste",
        ),
        CountryData(
            "tm",
            "+993",
            "Turkmenistan",
        ),
        CountryData(
            "tn",
            "+216",
            "Tunisia",
        ),
        CountryData(
            "to",
            "+676",
            "Tonga",
        ),
        CountryData(
            "tr",
            "+90",
            "Turkey",
        ),
        CountryData(
            "tt",
            "+1",
            "Trinidad &amp; Tobago",
        ),
        CountryData(
            "tv",
            "+688",
            "Tuvalu",
        ),
        CountryData(
            "tw",
            "+886",
            "Taiwan",
        ),
        CountryData(
            "tz",
            "+255",
            "Tanzania, United Republic Of",
        ),
        CountryData(
            "ua",
            "+380",
            "Ukraine",
        ),
        CountryData(
            "ug",
            "+256",
            "Uganda",
        ),
        CountryData(
            "us",
            "+1",
            "United States",
        ),
        CountryData(
            "uy",
            "+598",
            "Uruguay",
        ),
        CountryData(
            "uz",
            "+998",
            "Uzbekistan",
        ),
        CountryData(
            "va",
            "+379",
            "Holy See (vatican City State)",
        ),
        CountryData(
            "vc",
            "+1",
            "Saint Vincent &amp; The Grenadines",
        ),
        CountryData(
            "ve",
            "+58",
            "Venezuela, Bolivarian Republic Of",
        ),
        CountryData(
            "vg",
            "+1",
            "British Virgin Islands",
        ),
        CountryData(
            "vi",
            "+1",
            "US Virgin Islands",
        ),
        CountryData(
            "vn",
            "+84",
            "Vietnam",
        ),
        CountryData(
            "vu",
            "+678",
            "Vanuatu",
        ),
        CountryData(
            "wf",
            "+681",
            "Wallis And Futuna",
        ),
        CountryData(
            "ws",
            "4685",
            "Samoa",
        ),
        CountryData(
            "xk",
            "+383",
            "Kosovo",
        ),
        CountryData(
            "ye",
            "+967",
            "Yemen",
        ),
        CountryData(
            "yt",
            "+262",
            "Mayotte",
        ),
        CountryData(
            "za",
            "+27",
            "South Africa",
        ),
        CountryData(
            "zm",
            "+260",
            "Zambia",
        ),
        CountryData(
            "zw",
            "+263",
            "Zimbabwe",
        ),
    ).sortedBy { it.cCountryName }

/**
 * [getNumberHint] Returns the hint of the country.
 * [countryName] The name of the country.
 */
internal fun getNumberHint(countryName: String): Int {
    return when (countryName) {
        "ad" -> R.string.andorra_hint
        "ae" -> R.string.united_arab_emirates_hint
        "af" -> R.string.afganistan_hint
        "ag" -> R.string.antigua_and_barbuda_hint
        "ai" -> R.string.anguilla_hint
        "al" -> R.string.albania_hint
        "am" -> R.string.armenia_hint
        "ao" -> R.string.angola_hint
        "aq" -> R.string.antarctica_hint
        "ar" -> R.string.argentina_hint
        "as" -> R.string.american_samoa_hint
        "at" -> R.string.austria_hint
        "au" -> R.string.australia_hint
        "aw" -> R.string.aruba_hint
        "ax" -> R.string.aland_islands_hint
        "az" -> R.string.azerbaijan_hint
        "ba" -> R.string.bosnia_hint
        "bb" -> R.string.barbados_hint
        "bd" -> R.string.bangladesh_hint
        "be" -> R.string.belgium_hint
        "bf" -> R.string.burkina_faso_hint
        "bg" -> R.string.bulgaria_hint
        "bh" -> R.string.bahrain_hint
        "bi" -> R.string.burundi_hint
        "bj" -> R.string.benin_hint
        "bl" -> R.string.saint_barhelemy_hint
        "bm" -> R.string.bermuda_hint
        "bn" -> R.string.brunei_darussalam_hint
        "bo" -> R.string.bolivia_hint
        "br" -> R.string.brazil_hint
        "bs" -> R.string.bahamas_hint
        "bt" -> R.string.bhutan_hint
        "bw" -> R.string.botswana_hint
        "by" -> R.string.belarus_hint
        "bz" -> R.string.belize_hint
        "ca" -> R.string.canada_hint
        "cc" -> R.string.cocos_hint
        "cd" -> R.string.congo_democratic_hint
        "cf" -> R.string.central_african_hint
        "cg" -> R.string.congo_hint
        "ch" -> R.string.switzerland_hint
        "ci" -> R.string.cote_dlvoire_hint
        "ck" -> R.string.cook_islands_hint
        "cl" -> R.string.chile_hint
        "cm" -> R.string.cameroon_hint
        "cn" -> R.string.china_hint
        "co" -> R.string.colombia_hint
        "cr" -> R.string.costa_rica_hint
        "cu" -> R.string.cuba_hint
        "cv" -> R.string.cape_verde_hint
        "cw" -> R.string.curacao_hint
        "cx" -> R.string.christmas_island_hint
        "cy" -> R.string.cyprus_hint
        "cz" -> R.string.czech_republic_hint
        "de" -> R.string.germany_hint
        "dj" -> R.string.djibouti_hint
        "dk" -> R.string.denmark_hint
        "dm" -> R.string.dominica_hint
        "do" -> R.string.dominician_republic_hint
        "dz" -> R.string.algeria_hint
        "ec" -> R.string.ecuador_hint
        "ee" -> R.string.estonia_hint
        "eg" -> R.string.egypt_hint
        "er" -> R.string.eritrea_hint
        "es" -> R.string.spain_hint
        "et" -> R.string.ethiopia_hint
        "fi" -> R.string.finland_hint
        "fj" -> R.string.fiji_hint
        "fk" -> R.string.falkland_islands_hint
        "fm" -> R.string.micro_hint
        "fo" -> R.string.faroe_islands_hint
        "fr" -> R.string.france_hint
        "ga" -> R.string.gabon_hint
        "gb" -> R.string.united_kingdom_hint
        "gd" -> R.string.grenada_hint
        "ge" -> R.string.georgia_hint
        "gf" -> R.string.french_guyana_hint
        "gg" -> R.string.guernsey_hint
        "gh" -> R.string.ghana_hint
        "gi" -> R.string.unkown
        "gl" -> R.string.greenland_hint
        "gm" -> R.string.gambia_hint
        "gn" -> R.string.guinea_hint
        "gp" -> R.string.guadeloupe_hint
        "gq" -> R.string.equatorial_guinea_hint
        "gr" -> R.string.greece_hint
        "gt" -> R.string.guatemala_hint
        "gu" -> R.string.guam_hint
        "gw" -> R.string.guinea_bissau_hint
        "gy" -> R.string.guyana_hint
        "hk" -> R.string.hong_kong_hint
        "hn" -> R.string.honduras_hint
        "hr" -> R.string.croatia_hint
        "ht" -> R.string.haiti_hint
        "hu" -> R.string.hungary_hint
        "id" -> R.string.indonesia_hint
        "ie" -> R.string.ireland_hint
        "il" -> R.string.israil_hint
        "im" -> R.string.isle_of_man
        "is" -> R.string.iceland
        "in" -> R.string.india_hint
        "io" -> R.string.british_indian_ocean
        "iq" -> R.string.iraq_hint
        "ir" -> R.string.iran_hint
        "it" -> R.string.italia_hint
        "je" -> R.string.jersey_hint
        "jm" -> R.string.jamaica_hint
        "jo" -> R.string.jordan_hint
        "jp" -> R.string.japan_hint
        "ke" -> R.string.kenya_hint
        "kg" -> R.string.kyrgyzstan_hint
        "kh" -> R.string.cambodia_hint
        "ki" -> R.string.kiribati
        "km" -> R.string.comoros_hint
        "kn" -> R.string.saint_kitts_hint
        "kp" -> R.string.north_korea_hint
        "kr" -> R.string.south_korea_hint
        "kw" -> R.string.kuwait_hint
        "ky" -> R.string.cayman_islands_hint
        "kz" -> R.string.kazakhstan_hint
        "la" -> R.string.laos_hint
        "lb" -> R.string.lebanon_hint
        "lc" -> R.string.saint_lucia_hint
        "li" -> R.string.liechtenstein
        "lk" -> R.string.siri_lanka_hint
        "lr" -> R.string.liberia_hint
        "ls" -> R.string.lesotho_hint
        "lt" -> R.string.lithuania_hint
        "lu" -> R.string.luxembourg_hint
        "lv" -> R.string.latvia_hint
        "ly" -> R.string.libya_hint
        "ma" -> R.string.marocco_hint
        "mc" -> R.string.monaco_hint
        "md" -> R.string.moldova_hint
        "me" -> R.string.montenegro_hint
        "mf" -> R.string.saint_martin_hint
        "mg" -> R.string.madagascar_hint
        "mh" -> R.string.marshall_islands_hint
        "mk" -> R.string.north_macedonia_hint
        "ml" -> R.string.mali_hint
        "mm" -> R.string.myanmar_hint
        "mn" -> R.string.mongolia_hint
        "mo" -> R.string.macau_hint
        "mp" -> R.string.northern_mariana_hint
        "mq" -> R.string.martinique_hint
        "mr" -> R.string.mauriatana_hint
        "ms" -> R.string.montserrat_hint
        "mt" -> R.string.malta_hint
        "mu" -> R.string.mauritius_hint
        "mv" -> R.string.maldives_hint
        "mw" -> R.string.malawi_hint
        "mx" -> R.string.mexico_hint
        "my" -> R.string.malaysia_hint
        "mz" -> R.string.mozambique_hint
        "na" -> R.string.namibia_hint
        "nc" -> R.string.new_caledonia_hint
        "ne" -> R.string.niger_hint
        "nf" -> R.string.norfolk_hint
        "ng" -> R.string.nigeria_hint
        "ni" -> R.string.nicaragua
        "nl" -> R.string.netherlands_hint
        "no" -> R.string.norway_hint
        "np" -> R.string.nepal_hint
        "nr" -> R.string.nauru_hint
        "nu" -> R.string.niue_hint
        "nz" -> R.string.new_zealand_hint
        "om" -> R.string.oman_hint
        "pa" -> R.string.panama_hint
        "pe" -> R.string.peru_hint
        "pf" -> R.string.french_polynesia_hint
        "pg" -> R.string.papua_new_guinea_hint
        "ph" -> R.string.philippinies_hint
        "pk" -> R.string.pakistan_hint
        "pl" -> R.string.poland_hint
        "pm" -> R.string.saint_pierre_hint
        "pn" -> R.string.pitcairn
        "pr" -> R.string.puerto_rico_hint
        "ps" -> R.string.state_of_palestine_hint
        "pt" -> R.string.portugal_hint
        "pw" -> R.string.palau_hint
        "py" -> R.string.paraguay_hint
        "qa" -> R.string.qatar_hint
        "re" -> R.string.reunion_hint
        "ro" -> R.string.romania_hint
        "rs" -> R.string.serbia_hint
        "ru" -> R.string.russia_hint
        "rw" -> R.string.rwanda_hint
        "sa" -> R.string.saudi_arabia_hint
        "sb" -> R.string.solomon_islands_hint
        "sc" -> R.string.seychelles_hint
        "sd" -> R.string.sudan_hint
        "se" -> R.string.sweden_hint
        "sg" -> R.string.singapore_hint
        "sh" -> R.string.saint_helena_hint
        "si" -> R.string.slovenia_hint
        "sk" -> R.string.slovakia_hint
        "sl" -> R.string.sierra_leone_hint
        "sm" -> R.string.san_marino_hint
        "sn" -> R.string.senegal_hint
        "so" -> R.string.somali_hint
        "sr" -> R.string.suriname_hint
        "ss" -> R.string.south_sudan_hint
        "st" -> R.string.sao_tome_hint
        "sv" -> R.string.el_salvador_hint
        "sx" -> R.string.sint_maarten_hint
        "sy" -> R.string.syrian_hint
        "sz" -> R.string.swaziland_hint
        "tc" -> R.string.turks_and_caicos_hint
        "td" -> R.string.chad_hint
        "tg" -> R.string.togo_hint
        "th" -> R.string.thailand_hint
        "tj" -> R.string.taijikistan_hint
        "tk" -> R.string.tokelau_hint
        "tl" -> R.string.timor_leste_hint
        "tm" -> R.string.turkmenistan_hint
        "tn" -> R.string.tunisia_hint
        "to" -> R.string.tonga_hint
        "tr" -> R.string.turkey_hint
        "tt" -> R.string.trinidad_and_tobago_hint
        "tv" -> R.string.tuvalu_hint
        "tw" -> R.string.taiwan_hint
        "tz" -> R.string.tazmania_hint
        "ua" -> R.string.ukraina_hint
        "ug" -> R.string.uganda_hint
        "us" -> R.string.united_states_america_hint
        "uy" -> R.string.uruguay_hint
        "uz" -> R.string.uzbekistan_hint
        "va" -> R.string.holy_see
        "vc" -> R.string.saint_vincent_hint
        "ve" -> R.string.venezuela_hint
        "vg" -> R.string.virgin_islands_hint
        "vi" -> R.string.virgin_island_us
        "vn" -> R.string.vietnam_hint
        "vu" -> R.string.vanuatu_hint
        "wf" -> R.string.walli_and_fatuna_hint
        "ws" -> R.string.samoa_hint
        "xk" -> R.string.kosovo_hint
        "ye" -> R.string.yemen_hint
        "yt" -> R.string.mayotte_hint
        "za" -> R.string.south_africa_hint
        "zm" -> R.string.zambia_hint
        "zw" -> R.string.zimbabwe_hint
        else -> R.string.unkown
    }
}
