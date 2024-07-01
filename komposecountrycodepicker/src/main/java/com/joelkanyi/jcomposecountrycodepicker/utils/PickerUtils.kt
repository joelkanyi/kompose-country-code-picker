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
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.joelkanyi.jcomposecountrycodepicker.R
import com.joelkanyi.jcomposecountrycodepicker.data.Country

internal object PickerUtils {
    /**
     * [getCountry] Returns the country of the supplied country code.
     * if the country code is empty, the default country is the United States.
     */
    fun String.getCountry(): Country {
        val default = allCountries.first { it.code.lowercase() == "us" }
        return if (this.isNotEmpty()) {
            allCountries.find { it.code.lowercase() == this.lowercase() } ?: default
        } else {
            default
        }
    }

    /**
     * [getDefaultLangCode] Returns the default language code of the device.
     * [context] The context of the activity or fragment.
     */

    fun getDefaultLangCode(context: Context): String {
        return try {
            val localeCode: TelephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val countryCode = localeCode.networkCountryIso
            countryCode.ifEmpty {
                "us"
            }
        } catch (e: Exception) {
            "us"
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

    /**
     * [searchForAnItem] Returns a list of items that match the search string.
     * [searchStr] The search string.
     */
    fun List<Country>.searchForAnItem(
        searchStr: String,
    ): List<Country> {
        val filteredItems = filter {
            it.name.contains(
                searchStr,
                ignoreCase = true,
            ) ||
                it.phoneNoCode.contains(
                    searchStr,
                    ignoreCase = true,
                ) ||
                it.code.contains(
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
    fun getFlags(countryName: String): Int {
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
    fun getCountryName(countryName: String): Int {
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
    val allCountries: List<Country>
        get() = listOf(
            Country(
                code = "ad",
                phoneNoCode = "+376",
                name = "Andorra",
                flag = R.drawable.ad,
            ),
            Country(
                code = "ae",
                phoneNoCode = "+971",
                name = "United Arab Emirates (UAE)",
                flag = R.drawable.ae,
            ),
            Country(
                code = "af",
                phoneNoCode = "+93",
                name = "Afghanistan",
                flag = R.drawable.af,
            ),
            Country(
                code = "ag",
                phoneNoCode = "+1",
                name = "Antigua and Barbuda",
                flag = R.drawable.ag,
            ),
            Country(
                code = "ai",
                phoneNoCode = "+1",
                name = "Anguilla",
                flag = R.drawable.ai,
            ),
            Country(
                "al",
                "+355",
                "Albania",
                R.drawable.al,
            ),
            Country(
                "am",
                "+374",
                "Armenia",
                R.drawable.am,
            ),
            Country(
                "ao",
                "+244",
                "Angola",
                R.drawable.ao,
            ),
            Country(
                "aq",
                "+672",
                "Antarctica",
                R.drawable.aq,
            ),
            Country(
                "ar",
                "+54",
                "Argentina",
                R.drawable.ar,
            ),
            Country(
                "as",
                "+1",
                "American Samoa",
                R.drawable.`as`,
            ),
            Country(
                "at",
                "+43",
                "Austria",
                R.drawable.at,
            ),
            Country(
                "au",
                "+61",
                "Australia",
                R.drawable.au,
            ),
            Country(
                "aw",
                "+297",
                "Aruba",
                R.drawable.aw,
            ),
            Country(
                "ax",
                "+358",
                "Åland Islands",
                R.drawable.ax,
            ),
            Country(
                "az",
                "+994",
                "Azerbaijan",
                R.drawable.az,
            ),
            Country(
                "ba",
                "+387",
                "Bosnia And Herzegovina",
                R.drawable.ba,
            ),
            Country(
                "bb",
                "+1",
                "Barbados",
                R.drawable.bb,
            ),
            Country(
                "bd",
                "+880",
                "Bangladesh",
                R.drawable.bd,
            ),
            Country(
                "be",
                "+32",
                "Belgium",
                R.drawable.be,
            ),
            Country(
                "bf",
                "+226",
                "Burkina Faso",
                R.drawable.bf,
            ),
            Country(
                "bg",
                "+359",
                "Bulgaria",
                R.drawable.bg,
            ),
            Country(
                "bh",
                "+973",
                "Bahrain",
                R.drawable.bh,
            ),
            Country(
                "bi",
                "+257",
                "Burundi",
                R.drawable.bi,
            ),
            Country(
                "bj",
                "+229",
                "Benin",
                R.drawable.bj,
            ),
            Country(
                "bl",
                "+590",
                "Saint Barthélemy",
                R.drawable.bl,
            ),
            Country(
                "bm",
                "+1",
                "Bermuda",
                R.drawable.bm,
            ),
            Country(
                "bn",
                "+673",
                "Brunei Darussalam",
                R.drawable.bn,
            ),
            Country(
                "bo",
                "+591",
                "Bolivia, Plurinational State Of",
                R.drawable.bo,
            ),
            Country(
                "br",
                "+55",
                "Brazil",
                R.drawable.br,
            ),
            Country(
                "bs",
                "+1",
                "Bahamas",
                R.drawable.bs,
            ),
            Country(
                "bt",
                "+975",
                "Bhutan",
                R.drawable.bt,
            ),
            Country(
                "bw",
                "+267",
                "Botswana",
                R.drawable.bw,
            ),
            Country(
                "by",
                "+375",
                "Belarus",
                R.drawable.by,
            ),
            Country(
                "bz",
                "+501",
                "Belize",
                R.drawable.bz,
            ),
            Country(
                "ca",
                "+1",
                "Canada",
                R.drawable.ca,
            ),
            Country(
                "cc",
                "+61",
                "Cocos (keeling) Islands",
                R.drawable.cc,
            ),
            Country(
                "cd",
                "+243",
                "Congo, The Democratic Republic Of The",
                R.drawable.cd,
            ),
            Country(
                "cf",
                "+236",
                "Central African Republic",
                R.drawable.cf,
            ),
            Country(
                "cg",
                "+242",
                "Congo",
                R.drawable.cg,
            ),
            Country(
                "ch",
                "+41",
                "Switzerland",
                R.drawable.ch,
            ),
            Country(
                "ci",
                "+225",
                "Côte D'ivoire",
                R.drawable.ci,
            ),
            Country(
                "ck",
                "+682",
                "Cook Islands",
                R.drawable.ck,
            ),
            Country(
                "cl",
                "+56",
                "Chile",
                R.drawable.cl,
            ),
            Country(
                "cm",
                "+237",
                "Cameroon",
                R.drawable.cm,
            ),
            Country(
                "cn",
                "+86",
                "China",
                R.drawable.cn,
            ),
            Country(
                "co",
                "+57",
                "Colombia",
                R.drawable.co,
            ),
            Country(
                "cr",
                "+506",
                "Costa Rica",
                R.drawable.cr,
            ),
            Country(
                "cu",
                "+53",
                "Cuba",
                R.drawable.cu,
            ),
            Country(
                "cv",
                "+238",
                "Cape Verde",
                R.drawable.cv,
            ),
            Country(
                "cw",
                "+599",
                "Curaçao",
                R.drawable.cw,
            ),
            Country(
                "cx",
                "+61",
                "Christmas Island",
                R.drawable.cx,
            ),
            Country(
                "cy",
                "+357",
                "Cyprus",
                R.drawable.cy,
            ),
            Country(
                "cz",
                "+420",
                "Czech Republic",
                R.drawable.cz,
            ),
            Country(
                "de",
                "+49",
                "Germany",
                R.drawable.de,
            ),
            Country(
                "dj",
                "+253",
                "Djibouti",
                R.drawable.dj,
            ),
            Country(
                "dk",
                "+45",
                "Denmark",
                R.drawable.dk,
            ),
            Country(
                "dm",
                "+1",
                "Dominica",
                R.drawable.dm,
            ),
            Country(
                "do",
                "+1",
                "Dominican Republic",
                R.drawable.ic_do,
            ),
            Country(
                "dz",
                "+213",
                "Algeria",
                R.drawable.dz,
            ),
            Country(
                "ec",
                "+593",
                "Ecuador",
                R.drawable.ec,
            ),
            Country(
                "ee",
                "+372",
                "Estonia",
                R.drawable.ee,
            ),
            Country(
                "eg",
                "+20",
                "Egypt",
                R.drawable.eg,
            ),
            Country(
                "er",
                "+291",
                "Eritrea",
                R.drawable.er,
            ),
            Country(
                "es",
                "+34",
                "Spain",
                R.drawable.es,
            ),
            Country(
                "et",
                "+251",
                "Ethiopia",
                R.drawable.et,
            ),
            Country(
                "fi",
                "+358",
                "Finland",
                R.drawable.fi,
            ),
            Country(
                "fj",
                "+679",
                "Fiji",
                R.drawable.fj,
            ),
            Country(
                "fk",
                "+500",
                "Falkland Islands (malvinas)",
                R.drawable.fk,
            ),
            Country(
                "fm",
                "+691",
                "Micronesia, Federated States Of",
                R.drawable.fm,
            ),
            Country(
                "fo",
                "+298",
                "Faroe Islands",
                R.drawable.fo,
            ),
            Country(
                "fr",
                "+33",
                "France",
                R.drawable.fr,
            ),
            Country(
                "ga",
                "+241",
                "Gabon",
                R.drawable.ga,
            ),
            Country(
                "gb",
                "+44",
                "United Kingdom",
                R.drawable.gb,
            ),
            Country(
                "gd",
                "+1",
                "Grenada",
                R.drawable.gd,
            ),
            Country(
                "ge",
                "+995",
                "Georgia",
                R.drawable.ge,
            ),
            Country(
                "gf",
                "+594",
                "French Guyana",
                R.drawable.gf,
            ),
            Country(
                "gh",
                "+233",
                "Ghana",
                R.drawable.gh,
            ),
            Country(
                "gi",
                "+350",
                "Gibraltar",
                R.drawable.gi,
            ),
            Country(
                "gl",
                "+299",
                "Greenland",
                R.drawable.gl,
            ),
            Country(
                "gm",
                "+220",
                "Gambia",
                R.drawable.gm,
            ),
            Country(
                "gn",
                "+224",
                "Guinea",
                R.drawable.gn,
            ),
            Country(
                "gp",
                "+450",
                "Guadeloupe",
                R.drawable.gp,
            ),
            Country(
                "gq",
                "+240",
                "Equatorial Guinea",
                R.drawable.gq,
            ),
            Country(
                "gr",
                "+30",
                "Greece",
                R.drawable.gr,
            ),
            Country(
                "gt",
                "+502",
                "Guatemala",
                R.drawable.gt,
            ),
            Country(
                "gu",
                "+1",
                "Guam",
                R.drawable.gu,
            ),
            Country(
                "gw",
                "+245",
                "Guinea-bissau",
                R.drawable.gw,
            ),
            Country(
                "gy",
                "+592",
                "Guyana",
                R.drawable.gy,
            ),
            Country(
                "hk",
                "+852",
                "Hong Kong",
                R.drawable.hk,
            ),
            Country(
                "hn",
                "+504",
                "Honduras",
                R.drawable.hn,
            ),
            Country(
                "hr",
                "+385",
                "Croatia",
                R.drawable.hr,
            ),
            Country(
                "ht",
                "+509",
                "Haiti",
                R.drawable.ht,
            ),
            Country(
                "hu",
                "+36",
                "Hungary",
                R.drawable.hu,
            ),
            Country(
                "id",
                "+62",
                "Indonesia",
                R.drawable.id,
            ),
            Country(
                "ie",
                "+353",
                "Ireland",
                R.drawable.ie,
            ),
            Country(
                "il",
                "+972",
                "Israel",
                R.drawable.il,
            ),
            Country(
                "im",
                "+44",
                "Isle Of Man",
                R.drawable.im,
            ),
            Country(
                "is",
                "+354",
                "Iceland",
                R.drawable.`is`,
            ),
            Country(
                "in",
                "+91",
                "India",
                R.drawable.`in`,
            ),
            Country(
                "io",
                "+246",
                "British Indian Ocean Territory",
                R.drawable.io,
            ),
            Country(
                "iq",
                "+964",
                "Iraq",
                R.drawable.iq,
            ),
            Country(
                "ir",
                "+98",
                "Iran, Islamic Republic Of",
                R.drawable.ir,
            ),
            Country(
                "it",
                "+39",
                "Italy",
                R.drawable.it,
            ),
            Country(
                "je",
                "+44",
                "Jersey ",
                R.drawable.je,
            ),
            Country(
                "jm",
                "+1",
                "Jamaica",
                R.drawable.jm,
            ),
            Country(
                "jo",
                "+962",
                "Jordan",
                R.drawable.jo,
            ),
            Country(
                "jp",
                "+81",
                "Japan",
                R.drawable.jp,
            ),
            Country(
                "ke",
                "+254",
                "Kenya",
                R.drawable.ke,
            ),
            Country(
                "kg",
                "+996",
                "Kyrgyzstan",
                R.drawable.kg,
            ),
            Country(
                "kh",
                "+855",
                "Cambodia",
                R.drawable.kh,
            ),
            Country(
                "ki",
                "+686",
                "Kiribati",
                R.drawable.ki,
            ),
            Country(
                "km",
                "+269",
                "Comoros",
                R.drawable.km,
            ),
            Country(
                "kn",
                "+1",
                "Saint Kitts and Nevis",
                R.drawable.kn,
            ),
            Country(
                "kp",
                "+850",
                "North Korea",
                R.drawable.kp,
            ),
            Country(
                "kr",
                "+82",
                "South Korea",
                R.drawable.kr,
            ),
            Country(
                "kw",
                "+965",
                "Kuwait",
                R.drawable.kw,
            ),
            Country(
                "ky",
                "+1",
                "Cayman Islands",
                R.drawable.ky,
            ),
            Country(
                "kz",
                "+7",
                "Kazakhstan",
                R.drawable.kz,
            ),
            Country(
                "la",
                "+856",
                "Lao People's Democratic Republic",
                R.drawable.la,
            ),
            Country(
                "lb",
                "+961",
                "Lebanon",
                R.drawable.lb,
            ),
            Country(
                "lc",
                "+1",
                "Saint Lucia",
                R.drawable.lc,
            ),
            Country(
                "li",
                "+423",
                "Liechtenstein",
                R.drawable.li,
            ),
            Country(
                "lk",
                "+94",
                "Sri Lanka",
                R.drawable.lk,
            ),
            Country(
                "lr",
                "+231",
                "Liberia",
                R.drawable.lr,
            ),
            Country(
                "ls",
                "+266",
                "Lesotho",
                R.drawable.ls,
            ),
            Country(
                "lt",
                "+370",
                "Lithuania",
                R.drawable.lt,
            ),
            Country(
                "lu",
                "+352",
                "Luxembourg",
                R.drawable.lu,
            ),
            Country(
                "lv",
                "+371",
                "Latvia",
                R.drawable.lv,
            ),
            Country(
                "ly",
                "+218",
                "Libya",
                R.drawable.ly,
            ),
            Country(
                "ma",
                "+212",
                "Morocco",
                R.drawable.ma,
            ),
            Country(
                "mc",
                "+377",
                "Monaco",
                R.drawable.mc,
            ),
            Country(
                "md",
                "+373",
                "Moldova, Republic Of",
                R.drawable.md,
            ),
            Country(
                "me",
                "+382",
                "Montenegro",
                R.drawable.me,
            ),
            Country(
                "mf",
                "+590",
                "Saint Martin",
                R.drawable.mf,
            ),
            Country(
                "mg",
                "+261",
                "Madagascar",
                R.drawable.mg,
            ),
            Country(
                "mh",
                "+692",
                "Marshall Islands",
                R.drawable.mh,
            ),
            Country(
                "mk",
                "+389",
                "Macedonia (FYROM)",
                R.drawable.mk,
            ),
            Country(
                "ml",
                "+223",
                "Mali",
                R.drawable.ml,
            ),
            Country(
                "mm",
                "+95",
                "Myanmar",
                R.drawable.mm,
            ),
            Country(
                "mn",
                "+976",
                "Mongolia",
                R.drawable.mn,
            ),
            Country(
                "mo",
                "+853",
                "Macau",
                R.drawable.mo,
            ),
            Country(
                "mp",
                "+1",
                "Northern Mariana Islands",
                R.drawable.mp,
            ),
            Country(
                "mq",
                "+596",
                "Martinique",
                R.drawable.mq,
            ),
            Country(
                "mr",
                "+222",
                "Mauritania",
                R.drawable.mr,
            ),
            Country(
                "ms",
                "+1",
                "Montserrat",
                R.drawable.ms,
            ),
            Country(
                "mt",
                "+356",
                "Malta",
                R.drawable.mt,
            ),
            Country(
                "mu",
                "+230",
                "Mauritius",
                R.drawable.mu,
            ),
            Country(
                "mv",
                "+960",
                "Maldives",
                R.drawable.mv,
            ),
            Country(
                "mw",
                "+265",
                "Malawi",
                R.drawable.mw,
            ),
            Country(
                "mx",
                "+52",
                "Mexico",
                R.drawable.mx,
            ),
            Country(
                "my",
                "+60",
                "Malaysia",
                R.drawable.my,
            ),
            Country(
                "mz",
                "+258",
                "Mozambique",
                R.drawable.mz,
            ),
            Country(
                "na",
                "+264",
                "Namibia",
                R.drawable.na,
            ),
            Country(
                "nc",
                "+687",
                "New Caledonia",
                R.drawable.nc,
            ),
            Country(
                "ne",
                "+227",
                "Niger",
                R.drawable.ne,
            ),
            Country(
                "nf",
                "+672",
                "Norfolk Islands",
                R.drawable.nf,
            ),
            Country(
                "ng",
                "+234",
                "Nigeria",
                R.drawable.ng,
            ),
            Country(
                "ni",
                "+505",
                "Nicaragua",
                R.drawable.ni,
            ),
            Country(
                "nl",
                "+31",
                "Netherlands",
                R.drawable.nl,
            ),
            Country(
                "no",
                "+47",
                "Norway",
                R.drawable.no,
            ),
            Country(
                "np",
                "+977",
                "Nepal",
                R.drawable.np,
            ),
            Country(
                "nr",
                "+674",
                "Nauru",
                R.drawable.nr,
            ),
            Country(
                "nu",
                "+683",
                "Niue",
                R.drawable.nu,
            ),
            Country(
                "nz",
                "+64",
                "New Zealand",
                R.drawable.nz,
            ),
            Country(
                "om",
                "+968",
                "Oman",
                R.drawable.om,
            ),
            Country(
                "pa",
                "+507",
                "Panama",
                R.drawable.pa,
            ),
            Country(
                "pe",
                "+51",
                "Peru",
                R.drawable.pe,
            ),
            Country(
                "pf",
                "+689",
                "French Polynesia",
                R.drawable.pf,
            ),
            Country(
                "pg",
                "+675",
                "Papua New Guinea",
                R.drawable.pg,
            ),
            Country(
                "ph",
                "+63",
                "Philippines",
                R.drawable.ph,
            ),
            Country(
                "pk",
                "+92",
                "Pakistan",
                R.drawable.pk,
            ),
            Country(
                "pl",
                "+48",
                "Poland",
                R.drawable.pl,
            ),
            Country(
                "pm",
                "+508",
                "Saint Pierre And Miquelon",
                R.drawable.pm,
            ),
            Country(
                "pn",
                "+870",
                "Pitcairn Islands",
                R.drawable.pn,
            ),
            Country(
                "pr",
                "+1",
                "Puerto Rico",
                R.drawable.pr,
            ),
            Country(
                "ps",
                "+970",
                "Palestine",
                R.drawable.ps,
            ),
            Country(
                "pt",
                "+351",
                "Portugal",
                R.drawable.pt,
            ),
            Country(
                "pw",
                "+680",
                "Palau",
                R.drawable.pw,
            ),
            Country(
                "py",
                "+595",
                "Paraguay",
                R.drawable.py,
            ),
            Country(
                "qa",
                "+974",
                "Qatar",
                R.drawable.qa,
            ),
            Country(
                "re",
                "+262",
                "Réunion",
                R.drawable.re,
            ),
            Country(
                "ro",
                "+40",
                "Romania",
                R.drawable.ro,
            ),
            Country(
                "rs",
                "+381",
                "Serbia",
                R.drawable.rs,
            ),
            Country(
                "ru",
                "+7",
                "Russian Federation",
                R.drawable.ru,
            ),
            Country(
                "rw",
                "+250",
                "Rwanda",
                R.drawable.rw,
            ),
            Country(
                "sa",
                "+966",
                "Saudi Arabia",
                R.drawable.sa,
            ),
            Country(
                "sb",
                "+677",
                "Solomon Islands",
                R.drawable.sb,
            ),
            Country(
                "sc",
                "+248",
                "Seychelles",
                R.drawable.sc,
            ),
            Country(
                "sd",
                "+249",
                "Sudan",
                R.drawable.sd,
            ),
            Country(
                "se",
                "+46",
                "Sweden",
                R.drawable.se,
            ),
            Country(
                "sg",
                "+65",
                "Singapore",
                R.drawable.sg,
            ),
            Country(
                "sh",
                "+290",
                "Saint Helena, Ascension And Tristan Da Cunha",
                R.drawable.sh,
            ),
            Country(
                "si",
                "+386",
                "Slovenia",
                R.drawable.si,
            ),
            Country(
                "sk",
                "+421",
                "Slovakia",
                R.drawable.sk,
            ),
            Country(
                "sl",
                "+232",
                "Sierra Leone",
                R.drawable.sl,
            ),
            Country(
                "sm",
                "+378",
                "San Marino",
                R.drawable.sm,
            ),
            Country(
                "sn",
                "+221",
                "Senegal",
                R.drawable.sn,
            ),
            Country(
                "so",
                "+252",
                "Somalia",
                R.drawable.so,
            ),
            Country(
                "sr",
                "+597",
                "Suriname",
                R.drawable.sr,
            ),
            Country(
                "ss",
                "+211",
                "South Sudan",
                R.drawable.ss,
            ),
            Country(
                "st",
                "+239",
                "Sao Tome And Principe",
                R.drawable.st,
            ),
            Country(
                "sv",
                "+503",
                "El Salvador",
                R.drawable.sv,
            ),
            Country(
                "sx",
                "+1",
                "Sint Maarten",
                R.drawable.sx,
            ),
            Country(
                "sy",
                "+963",
                "Syrian Arab Republic",
                R.drawable.sy,
            ),
            Country(
                "sz",
                "+268",
                "Swaziland",
                R.drawable.sz,
            ),
            Country(
                "tc",
                "+1",
                "Turks and Caicos Islands",
                R.drawable.tc,
            ),
            Country(
                "td",
                "+235",
                "Chad",
                R.drawable.td,
            ),
            Country(
                "tg",
                "+228",
                "Togo",
                R.drawable.tg,
            ),
            Country(
                "th",
                "+66",
                "Thailand",
                R.drawable.th,
            ),
            Country(
                "tj",
                "+992",
                "Tajikistan",
                R.drawable.tj,
            ),
            Country(
                "tk",
                "+690",
                "Tokelau",
                R.drawable.tk,
            ),
            Country(
                "tl",
                "+670",
                "Timor-leste",
                R.drawable.tl,
            ),
            Country(
                "tm",
                "+993",
                "Turkmenistan",
                R.drawable.tm,
            ),
            Country(
                "tn",
                "+216",
                "Tunisia",
                R.drawable.tn,
            ),
            Country(
                "to",
                "+676",
                "Tonga",
                R.drawable.to,
            ),
            Country(
                "tr",
                "+90",
                "Turkey",
                R.drawable.tr,
            ),
            Country(
                "tt",
                "+1",
                "Trinidad &amp; Tobago",
                R.drawable.tt,
            ),
            Country(
                "tv",
                "+688",
                "Tuvalu",
                R.drawable.tv,
            ),
            Country(
                "tw",
                "+886",
                "Taiwan",
                R.drawable.tw,
            ),
            Country(
                "tz",
                "+255",
                "Tanzania, United Republic Of",
                R.drawable.tz,
            ),
            Country(
                "ua",
                "+380",
                "Ukraine",
                R.drawable.ua,
            ),
            Country(
                "ug",
                "+256",
                "Uganda",
                R.drawable.ug,
            ),
            Country(
                "us",
                "+1",
                "United States",
                R.drawable.us,
            ),
            Country(
                "uy",
                "+598",
                "Uruguay",
                R.drawable.uy,
            ),
            Country(
                "uz",
                "+998",
                "Uzbekistan",
                R.drawable.uz,
            ),
            Country(
                "va",
                "+379",
                "Holy See (vatican City State)",
                R.drawable.va,
            ),
            Country(
                "vc",
                "+1",
                "Saint Vincent &amp; The Grenadines",
                R.drawable.vc,
            ),
            Country(
                "ve",
                "+58",
                "Venezuela, Bolivarian Republic Of",
                R.drawable.ve,
            ),
            Country(
                "vg",
                "+1",
                "British Virgin Islands",
                R.drawable.vg,
            ),
            Country(
                "vi",
                "+1",
                "US Virgin Islands",
                R.drawable.vi,
            ),
            Country(
                "vn",
                "+84",
                "Vietnam",
                R.drawable.vn,
            ),
            Country(
                "vu",
                "+678",
                "Vanuatu",
                R.drawable.vu,
            ),
            Country(
                "wf",
                "+681",
                "Wallis And Futuna",
                R.drawable.wf,
            ),
            Country(
                "ws",
                "4685",
                "Samoa",
                R.drawable.ws,
            ),
            Country(
                "xk",
                "+383",
                "Kosovo",
                R.drawable.xk,
            ),
            Country(
                "ye",
                "+967",
                "Yemen",
                R.drawable.ye,
            ),
            Country(
                "yt",
                "+262",
                "Mayotte",
                R.drawable.yt,
            ),
            Country(
                "za",
                "+27",
                "South Africa",
                R.drawable.za,
            ),
            Country(
                "zm",
                "+260",
                "Zambia",
                R.drawable.zm,
            ),
            Country(
                "zw",
                "+263",
                "Zimbabwe",
                R.drawable.zw,
            ),
        ).sortedBy { it.name }

    /**
     * [getNumberHint] Returns the hint of the country.
     * [countryName] The name of the country.
     */
    fun getNumberHint(countryName: String): Int {
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

    /*
    Country(
            code = "ad",
            name = R.string.andorra,
            phoneNoCode = "+376",
            flag = R.drawable.ad,
            phoneNoHint = R.string.andorra_hint
        )
     */
    /*private fun Context.allCountries(): List<Country> {
        return listOf(
            Country(
                code = "af",
                name = getString(R.string.afghanistan),
                phoneNoCode = "+93",
                flag = R.drawable.af,
                phoneNoHint = getString(R.string.afganistan_hint)
            ),
            Country(
                code = "al",
                name = getString(R.string.albania),
                phoneNoCode = "+355",
                flag = R.drawable.al,
                phoneNoHint = getString(R.string.albania_hint)
            ),
            Country(
                code = "dz",
                name = getString(R.string.algeria),
                phoneNoCode = "+213",
                flag = R.drawable.dz,
                phoneNoHint = getString(R.string.algeria_hint)
            ),
            Country(
                code = "as",
                name = getString(R.string.american_samoa),
                phoneNoCode = "+1",
                flag = R.drawable.`as`,
                phoneNoHint = getString(R.string.american_samoa_hint)
            ),
            Country(
                code = "ad",
                name = getString(R.string.andorra),
                phoneNoCode = "+376",
                flag = R.drawable.ad,
                phoneNoHint = getString(R.string.andorra_hint)
            ),
            Country(
                code = "ao",
                name = getString(R.string.angola),
                phoneNoCode = "+244",
                flag = R.drawable.ao,
                phoneNoHint = getString(R.string.angola_hint)
            ),
            Country(
                code = "ai",
                name = getString(R.string.anguilla),
                phoneNoCode = "+1",
                flag = R.drawable.ai,
                phoneNoHint = getString(R.string.anguilla_hint)
            ),
            Country(
                code = "ag",
                name = getString(R.string.antigua_and_barbuda),
                phoneNoCode = "+1",
                flag = R.drawable.ag,
                phoneNoHint = getString(R.string.antigua_and_barbuda_hint)
            ),
            Country(
                code = "ar",
                name = getString(R.string.argentina),
                phoneNoCode = "+54",
                flag = R.drawable.ar,
                phoneNoHint = getString(R.string.argentina_hint)
            ),
            Country(
                code = "am",
                name = getString(R.string.armenia),
                phoneNoCode = "+374",
                flag = R.drawable.am,
                phoneNoHint = getString(R.string.armenia_hint)
            ),
            Country(
                code = "aw",
                name = getString(R.string.aruba),
                phoneNoCode = "+297",
                flag = R.drawable.aw,
                phoneNoHint = getString(R.string.aruba_hint)
            ),
            Country(
                code = "au",
                name = getString(R.string.australia),
                phoneNoCode = "+61",
                flag = R.drawable.au,
                phoneNoHint = getString(R.string.australia_hint)
            ),
            Country(
                code = "at",
                name = getString(R.string.austria),
                phoneNoCode = "+43",
                flag = R.drawable.at,
                phoneNoHint = getString(R.string.austria_hint)
            ),
            Country(
                code = "az",
                name = getString(R.string.azerbaijan),
                phoneNoCode = "+994",
                flag = R.drawable.az,
                phoneNoHint = getString(R.string.azerbaijan_hint)
            ),
            Country(
                code = "bs",
                name = getString(R.string.bahamas),
                phoneNoCode = "+1",
                flag = R.drawable.bs,
                phoneNoHint = getString(R.string.bahamas_hint)
            ),
            Country(
                code = "bh",
                name = getString(R.string.bahrain),
                phoneNoCode = "+973",
                flag = R.drawable.bh,
                phoneNoHint = getString(R.string.bahrain_hint)
            ),
            Country(
                code = "bd",
                name = getString(R.string.bangladesh),
                phoneNoCode = "+880",
                flag = R.drawable.bd,
                phoneNoHint = getString(R.string.bangladesh_hint)
            ),
            Country(
                code = "bb",
                name = getString(R.string.barbados),
                phoneNoCode = "+1",
                flag = R.drawable.bb,
                phoneNoHint = getString(R.string.barbados_hint)
            ),
            Country(
                code = "by",
                name = getString(R.string.belarus),
                phoneNoCode = "+375",
                flag = R.drawable.by,
                phoneNoHint = getString(R.string.belarus_hint)
            ),
            Country(
                code = "be",
                name = getString(R.string.belgium),
                phoneNoCode = "+32",
                flag = R.drawable.be,
                phoneNoHint = getString(R.string.belgium_hint)
            ),
            Country(
                code = "bz",
                name = getString(R.string.belize),
                phoneNoCode = "+501",
                flag = R.drawable.bz,
                phoneNoHint = getString(R.string.belize_hint)
            ),
            Country(
                code = "bj",
                name = getString(R.string.benin),
                phoneNoCode = "+229",
                flag = R.drawable.bj,
                phoneNoHint = getString(R.string.benin_hint)
            ),
            Country(
                code = "bm",
                name = getString(R.string.bermuda),
                phoneNoCode = "+1",
                flag = R.drawable.bm,
                phoneNoHint = getString(R.string.bermuda_hint)
            ),
            Country(
                code = "bt",
                name = getString(R.string.bhutan),
                phoneNoCode = "+975",
                flag = R.drawable.bt,
                phoneNoHint = getString(R.string.bhutan_hint)
            ),
            Country(
                code = "bo",
                name = getString(R.string.bolivia),
                phoneNoCode = "+591",
                flag = R.drawable.bo,
                phoneNoHint = getString(R.string.bolivia_hint)
            ),
            Country(
                code = "ba",
                name = getString(R.string.bosnia),
                phoneNoCode = "+387",
                flag = R.drawable.ba,
                phoneNoHint = getString(R.string.bosnia_hint)
            ),
            Country(
                code = "bw",
                name = getString(R.string.botswana),
                phoneNoCode = "+267",
                flag = R.drawable.bw,
                phoneNoHint = getString(R.string.botswana_hint)
            ),
            Country(
                code = "br",
                name = getString(R.string.brazil),
                phoneNoCode = "+55",
                flag = R.drawable.br,
                phoneNoHint = getString(R.string.brazil_hint)
            ),
            Country(
                code = "bn",
                name = getString(R.string.brunei_darussalam),
                phoneNoCode = "+673",
                flag = R.drawable.bn,
                phoneNoHint = getString(R.string.brunei_darussalam_hint)
            ),
            Country(
                code = "bg",
                name = getString(R.string.bulgaria),
                phoneNoCode = "+359",
                flag = R.drawable.bg,
                phoneNoHint = getString(R.string.bulgaria_hint)
            ),
            Country(
                code = "bf",
                name = getString(R.string.burkina_faso),
                phoneNoCode = "+226",
                flag = R.drawable.bf,
                phoneNoHint = getString(R.string.burkina_faso_hint)
            ),
            Country(
                code = "bi",
                name = getString(R.string.burundi),
                phoneNoCode = "+257",
                flag = R.drawable.bi,
                phoneNoHint = getString(R.string.burundi_hint)
            ),
            Country(
                code = "kh",
                name = getString(R.string.cambodia),
                phoneNoCode = "+855",
                flag = R.drawable.kh,
                phoneNoHint = getString(R.string.cambodia_hint)
            ),
            Country(
                code = "cm",
                name = getString(R.string.cameroon),
                phoneNoCode = "+237",
                flag = R.drawable.cm,
                phoneNoHint = getString(R.string.cameroon_hint)
            ),
            Country(
                code = "ca",
                name = getString(R.string.canada),
                phoneNoCode = "+1",
                flag = R.drawable.ca,
                phoneNoHint = getString(R.string.canada_hint)
            ),
            Country(
                code = "cv",
                name = getString(R.string.cape_verde),
                phoneNoCode = "+238",
                flag = R.drawable.cv,
                phoneNoHint = getString(R.string.cape_verde_hint)
            ),
            Country(
                code = "ky",
                name = getString(R.string.cayman_islands),
                phoneNoCode = "+1",
                flag = R.drawable.ky,
                phoneNoHint = getString(R.string.cayman_islands_hint)
            ),
            Country(
                code = "cf",
                name = getString(R.string.central_african),
                phoneNoCode = "+236",
                flag = R.drawable.cf,
                phoneNoHint = getString(R.string.central_african_hint)
            ),
            Country(
                code = "td",
                name = getString(R.string.chad),
                phoneNoCode = "+235",
                flag = R.drawable.td,
                phoneNoHint = getString(R.string.chad_hint)
            ),
            Country(
                code = "cl",
                name = getString(R.string.chile),
                phoneNoCode = "+56",
                flag = R.drawable.cl,
                phoneNoHint = getString(R.string.chile_hint)
            ),
            Country(
                code = "cn",
                name = getString(R.string.china),
                phoneNoCode = "+86",
                flag = R.drawable.cn,
                phoneNoHint = getString(R.string.china_hint)
            ),
            Country(
                code = "co",
                name = getString(R.string.colombia),
                phoneNoCode = "+57",
                flag = R.drawable.co,
                phoneNoHint = getString(R.string.colombia_hint)
            ),
            Country(
                code = "km",
                name = getString(R.string.comoros),
                phoneNoCode = "+269",
                flag = R.drawable.km,
                phoneNoHint = getString(R.string.comoros_hint)
            ),
            Country(
                code = "cg",
                name = getString(R.string.congo),
                phoneNoCode = "+242",
                flag = R.drawable.cg,
                phoneNoHint = getString(R.string.congo)
            ),
            Country(
                code = "cd",
                name = getString(R.string.congo_democratic),
                phoneNoCode = "+243",
                flag = R.drawable.cd,
                phoneNoHint = getString(R.string.congo_democratic_hint)
            ),
            Country(
                code = "cr",
                name = getString(R.string.costa_rica),
                phoneNoCode = "+506",
                flag = R.drawable.cr,
                phoneNoHint = getString(R.string.costa_rica_hint)
            ),
            Country(
                code = "ci",
                name = getString(R.string.cote_dlvoire),
                phoneNoCode = "+225",
                flag = R.drawable.ci,
                phoneNoHint = getString(R.string.cote_dlvoire_hint)
            ),
            Country(
                code = "hr",
                name = getString(R.string.croatia),
                phoneNoCode = "+385",
                flag = R.drawable.hr,
                phoneNoHint = getString(R.string.croatia_hint)
            ),
            Country(
                code = "cu",
                name = getString(R.string.cuba),
                phoneNoCode = "+53",
                flag = R.drawable.cu,
                phoneNoHint = getString(R.string.cuba_hint)
            ),
            Country(
                code = "cy",
                name = getString(R.string.cyprus),
                phoneNoCode = "+357",
                flag = R.drawable.cy,
                phoneNoHint = getString(R.string.cyprus_hint)
            ),
            Country(
                code = "cz",
                name = getString(R.string.czech_republic),
                phoneNoCode = "+420",
                flag = R.drawable.cz,
                phoneNoHint = getString(R.string.czech_republic_hint)
            ),
            Country(
                code = "dk",
                name = getString(R.string.denmark),
                phoneNoCode = "+45",
                flag = R.drawable.dk,
                phoneNoHint = getString(R.string.denmark_hint)
            ),
            Country(
                code = "dj",
                name = getString(R.string.djibouti),
                phoneNoCode = "+253",
                flag = R.drawable.dj,
                phoneNoHint = getString(R.string.djibouti_hint)
            ),
            Country(
                code = "dm",
                name = getString(R.string.dominica),
                phoneNoCode = "+1",
                flag = R.drawable.dm,
                phoneNoHint = getString(R.string.dominica_hint)
            ),
            Country(
                code = "do",
                name = getString(R.string.dominician_republic),
                phoneNoCode = "+1",
                flag = R.drawable.ic_do,
                phoneNoHint = getString(R.string.dominician_republic_hint)
            ),
            Country(
                code = "ec",
                name = getString(R.string.ecuador),
                phoneNoCode = "+593",
                flag = R.drawable.ec,
                phoneNoHint = getString(R.string.ecuador_hint)
            ),
            Country(
                code = "eg",
                name = getString(R.string.egypt),
                phoneNoCode = "+20",
                flag = R.drawable.eg,
                phoneNoHint = getString(R.string.egypt_hint)
            ),
            Country(
                code = "sv",
                name = getString(R.string.el_salvador),
                phoneNoCode = "+503",
                flag = R.drawable.sv,
                phoneNoHint = getString(R.string.el_salvador_hint)
            ),
            Country(
                code = "gq",
                name = getString(R.string.equatorial_guinea),
                phoneNoCode = "+240",
                flag = R.drawable.gq,
                phoneNoHint = getString(R.string.equatorial_guinea_hint)
            ),
            Country(
                code = "er",
                name = getString(R.string.eritrea),
                phoneNoCode = "+291",
                flag = R.drawable.er,
                phoneNoHint = getString(R.string.eritrea_hint)
            ),
            Country(
                code = "ee",
                name = getString(R.string.estonia),
                phoneNoCode = "+372",
                flag = R.drawable.ee,
                phoneNoHint = getString(R.string.estonia_hint)
            ),
            Country(
                code = "et",
                name = getString(R.string.ethiopia),
                phoneNoCode = "+251",
                flag = R.drawable.et,
                phoneNoHint = getString(R.string.ethiopia_hint)
            ),
            Country(
                code = "fj",
                name = getString(R.string.fiji),
                phoneNoCode = "+679",
                flag = R.drawable.fj,
                phoneNoHint = getString(R.string.fiji_hint)
            ),
            Country(
                code = "fi",
                name = getString(R.string.finland),
                phoneNoCode = "+358",
                flag = R.drawable.fi,
                phoneNoHint = getString(R.string.finland_hint)
            ),
            Country(
                code = "fr",
                name = getString(R.string.france),
                phoneNoCode = "+33",
                flag = R.drawable.fr,
                phoneNoHint = getString(R.string.france_hint)
            ),
            Country(
                code = "ga",
                name = getString(R.string.gabon),
                phoneNoCode = "+241",
                flag = R.drawable.ga,
                phoneNoHint = getString(R.string.gabon_hint)
            ),
            Country(
                code = "gm",
                name = getString(R.string.gambia),
                phoneNoCode = "+220",
                flag = R.drawable.gm,
                phoneNoHint = getString(R.string.gambia_hint)
            ),
            Country(
                code = "ge",
                name = getString(R.string.georgia),
                phoneNoCode = "+995",
                flag = R.drawable.ge,
                phoneNoHint = getString(R.string.georgia_hint)
            ),
            Country(
                code = "de",
                name = getString(R.string.germany),
                phoneNoCode = "+49",
                flag = R.drawable.de,
                phoneNoHint = getString(R.string.germany_hint)
            ),
            Country(
                code = "gh",
                name = getString(R.string.ghana),
                phoneNoCode = "+233",
                flag = R.drawable.gh,
                phoneNoHint = getString(R.string.ghana_hint)
            ),
            Country(
                code = "gr",
                name = getString(R.string.greece),
                phoneNoCode = "+30",
                flag = R.drawable.gr,
                phoneNoHint = getString(R.string.greece_hint)
            ),
            Country(
                code = "gd",
                name = getString(R.string.grenada),
                phoneNoCode = "+1",
                flag = R.drawable.gd,
                phoneNoHint = getString(R.string.grenada_hint)
            ),
            Country(
                code = "gt",
                name = getString(R.string.guatemala),
                phoneNoCode = "+502",
                flag = R.drawable.gt,
                phoneNoHint = getString(R.string.guatemala_hint)
            ),
            Country(
                code = "gn",
                name = getString(R.string.guinea),
                phoneNoCode = "+224",
                flag = R.drawable.gn,
                phoneNoHint = getString(R.string.guinea_hint)
            ),
            Country(
                code = "gw",
                name = getString(R.string.guinea_bissau),
                phoneNoCode = "+245",
                flag = R.drawable.gw,
                phoneNoHint = getString(R.string.guinea_bissau_hint)
            ),
            Country(
                code = "gy",
                name = getString(R.string.guyana),
                phoneNoCode = "+592",
                flag = R.drawable.gy,
                phoneNoHint = getString(R.string.guyana_hint)
            ),
            Country(
                code = "ht",
                name = getString(R.string.haiti),
                phoneNoCode = "+509",
                flag = R.drawable.ht,
                phoneNoHint = getString(R.string.haiti_hint)
            ),
            Country(
                code = "hn",
                name = getString(R.string.honduras),
                phoneNoCode = "+504",
                flag = R.drawable.hn,
                phoneNoHint = getString(R.string.honduras_hint)
            ),
            Country(
                code = "hk",
                name = getString(R.string.hong_kong),
                phoneNoCode = "+852",
                flag = R.drawable.hk,
                phoneNoHint = getString(R.string.hong_kong_hint)
            ),
            Country(
                code = "hu",
                name = getString(R.string.hungary),
                phoneNoCode = "+36",
                flag = R.drawable.hu,
                phoneNoHint = getString(R.string.hungary_hint)
            ),
            Country(
                code = "is",
                name = getString(R.string.iceland),
                phoneNoCode = "+354",
                flag = R.drawable.`is`,
                phoneNoHint = getString(R.string.iceland)
            ),
            Country(
                code = "in",
                name = getString(R.string.india),
                phoneNoCode = "+91",
                flag = R.drawable.`in`,
                phoneNoHint = getString(R.string.india_hint)
            ),
            Country(
                code = "id",
                name = getString(R.string.indonesia),
                phoneNoCode = "+62",
                flag = R.drawable.id,
                phoneNoHint = getString(R.string.indonesia_hint)
            ),
            Country(
                code = "ir",
                name = getString(R.string.iran),
                phoneNoCode = "+98",
                flag = R.drawable.ir,
                phoneNoHint = getString(R.string.iran_hint)
            ),
            Country(
                code = "iq",
                name = getString(R.string.iraq),
                phoneNoCode = "+964",
                flag = R.drawable.iq,
                phoneNoHint = getString(R.string.iraq_hint)
            ),
            Country(
                code = "ie",
                name = getString(R.string.ireland),
                phoneNoCode = "+353",
                flag = R.drawable.ie,
                phoneNoHint = getString(R.string.ireland_hint)
            ),
            Country(
                code = "il",
                name = getString(R.string.israil),
                phoneNoCode = "+972",
                flag = R.drawable.il,
                phoneNoHint = getString(R.string.israil_hint)
            ),
            Country(
                code = "it",
                name = getString(R.string.italia),
                phoneNoCode = "+39",
                flag = R.drawable.it,
                phoneNoHint = getString(R.string.italia_hint)
            ),
            Country(
                code = "jm",
                name = getString(R.string.jamaica),
                phoneNoCode = "+1",
                flag = R.drawable.jm,
                phoneNoHint = getString(R.string.jamaica_hint)
            ),
            Country(
                code = "jp",
                name = getString(R.string.japan),
                phoneNoCode = "+81",
                flag = R.drawable.jp,
                phoneNoHint = getString(R.string.japan_hint)
            ),
            Country(
                code = "jo",
                name = getString(R.string.jordan),
                phoneNoCode = "+962",
                flag = R.drawable.jo,
                phoneNoHint = getString(R.string.jordan_hint)
            ),
            Country(
                code = "kz",
                name = getString(R.string.kazakhstan),
                phoneNoCode = "+7",
                flag = R.drawable.kz,
                phoneNoHint = getString(R.string.kazakhstan_hint)
            ),
            Country(
                code = "ke",
                name = getString(R.string.kenya),
                phoneNoCode = "+254",
                flag = R.drawable.ke,
                phoneNoHint = getString(R.string.kenya_hint)
            ),
            Country(
                code = "ki",
                name = getString(R.string.kiribati),
                phoneNoCode = "+686",
                flag = R.drawable.ki,
                phoneNoHint = getString(R.string.kiribati)
            ),
            Country(
                code = "kp",
                name = getString(R.string.north_korea),
                phoneNoCode = "+850",
                flag = R.drawable.kp,
                phoneNoHint = getString(R.string.north_korea_hint)
            ),
            Country(
                code = "kr",
                name = getString(R.string.south_korea),
                phoneNoCode = "+82",
                flag = R.drawable.kr,
                phoneNoHint = getString(R.string.south_korea_hint)
            ),
            Country(
                code = "kw",
                name = getString(R.string.kuwait),
                phoneNoCode = "+965",
                flag = R.drawable.kw,
                phoneNoHint = getString(R.string.kuwait_hint)
            ),
            Country(
                code = "kg",
                name = getString(R.string.kyrgyzstan),
                phoneNoCode = "+996",
                flag = R.drawable.kg,
                phoneNoHint = getString(R.string.kyrgyzstan_hint)
            ),
            Country(
                code = "la",
                name = getString(R.string.laos),
                phoneNoCode = "+856",
                flag = R.drawable.la,
                phoneNoHint = getString(R.string.laos_hint)
            ),
            Country(
                code = "lv",
                name = getString(R.string.latvia),
                phoneNoCode = "+371",
                flag = R.drawable.lv,
                phoneNoHint = getString(R.string.latvia_hint)
            ),
            Country(
                code = "lb",
                name = getString(R.string.lebanon),
                phoneNoCode = "+961",
                flag = R.drawable.lb,
                phoneNoHint = getString(R.string.lebanon_hint)
            ),
            Country(
                code = "ls",
                name = getString(R.string.lesotho),
                phoneNoCode = "+266",
                flag = R.drawable.ls,
                phoneNoHint = getString(R.string.lesotho_hint)
            ),
            Country(
                code = "lr",
                name = getString(R.string.liberia),
                phoneNoCode = "+231",
                flag = R.drawable.lr,
                phoneNoHint = getString(R.string.liberia_hint)
            ),
            Country(
                code = "ly",
                name = getString(R.string.libya),
                phoneNoCode = "+218",
                flag = R.drawable.ly,
                phoneNoHint = getString(R.string.libya_hint)
            ),
            Country(
                code = "li",
                name = getString(R.string.liechtenstein),
                phoneNoCode = "+423",
                flag = R.drawable.li,
                phoneNoHint = getString(R.string.liechtenstein)
            ),
            Country(
                code = "lt",
                name = getString(R.string.lithuania),
                phoneNoCode = "+370",
                flag = R.drawable.lt,
                phoneNoHint = getString(R.string.lithuania_hint)
            ),
            Country(
                code = "lu",
                name = getString(R.string.luxembourg),
                phoneNoCode = "+352",
                flag = R.drawable.lu,
                phoneNoHint = getString(R.string.luxembourg_hint)
            ),
            Country(
                code = "mo",
                name = getString(R.string.macau),
                phoneNoCode = "+853",
                flag = R.drawable.mo,
                phoneNoHint = getString(R.string.macau_hint)
            ),
            Country(
                code = "mk",
                name = getString(R.string.north_macedonia),
                phoneNoCode = "+389",
                flag = R.drawable.mk,
                phoneNoHint = getString(R.string.north_macedonia_hint)
            ),
            Country(
                code = "mg",
                name = getString(R.string.madagascar),
                phoneNoCode = "+261",
                flag = R.drawable.mg,
                phoneNoHint = getString(R.string.madagascar_hint)
            ),
            Country(
                code = "mw",
                name = getString(R.string.malawi),
                phoneNoCode = "+265",
                flag = R.drawable.mw,
                phoneNoHint = getString(R.string.malawi_hint)
            ),
            Country(
                code = "my",
                name = getString(R.string.malaysia),
                phoneNoCode = "+60",
                flag = R.drawable.my,
                phoneNoHint = getString(R.string.malaysia_hint)
            ),
            Country(
                code = "mv",
                name = getString(R.string.maldives),
                phoneNoCode = "+960",
                flag = R.drawable.mv,
                phoneNoHint = getString(R.string.maldives_hint)
            ),
            Country(
                code = "ml",
                name = getString(R.string.mali),
                phoneNoCode = "+223",
                flag = R.drawable.ml,
                phoneNoHint = getString(R.string.mali_hint)
            ),
            Country(
                code = "mt",
                name = getString(R.string.malta),
                phoneNoCode = "+356",
                flag = R.drawable.mt,
                phoneNoHint = getString(R.string.malta_hint)
            ),
            Country(
                code = "mh",
                name = getString(R.string.marshall_islands),
                phoneNoCode = "+692",
                flag = R.drawable.mh,
                phoneNoHint = getString(R.string.marshall_islands_hint)
            ),
            Country(
                code = "mr",
                name = getString(R.string.mauriatana),
                phoneNoCode = "+222",
                flag = R.drawable.mr,
                phoneNoHint = getString(R.string.mauriatana_hint)
            ),
            Country(
                code = "mu",
                name = getString(R.string.mauritius),
                phoneNoCode = "+230",
                flag = R.drawable.mu,
                phoneNoHint = getString(R.string.mauritius_hint)
            ),
            Country(
                code = "mx",
                name = getString(R.string.mexico),
                phoneNoCode = "+52",
                flag = R.drawable.mx,
                phoneNoHint = getString(R.string.mexico_hint)
            ),
            Country(
                code = "fm",
                name = getString(R.string.micro),
                phoneNoCode = "+691",
                flag = R.drawable.fm,
                phoneNoHint = getString(R.string.micro_hint)
            ),
            Country(
                code = "md",
                name = getString(R.string.moldova),
                phoneNoCode = "+373",
                flag = R.drawable.md,
                phoneNoHint = getString(R.string.moldova_hint)
            ),
            Country(
                code = "mc",
                name = getString(R.string.monaco),
                phoneNoCode = "+377",
                flag = R.drawable.mc,
                phoneNoHint = getString(R.string.monaco_hint)
            ),
            Country(
                code = "mn",
                name = getString(R.string.mongolia),
                phoneNoCode = "+976",
                flag = R.drawable.mn,
                phoneNoHint = getString(R.string.mongolia_hint)
            ),
            Country(
                code = "me",
                name = getString(R.string.montenegro),
                phoneNoCode = "+382",
                flag = R.drawable.me,
                phoneNoHint = getString(R.string.montenegro_hint)
            ),
            Country(
                code = "ma",
                name = getString(R.string.marocco),
                phoneNoCode = "+212",
                flag = R.drawable.ma,
                phoneNoHint = getString(R.string.marocco_hint)
            ),
            Country(
                code = "mz",
                name = getString(R.string.mozambique),
                phoneNoCode = "+258",
                flag = R.drawable.mz,
                phoneNoHint = getString(R.string.mozambique_hint)
            ),
            Country(
                code = "mm",
                name = getString(R.string.myanmar),
                phoneNoCode = "+95",
                flag = R.drawable.mm,
                phoneNoHint = getString(R.string.myanmar_hint)
            ),
            Country(
                code = "na",
                name = getString(R.string.namibia),
                phoneNoCode = "+264",
                flag = R.drawable.na,
                phoneNoHint = getString(R.string.namibia_hint)
            ),
            Country(
                code = "nr",
                name = getString(R.string.nauru),
                phoneNoCode = "+674",
                flag = R.drawable.nr,
                phoneNoHint = getString(R.string.nauru_hint)
            ),
            Country(
                code = "np",
                name = getString(R.string.nepal),
                phoneNoCode = "+977",
                flag = R.drawable.np,
                phoneNoHint = getString(R.string.nepal_hint)
            ),
            Country(
                code = "nl",
                name = getString(R.string.netherlands),
                phoneNoCode = "+31",
                flag = R.drawable.nl,
                phoneNoHint = getString(R.string.netherlands_hint)
            ),
            Country(
                code = "nz",
                name = getString(R.string.new_zealand),
                phoneNoCode = "+64",
                flag = R.drawable.nz,
                phoneNoHint = getString(R.string.new_zealand_hint)
            ),
            Country(
                code = "ni",
                name = getString(R.string.nicaragua),
                phoneNoCode = "+505",
                flag = R.drawable.ni,
                phoneNoHint = getString(R.string.dominica_hint)
            ),
            Country(
                code = "ne",
                name = getString(R.string.niger),
                phoneNoCode = "+227",
                flag = R.drawable.ne,
                phoneNoHint = getString(R.string.niger_hint)
            ),
            Country(
                code = "ng",
                name = getString(R.string.nigeria),
                phoneNoCode = "+234",
                flag = R.drawable.ng,
                phoneNoHint = getString(R.string.nigeria_hint)
            ),
            Country(
                code = "no",
                name = getString(R.string.norway),
                phoneNoCode = "+47",
                flag = R.drawable.no,
                phoneNoHint = getString(R.string.norway_hint)
            ),
            Country(
                code = "om",
                name = getString(R.string.oman),
                phoneNoCode = "+968",
                flag = R.drawable.om,
                phoneNoHint = getString(R.string.oman_hint)
            ),
            Country(
                code = "pk",
                name = getString(R.string.pakistan),
                phoneNoCode = "+92",
                flag = R.drawable.pk,
                phoneNoHint = getString(R.string.pakistan_hint)
            ),
            Country(
                code = "pw",
                name = getString(R.string.palau),
                phoneNoCode = "+680",
                flag = R.drawable.pw,
                phoneNoHint = getString(R.string.palau_hint)
            ),
            Country(
                code = "pa",
                name = getString(R.string.panama),
                phoneNoCode = "+507",
                flag = R.drawable.pa,
                phoneNoHint = getString(R.string.panama_hint)
            ),
            Country(
                code = "pg",
                name = getString(R.string.papua_new_guinea),
                phoneNoCode = "+675",
                flag = R.drawable.pg,
                phoneNoHint = getString(R.string.papua_new_guinea_hint)
            ),
            Country(
                code = "py",
                name = getString(R.string.paraguay),
                phoneNoCode = "+595",
                flag = R.drawable.py,
                phoneNoHint = getString(R.string.paraguay_hint)
            ),
            Country(
                code = "pe",
                name = getString(R.string.peru),
                phoneNoCode = "+51",
                flag = R.drawable.pe,
                phoneNoHint = getString(R.string.peru_hint)
            ),
            Country(
                code = "ph",
                name = getString(R.string.philippinies),
                phoneNoCode = "+63",
                flag = R.drawable.ph,
                phoneNoHint = getString(R.string.philippinies_hint)
            ),
            Country(
                code = "pl",
                name = getString(R.string.poland),
                phoneNoCode = "+48",
                flag = R.drawable.pl,
                phoneNoHint = getString(R.string.poland_hint)
            ),
            Country(
                code = "pt",
                name = getString(R.string.portugal),
                phoneNoCode = "+351",
                flag = R.drawable.pt,
                phoneNoHint = getString(R.string.portugal_hint)
            ),
            Country(
                code = "qa",
                name = getString(R.string.qatar),
                phoneNoCode = "+974",
                flag = R.drawable.qa,
                phoneNoHint = getString(R.string.qatar_hint)
            ),
            Country(
                code = "ro",
                name = getString(R.string.romania),
                phoneNoCode = "+40",
                flag = R.drawable.ro,
                phoneNoHint = getString(R.string.romania_hint)
            ),
            Country(
                code = "ru",
                name = getString(R.string.russia),
                phoneNoCode = "+7",
                flag = R.drawable.ru,
                phoneNoHint = getString(R.string.russia_hint)
            ),
            Country(
                code = "rw",
                name = getString(R.string.rwanda),
                phoneNoCode = "+250",
                flag = R.drawable.rw,
                phoneNoHint = getString(R.string.rwanda_hint)
            ),
            Country(
                code = "kn",
                name = getString(R.string.saint_kitts),
                phoneNoCode = "+1",
                flag = R.drawable.kn,
                phoneNoHint = getString(R.string.saint_kitts_hint)
            ),
            Country(
                code = "lc",
                name = getString(R.string.saint_lucia),
                phoneNoCode = "+1",
                flag = R.drawable.lc,
                phoneNoHint = getString(R.string.saint_lucia_hint)
            ),
            Country(
                code = "vc",
                name = getString(R.string.saint_vincent),
                phoneNoCode = "+1",
                flag = R.drawable.vc,
                phoneNoHint = getString(R.string.saint_vincent_hint)
            ),
            Country(
                code = "ws",
                name = getString(R.string.samoa),
                phoneNoCode = "+685",
                flag = R.drawable.ws,
                phoneNoHint = getString(R.string.samoa_hint)
            ),
            Country(
                code = "sm",
                name = getString(R.string.san_marino),
                phoneNoCode = "+378",
                flag = R.drawable.sm,
                phoneNoHint = getString(R.string.san_marino_hint)
            ),
            Country(
                code = "st",
                name = getString(R.string.sao_tome),
                phoneNoCode = "+239",
                flag = R.drawable.st,
                phoneNoHint = getString(R.string.sao_tome_hint)
            ),
            Country(
                code = "sa",
                name = getString(R.string.saudi_arabia),
                phoneNoCode = "+966",
                flag = R.drawable.sa,
                phoneNoHint = getString(R.string.saudi_arabia_hint)
            ),
            Country(
                code = "sn",
                name = getString(R.string.senegal),
                phoneNoCode = "+221",
                flag = R.drawable.sn,
                phoneNoHint = getString(R.string.senegal_hint)
            ),
            Country(
                code = "rs",
                name = getString(R.string.serbia),
                phoneNoCode = "+381",
                flag = R.drawable.rs,
                phoneNoHint = getString(R.string.serbia_hint)
            ),
            Country(
                code = "sc",
                name = getString(R.string.seychelles),
                phoneNoCode = "+248",
                flag = R.drawable.sc,
                phoneNoHint = getString(R.string.seychelles_hint)
            ),
            Country(
                code = "sl",
                name = getString(R.string.sierra_leone),
                phoneNoCode = "+232",
                flag = R.drawable.sl,
                phoneNoHint = getString(R.string.sierra_leone_hint)
            ),
            Country(
                code = "sg",
                name = getString(R.string.singapore),
                phoneNoCode = "+65",
                flag = R.drawable.sg,
                phoneNoHint = getString(R.string.singapore_hint)
            ),
            Country(
                code = "sk",
                name = getString(R.string.slovakia),
                phoneNoCode = "+421",
                flag = R.drawable.sk,
                phoneNoHint = getString(R.string.slovakia_hint)
            ),
            Country(
                code = "si",
                name = getString(R.string.slovenia),
                phoneNoCode = "+386",
                flag = R.drawable.si,
                phoneNoHint = getString(R.string.slovenia_hint)
            ),
            Country(
                code = "sb",
                name = getString(R.string.solomon_islands),
                phoneNoCode = "+677",
                flag = R.drawable.sb,
                phoneNoHint = getString(R.string.solomon_islands_hint)
            ),
            Country(
                code = "so",
                name = getString(R.string.somali),
                phoneNoCode = "+252",
                flag = R.drawable.so,
                phoneNoHint = getString(R.string.somali_hint)
            ),
            Country(
                code = "za",
                name = getString(R.string.south_africa),
                phoneNoCode = "+27",
                flag = R.drawable.za,
                phoneNoHint = getString(R.string.south_africa_hint)
            ),
            Country(
                code = "es",
                name = getString(R.string.spain),
                phoneNoCode = "+34",
                flag = R.drawable.es,
                phoneNoHint = getString(R.string.spain_hint)
            ),
            Country(
                code = "lk",
                name = getString(R.string.siri_lanka),
                phoneNoCode = "+94",
                flag = R.drawable.lk,
                phoneNoHint = getString(R.string.siri_lanka_hint)
            ),
            Country(
                code = "sd",
                name = getString(R.string.sudan),
                phoneNoCode = "+249",
                flag = R.drawable.sd,
                phoneNoHint = getString(R.string.sudan_hint)
            ),
            Country(
                code = "sr",
                name = getString(R.string.suriname),
                phoneNoCode = "+597",
                flag = R.drawable.sr,
                phoneNoHint = getString(R.string.suriname_hint)
            ),
            Country(
                code = "sz",
                name = getString(R.string.swaziland),
                phoneNoCode = "+268",
                flag = R.drawable.sz,
                phoneNoHint = getString(R.string.swaziland_hint)
            ),
            Country(
                code = "se",
                name = getString(R.string.sweden),
                phoneNoCode = "+46",
                flag = R.drawable.se,
                phoneNoHint = getString(R.string.sweden_hint)
            ),
            Country(
                code = "ch",
                name = getString(R.string.switzerland),
                phoneNoCode = "+41",
                flag = R.drawable.ch,
                phoneNoHint = getString(R.string.switzerland_hint)
            ),
            Country(
                code = "sy",
                name = getString(R.string.syrian),
                phoneNoCode = "+963",
                flag = R.drawable.sy,
                phoneNoHint = getString(R.string.syrian_hint)
            ),
            Country(
                code = "tj",
                name = getString(R.string.taijikistan),
                phoneNoCode = "+992",
                flag = R.drawable.tj,
                phoneNoHint = getString(R.string.taijikistan_hint)
            ),
            Country(
                code = "tz",
                name = getString(R.string.tazmania),
                phoneNoCode = "+255",
                flag = R.drawable.tz,
                phoneNoHint = getString(R.string.tazmania_hint)
            ),
            Country(
                code = "th",
                name = getString(R.string.thailand),
                phoneNoCode = "+66",
                flag = R.drawable.th,
                phoneNoHint = getString(R.string.thailand_hint)
            ),
            Country(
                code = "tl",
                name = getString(R.string.timor_leste),
                phoneNoCode = "+670",
                flag = R.drawable.tl,
                phoneNoHint = getString(R.string.timor_leste_hint)
            ),
            Country(
                code = "tg",
                name = getString(R.string.togo),
                phoneNoCode = "+228",
                flag = R.drawable.tg,
                phoneNoHint = getString(R.string.togo_hint)
            ),
            Country(
                code = "to",
                name = getString(R.string.tonga),
                phoneNoCode = "+676",
                flag = R.drawable.to,
                phoneNoHint = getString(R.string.tonga_hint)
            ),
            Country(
                code = "tt",
                name = getString(R.string.trinidad_and_tobago),
                phoneNoCode = "+1",
                flag = R.drawable.tt,
                phoneNoHint = getString(R.string.trinidad_and_tobago_hint)
            ),
            Country(
                code = "tn",
                name = getString(R.string.tunisia),
                phoneNoCode = "+216",
                flag = R.drawable.tn,
                phoneNoHint = getString(R.string.tunisia_hint)
            ),
            Country(
                code = "tr",
                name = getString(R.string.turkey),
                phoneNoCode = "+90",
                flag = R.drawable.tr,
                phoneNoHint = getString(R.string.turkey_hint)
            ),
            Country(
                code = "tm",
                name = getString(R.string.turkmenistan),
                phoneNoCode = "+993",
                flag = R.drawable.tm,
                phoneNoHint = getString(R.string.turkmenistan_hint)
            ),
            Country(
                code = "tv",
                name = getString(R.string.tuvalu),
                phoneNoCode = "+688",
                flag = R.drawable.tv,
                phoneNoHint = getString(R.string.tuvalu_hint)
            ),
            Country(
                code = "ug",
                name = getString(R.string.uganda),
                phoneNoCode = "+256",
                flag = R.drawable.ug,
                phoneNoHint = getString(R.string.uganda_hint)
            ),
            Country(
                code = "ua",
                name = getString(R.string.ukraina),
                phoneNoCode = "+380",
                flag = R.drawable.ua,
                phoneNoHint = getString(R.string.ukraina_hint)
            ),
            Country(
                code = "ae",
                name = getString(R.string.united_arab_emirates),
                phoneNoCode = "+971",
                flag = R.drawable.ae,
                phoneNoHint = getString(R.string.united_arab_emirates_hint)
            ),
            Country(
                code = "gb",
                name = getString(R.string.united_kingdom),
                phoneNoCode = "+44",
                flag = R.drawable.gb,
                phoneNoHint = getString(R.string.united_kingdom_hint)
            ),
            Country(
                code = "us",
                name = getString(R.string.united_states_america),
                phoneNoCode = "+1",
                flag = R.drawable.us,
                phoneNoHint = getString(R.string.united_states_america_hint)
            ),
            Country(
                code = "uy",
                name = getString(R.string.uruguay),
                phoneNoCode = "+598",
                flag = R.drawable.uy,
                phoneNoHint = getString(R.string.uruguay_hint)
            ),
            Country(
                code = "uz",
                name = getString(R.string.uzbekistan),
                phoneNoCode = "+998",
                flag = R.drawable.uz,
                phoneNoHint = getString(R.string.uzbekistan_hint)
            ),
            Country(
                code = "vu",
                name = getString(R.string.vanuatu),
                phoneNoCode = "+678",
                flag = R.drawable.vu,
                phoneNoHint = getString(R.string.vanuatu_hint)
            ),
            Country(
                code = "va",
                name = getString(R.string.holy_see),
                phoneNoCode = "+379",
                flag = R.drawable.va,
                phoneNoHint = getString(R.string.holy_see)
            ),
            Country(
                code = "ve",
                name = getString(R.string.venezuela),
                phoneNoCode = "+58",
                flag = R.drawable.ve,
                phoneNoHint = getString(R.string.venezuela_hint)
            ),
            Country(
                code = "vn",
                name = getString(R.string.vietnam),
                phoneNoCode = "+84",
                flag = R.drawable.vn,
                phoneNoHint = getString(R.string.vietnam_hint)
            ),
            Country(
                code = "ye",
                name = getString(R.string.yemen),
                phoneNoCode = "+967",
                flag = R.drawable.ye,
                phoneNoHint = getString(R.string.yemen_hint)
            ),
            Country(
                code = "zm",
                name = getString(R.string.zambia),
                phoneNoCode = "+260",
                flag = R.drawable.zm,
                phoneNoHint = getString(R.string.zambia_hint)
            ),
            Country(
                code = "zw",
                name = getString(R.string.zimbabwe),
                phoneNoCode = "+263",
                flag = R.drawable.zw,
                phoneNoHint = getString(R.string.zimbabwe_hint)
            )
        )
    }*/
}
