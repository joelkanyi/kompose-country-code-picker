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

import com.joelkanyi.jcomposecountrycodepicker.data.Country
import com.joelkanyi.jcomposecountrycodepicker.resources.*
import com.joelkanyi.jcomposecountrycodepicker.resources.Res
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

internal object PickerUtils {
    /**
     * [getCountry] Returns the country of the supplied country code. if the
     * country code is empty, the default country is the United States.
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
     * [getLimitedCountries] Returns a list of countries that match the search
     * @param limitedCountries The list of countries strings.
     */
    fun getLimitedCountries(limitedCountries: List<String>): List<Country> {
        val cleanedCountries =
            limitedCountries.map { it.removeSpecialCharacters().trim().lowercase() }

        val digits = cleanedCountries.filter { it.all { char -> char.isDigit() } }
        val shortCodes =
            cleanedCountries.filter { it.all { char -> char.isLetter() } && it.length <= 2 }
        val names = cleanedCountries.filter { it.all { char -> char.isLetter() } && it.length > 2 }

        return (
            digits.flatMap { digit ->
                allCountries.filter { it.phoneNoCode.contains(digit) }
            } + shortCodes.flatMap { code ->
                allCountries.filter { it.code.lowercase() == code }
            } + names.flatMap { name ->
                allCountries.filter { it.name.lowercase().contains(name) }
            }
            ).distinct().sortedBy { it.name }
    }

    /**
     * [sortCountriesWithPriority] Sorts the countries list with the priority
     * countries at the top.
     *
     * @param countries The list of countries to be sorted.
     * @param priorityCountries The list of country codes that should be prioritized.
     * @return A sorted list of countries with the priority countries at the top.
     *
     * Please dont alter the order of the priority countries.
     */
    fun sortCountriesWithPriority(
        countries: List<Country>,
        priorityCountries: List<String>,
    ): List<Country> {
        val priorityCodesLower = priorityCountries.map { it.lowercase() }

        val priorityMap = priorityCodesLower.withIndex().associate { it.value to it.index }

        val (priority, nonPriority) = countries.partition { it.code.lowercase() in priorityMap }

        val sortedPriority = priority.sortedBy { priorityMap[it.code.lowercase()] }
        val sortedNonPriority = nonPriority.sortedBy { it.name.lowercase() }

        return sortedPriority + sortedNonPriority
    }

    /**
     * [isValid] Returns true if the phone number is valid.
     *
     * @param phoneNumberStr The phone number to be checked.
     */
    fun isValid(phoneNumberStr: String): Boolean = isPhoneNumberValid(phoneNumberStr)

    /**
     * [removeSpecialCharacters] Returns the string without special
     * characters.
     */
    fun String.removeSpecialCharacters(): String = this.replace("[^a-zA-Z0-9]".toRegex(), "")

    /**
     * [searchForAnItem] Returns a list of items that match the search string.
     *
     * @param searchStr The search string.
     */
    fun List<Country>.searchForAnItem(
        searchStr: String,
    ): List<Country> {
        val filteredItems = filter {
            it.name.unaccent().contains(
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
        return filteredItems.toList()
    }

    /**
     * Map of shared phone codes to their preferred/primary country code.
     * When multiple countries share the same dialling code, this map determines
     * which country is returned by [extractCountryCodeAndPhoneNumber].
     */
    private val preferredCountryForSharedCode: Map<String, String> = mapOf(
        "+1" to "us", // NANP: 22 countries share +1
        "+7" to "ru", // Russia & Kazakhstan
        "+44" to "gb", // UK, Guernsey, Isle of Man, Jersey
        "+61" to "au", // Australia, Christmas Island, Cocos Islands
        "+47" to "no", // Norway & Svalbard
        "+262" to "re", // Réunion & Mayotte
        "+590" to "gp", // Guadeloupe, Saint Barthélemy, Saint Martin
    )

    /**
     * [extractCountryCodeAndPhoneNumber] Returns the country code and the
     * phone e.g +254712345678 -> Pair("ke", "712345678")
     *
     * @param wholePhoneNumber The phone number to be extracted.
     */
    fun extractCountryCodeAndPhoneNumber(wholePhoneNumber: String): Pair<String?, String> {
        // Find all countries whose phone code matches the start of the number
        val candidates = allCountries.filter { wholePhoneNumber.startsWith(it.phoneNoCode) }
        if (candidates.isEmpty()) return null to wholePhoneNumber

        // Pick the best match: prefer the longest phone code first (e.g. +254 over +2),
        // then prefer the "primary" country for shared codes (e.g. US for +1)
        val country = candidates
            .sortedByDescending { it.phoneNoCode.length }
            .let { sorted ->
                val longestCode = sorted.first().phoneNoCode
                val topCandidates = sorted.filter { it.phoneNoCode == longestCode }
                if (topCandidates.size == 1) {
                    topCandidates.first()
                } else {
                    val preferred = preferredCountryForSharedCode[longestCode]
                    topCandidates.find { it.code == preferred } ?: topCandidates.first()
                }
            }

        return country.code to stripPhoneCode(
            phoneCode = country.phoneNoCode,
            phoneNo = wholePhoneNumber,
        )
    }

    /**
     * [stripPhoneCode] Returns the phone number without the phone code.
     *
     * @param phoneCode The phone code.
     * @param phoneNo The phone number.
     */
    private fun stripPhoneCode(
        phoneCode: String,
        phoneNo: String,
    ): String = phoneNo.replaceFirst(phoneCode, "")

    /**
     * [getFlags] Returns the flag of the country. [countryName] The name of
     * the country.
     *
     * @param countryName The name of the country.
     */
    fun getFlags(countryName: String): DrawableResource = when (countryName) {
        "ad" -> Res.drawable.ad
        "ae" -> Res.drawable.ae
        "af" -> Res.drawable.af
        "ag" -> Res.drawable.ag
        "ai" -> Res.drawable.ai
        "al" -> Res.drawable.al
        "am" -> Res.drawable.am
        "ao" -> Res.drawable.ao
        "aq" -> Res.drawable.aq
        "ar" -> Res.drawable.ar
        "as" -> Res.drawable.`as`
        "at" -> Res.drawable.at
        "au" -> Res.drawable.au
        "aw" -> Res.drawable.aw
        "ax" -> Res.drawable.ax
        "az" -> Res.drawable.az
        "ba" -> Res.drawable.ba
        "bb" -> Res.drawable.bb
        "bd" -> Res.drawable.bd
        "be" -> Res.drawable.be
        "bf" -> Res.drawable.bf
        "bg" -> Res.drawable.bg
        "bh" -> Res.drawable.bh
        "bi" -> Res.drawable.bi
        "bj" -> Res.drawable.bj
        "bl" -> Res.drawable.bl
        "bm" -> Res.drawable.bm
        "bn" -> Res.drawable.bn
        "bo" -> Res.drawable.bo
        "br" -> Res.drawable.br
        "bs" -> Res.drawable.bs
        "bt" -> Res.drawable.bt
        "bw" -> Res.drawable.bw
        "by" -> Res.drawable.by
        "bz" -> Res.drawable.bz
        "ca" -> Res.drawable.ca
        "cc" -> Res.drawable.cc
        "cd" -> Res.drawable.cd
        "cf" -> Res.drawable.cf
        "cg" -> Res.drawable.cg
        "ch" -> Res.drawable.ch
        "ci" -> Res.drawable.ci
        "ck" -> Res.drawable.ck
        "cl" -> Res.drawable.cl
        "cm" -> Res.drawable.cm
        "cn" -> Res.drawable.cn
        "co" -> Res.drawable.co
        "cr" -> Res.drawable.cr
        "cu" -> Res.drawable.cu
        "cv" -> Res.drawable.cv
        "cw" -> Res.drawable.cw
        "cx" -> Res.drawable.cx
        "cy" -> Res.drawable.cy
        "cz" -> Res.drawable.cz
        "de" -> Res.drawable.de
        "dj" -> Res.drawable.dj
        "dk" -> Res.drawable.dk
        "dm" -> Res.drawable.dm
        "do" -> Res.drawable.ic_do
        "dz" -> Res.drawable.dz
        "ec" -> Res.drawable.ec
        "ee" -> Res.drawable.ee
        "eg" -> Res.drawable.eg
        "er" -> Res.drawable.er
        "es" -> Res.drawable.es
        "et" -> Res.drawable.et
        "fi" -> Res.drawable.fi
        "fj" -> Res.drawable.fj
        "fk" -> Res.drawable.fk
        "fm" -> Res.drawable.fm
        "fo" -> Res.drawable.fo
        "fr" -> Res.drawable.fr
        "ga" -> Res.drawable.ga
        "gb" -> Res.drawable.gb
        "gd" -> Res.drawable.gd
        "ge" -> Res.drawable.ge
        "gf" -> Res.drawable.gf
        "gg" -> Res.drawable.gg
        "gh" -> Res.drawable.gh
        "gi" -> Res.drawable.gi
        "gl" -> Res.drawable.gl
        "gm" -> Res.drawable.gm
        "gn" -> Res.drawable.gn
        "gp" -> Res.drawable.gp
        "gq" -> Res.drawable.gq
        "gr" -> Res.drawable.gr
        "gt" -> Res.drawable.gt
        "gu" -> Res.drawable.gu
        "gw" -> Res.drawable.gw
        "gy" -> Res.drawable.gy
        "hk" -> Res.drawable.hk
        "hn" -> Res.drawable.hn
        "hr" -> Res.drawable.hr
        "ht" -> Res.drawable.ht
        "hu" -> Res.drawable.hu
        "id" -> Res.drawable.id
        "ie" -> Res.drawable.ie
        "il" -> Res.drawable.il
        "im" -> Res.drawable.im
        "is" -> Res.drawable.`is`
        "in" -> Res.drawable.`in`
        "io" -> Res.drawable.io
        "iq" -> Res.drawable.iq
        "ir" -> Res.drawable.ir
        "it" -> Res.drawable.it
        "je" -> Res.drawable.je
        "jm" -> Res.drawable.jm
        "jo" -> Res.drawable.jo
        "jp" -> Res.drawable.jp
        "ke" -> Res.drawable.ke
        "kg" -> Res.drawable.kg
        "kh" -> Res.drawable.kh
        "ki" -> Res.drawable.ki
        "km" -> Res.drawable.km
        "kn" -> Res.drawable.kn
        "kp" -> Res.drawable.kp
        "kr" -> Res.drawable.kr
        "kw" -> Res.drawable.kw
        "ky" -> Res.drawable.ky
        "kz" -> Res.drawable.kz
        "la" -> Res.drawable.la
        "lb" -> Res.drawable.lb
        "lc" -> Res.drawable.lc
        "li" -> Res.drawable.li
        "lk" -> Res.drawable.lk
        "lr" -> Res.drawable.lr
        "ls" -> Res.drawable.ls
        "lt" -> Res.drawable.lt
        "lu" -> Res.drawable.lu
        "lv" -> Res.drawable.lv
        "ly" -> Res.drawable.ly
        "ma" -> Res.drawable.ma
        "mc" -> Res.drawable.mc
        "md" -> Res.drawable.md
        "me" -> Res.drawable.me
        "mf" -> Res.drawable.mf
        "mg" -> Res.drawable.mg
        "mh" -> Res.drawable.mh
        "mk" -> Res.drawable.mk
        "ml" -> Res.drawable.ml
        "mm" -> Res.drawable.mm
        "mn" -> Res.drawable.mn
        "mo" -> Res.drawable.mo
        "mp" -> Res.drawable.mp
        "mq" -> Res.drawable.mq
        "mr" -> Res.drawable.mr
        "ms" -> Res.drawable.ms
        "mt" -> Res.drawable.mt
        "mu" -> Res.drawable.mu
        "mv" -> Res.drawable.mv
        "mw" -> Res.drawable.mw
        "mx" -> Res.drawable.mx
        "my" -> Res.drawable.my
        "mz" -> Res.drawable.mz
        "na" -> Res.drawable.na
        "nc" -> Res.drawable.nc
        "ne" -> Res.drawable.ne
        "nf" -> Res.drawable.nf
        "ng" -> Res.drawable.ng
        "ni" -> Res.drawable.ni
        "nl" -> Res.drawable.nl
        "no" -> Res.drawable.no
        "np" -> Res.drawable.np
        "nr" -> Res.drawable.nr
        "nu" -> Res.drawable.nu
        "nz" -> Res.drawable.nz
        "om" -> Res.drawable.om
        "pa" -> Res.drawable.pa
        "pe" -> Res.drawable.pe
        "pf" -> Res.drawable.pf
        "pg" -> Res.drawable.pg
        "ph" -> Res.drawable.ph
        "pk" -> Res.drawable.pk
        "pl" -> Res.drawable.pl
        "pm" -> Res.drawable.pm
        "pn" -> Res.drawable.pn
        "pr" -> Res.drawable.pr
        "ps" -> Res.drawable.ps
        "pt" -> Res.drawable.pt
        "pw" -> Res.drawable.pw
        "py" -> Res.drawable.py
        "qa" -> Res.drawable.qa
        "re" -> Res.drawable.re
        "ro" -> Res.drawable.ro
        "rs" -> Res.drawable.rs
        "ru" -> Res.drawable.ru
        "rw" -> Res.drawable.rw
        "sa" -> Res.drawable.sa
        "sb" -> Res.drawable.sb
        "sc" -> Res.drawable.sc
        "sd" -> Res.drawable.sd
        "se" -> Res.drawable.se
        "sg" -> Res.drawable.sg
        "sh" -> Res.drawable.sh
        "si" -> Res.drawable.si
        "sk" -> Res.drawable.sk
        "sl" -> Res.drawable.sl
        "sm" -> Res.drawable.sm
        "sn" -> Res.drawable.sn
        "so" -> Res.drawable.so
        "sr" -> Res.drawable.sr
        "ss" -> Res.drawable.ss
        "st" -> Res.drawable.st
        "sv" -> Res.drawable.sv
        "sx" -> Res.drawable.sx
        "sy" -> Res.drawable.sy
        "sz" -> Res.drawable.sz
        "tc" -> Res.drawable.tc
        "td" -> Res.drawable.td
        "tg" -> Res.drawable.tg
        "th" -> Res.drawable.th
        "tj" -> Res.drawable.tj
        "tk" -> Res.drawable.tk
        "tl" -> Res.drawable.tl
        "tm" -> Res.drawable.tm
        "tn" -> Res.drawable.tn
        "to" -> Res.drawable.to
        "tr" -> Res.drawable.tr
        "tt" -> Res.drawable.tt
        "tv" -> Res.drawable.tv
        "tw" -> Res.drawable.tw
        "tz" -> Res.drawable.tz
        "ua" -> Res.drawable.ua
        "ug" -> Res.drawable.ug
        "us" -> Res.drawable.us
        "uy" -> Res.drawable.uy
        "uz" -> Res.drawable.uz
        "va" -> Res.drawable.va
        "vc" -> Res.drawable.vc
        "ve" -> Res.drawable.ve
        "vg" -> Res.drawable.vg
        "vi" -> Res.drawable.vi
        "vn" -> Res.drawable.vn
        "vu" -> Res.drawable.vu
        "wf" -> Res.drawable.wf
        "ws" -> Res.drawable.ws
        "xk" -> Res.drawable.xk
        "ye" -> Res.drawable.ye
        "yt" -> Res.drawable.yt
        "za" -> Res.drawable.za
        "zm" -> Res.drawable.zm
        "zw" -> Res.drawable.zw
        else -> Res.drawable.tr
    }

    /**
     * [getCountryName] Returns the name of the country. [countryName] The name
     * of the country.
     *
     * @param countryName The name of the country.
     */
    fun getCountryName(countryName: String): StringResource = when (countryName) {
        "ad" -> Res.string.andorra
        "ae" -> Res.string.united_arab_emirates
        "af" -> Res.string.afghanistan
        "ag" -> Res.string.antigua_and_barbuda
        "ai" -> Res.string.anguilla
        "al" -> Res.string.albania
        "am" -> Res.string.armenia
        "ao" -> Res.string.angola
        "aq" -> Res.string.antarctica
        "ar" -> Res.string.argentina
        "as" -> Res.string.american_samoa
        "at" -> Res.string.austria
        "au" -> Res.string.australia
        "aw" -> Res.string.aruba
        "ax" -> Res.string.aland_islands
        "az" -> Res.string.azerbaijan
        "ba" -> Res.string.bosnia
        "bb" -> Res.string.barbados
        "bd" -> Res.string.bangladesh
        "be" -> Res.string.belgium
        "bf" -> Res.string.burkina_faso
        "bg" -> Res.string.bulgaria
        "bh" -> Res.string.bahrain
        "bi" -> Res.string.burundi
        "bj" -> Res.string.benin
        "bl" -> Res.string.saint_barhelemy
        "bm" -> Res.string.bermuda
        "bn" -> Res.string.brunei_darussalam
        "bo" -> Res.string.bolivia
        "br" -> Res.string.brazil
        "bs" -> Res.string.bahamas
        "bt" -> Res.string.bhutan
        "bw" -> Res.string.botswana
        "by" -> Res.string.belarus
        "bz" -> Res.string.belize
        "ca" -> Res.string.canada
        "cc" -> Res.string.cocos
        "cd" -> Res.string.congo_democratic
        "cf" -> Res.string.central_african
        "cg" -> Res.string.congo
        "ch" -> Res.string.switzerland
        "ci" -> Res.string.cote_dlvoire
        "ck" -> Res.string.cook_islands
        "cl" -> Res.string.chile
        "cm" -> Res.string.cameroon
        "cn" -> Res.string.china
        "co" -> Res.string.colombia
        "cr" -> Res.string.costa_rica
        "cu" -> Res.string.cuba
        "cv" -> Res.string.cape_verde
        "cw" -> Res.string.curacao
        "cx" -> Res.string.christmas_island
        "cy" -> Res.string.cyprus
        "cz" -> Res.string.czech_republic
        "de" -> Res.string.germany
        "dj" -> Res.string.djibouti
        "dk" -> Res.string.denmark
        "dm" -> Res.string.dominica
        "do" -> Res.string.dominician_republic
        "dz" -> Res.string.algeria
        "ec" -> Res.string.ecuador
        "ee" -> Res.string.estonia
        "eg" -> Res.string.egypt
        "er" -> Res.string.eritrea
        "es" -> Res.string.spain
        "et" -> Res.string.ethiopia
        "fi" -> Res.string.finland
        "fj" -> Res.string.fiji
        "fk" -> Res.string.falkland_islands
        "fm" -> Res.string.micro
        "fo" -> Res.string.faroe_islands
        "fr" -> Res.string.france
        "ga" -> Res.string.gabon
        "gb" -> Res.string.united_kingdom
        "gd" -> Res.string.grenada
        "ge" -> Res.string.georgia
        "gf" -> Res.string.french_guyana
        "gg" -> Res.string.guernsey
        "gh" -> Res.string.ghana
        "gi" -> Res.string.gibraltar
        "gl" -> Res.string.greenland
        "gm" -> Res.string.gambia
        "gn" -> Res.string.guinea
        "gp" -> Res.string.guadeloupe
        "gq" -> Res.string.equatorial_guinea
        "gr" -> Res.string.greece
        "gt" -> Res.string.guatemala
        "gu" -> Res.string.guam
        "gw" -> Res.string.guinea_bissau
        "gy" -> Res.string.guyana
        "hk" -> Res.string.hong_kong
        "hn" -> Res.string.honduras
        "hr" -> Res.string.croatia
        "ht" -> Res.string.haiti
        "hu" -> Res.string.hungary
        "id" -> Res.string.indonesia
        "ie" -> Res.string.ireland
        "il" -> Res.string.israil
        "im" -> Res.string.isle_of_man
        "is" -> Res.string.iceland
        "in" -> Res.string.india
        "io" -> Res.string.british_indian_ocean
        "iq" -> Res.string.iraq
        "ir" -> Res.string.iran
        "it" -> Res.string.italia
        "je" -> Res.string.jersey
        "jm" -> Res.string.jamaica
        "jo" -> Res.string.jordan
        "jp" -> Res.string.japan
        "ke" -> Res.string.kenya
        "kg" -> Res.string.kyrgyzstan
        "kh" -> Res.string.cambodia
        "ki" -> Res.string.kiribati
        "km" -> Res.string.comoros
        "kn" -> Res.string.saint_kitts
        "kp" -> Res.string.north_korea
        "kr" -> Res.string.south_korea
        "kw" -> Res.string.kuwait
        "ky" -> Res.string.cayman_islands
        "kz" -> Res.string.kazakhstan
        "la" -> Res.string.laos
        "lb" -> Res.string.lebanon
        "lc" -> Res.string.saint_lucia
        "li" -> Res.string.liechtenstein
        "lk" -> Res.string.siri_lanka
        "lr" -> Res.string.liberia
        "ls" -> Res.string.lesotho
        "lt" -> Res.string.lithuania
        "lu" -> Res.string.luxembourg
        "lv" -> Res.string.latvia
        "ly" -> Res.string.libya
        "ma" -> Res.string.marocco
        "mc" -> Res.string.monaco
        "md" -> Res.string.moldova
        "me" -> Res.string.montenegro
        "mf" -> Res.string.saint_martin
        "mg" -> Res.string.madagascar
        "mh" -> Res.string.marshall_islands
        "mk" -> Res.string.north_macedonia
        "ml" -> Res.string.mali
        "mm" -> Res.string.myanmar
        "mn" -> Res.string.mongolia
        "mo" -> Res.string.macau
        "mp" -> Res.string.northern_mariana
        "mq" -> Res.string.martinique
        "mr" -> Res.string.mauriatana
        "ms" -> Res.string.montserrat
        "mt" -> Res.string.malta
        "mu" -> Res.string.mauritius
        "mv" -> Res.string.maldives
        "mw" -> Res.string.malawi
        "mx" -> Res.string.mexico
        "my" -> Res.string.malaysia
        "mz" -> Res.string.mozambique
        "na" -> Res.string.namibia
        "nc" -> Res.string.new_caledonia
        "ne" -> Res.string.niger
        "nf" -> Res.string.norfolk
        "ng" -> Res.string.nigeria
        "ni" -> Res.string.nicaragua
        "nl" -> Res.string.netherlands
        "no" -> Res.string.norway
        "np" -> Res.string.nepal
        "nr" -> Res.string.nauru
        "nu" -> Res.string.niue
        "nz" -> Res.string.new_zealand
        "om" -> Res.string.oman
        "pa" -> Res.string.panama
        "pe" -> Res.string.peru
        "pf" -> Res.string.french_polynesia
        "pg" -> Res.string.papua_new_guinea
        "ph" -> Res.string.philippinies
        "pk" -> Res.string.pakistan
        "pl" -> Res.string.poland
        "pm" -> Res.string.saint_pierre
        "pn" -> Res.string.pitcairn
        "pr" -> Res.string.puerto_rico
        "ps" -> Res.string.state_of_palestine
        "pt" -> Res.string.portugal
        "pw" -> Res.string.palau
        "py" -> Res.string.paraguay
        "qa" -> Res.string.qatar
        "re" -> Res.string.reunion
        "ro" -> Res.string.romania
        "rs" -> Res.string.serbia
        "ru" -> Res.string.russia
        "rw" -> Res.string.rwanda
        "sa" -> Res.string.saudi_arabia
        "sb" -> Res.string.solomon_islands
        "sc" -> Res.string.seychelles
        "sd" -> Res.string.sudan
        "se" -> Res.string.sweden
        "sg" -> Res.string.singapore
        "sh" -> Res.string.saint_helena
        "si" -> Res.string.slovenia
        "sk" -> Res.string.slovakia
        "sl" -> Res.string.sierra_leone
        "sm" -> Res.string.san_marino
        "sn" -> Res.string.senegal
        "so" -> Res.string.somali
        "sr" -> Res.string.suriname
        "ss" -> Res.string.south_sudan
        "st" -> Res.string.sao_tome
        "sv" -> Res.string.el_salvador
        "sx" -> Res.string.sint_maarten
        "sy" -> Res.string.syrian
        "sz" -> Res.string.swaziland
        "tc" -> Res.string.turks_and_caicos
        "td" -> Res.string.chad
        "tg" -> Res.string.togo
        "th" -> Res.string.thailand
        "tj" -> Res.string.taijikistan
        "tk" -> Res.string.tokelau
        "tl" -> Res.string.timor_leste
        "tm" -> Res.string.turkmenistan
        "tn" -> Res.string.tunisia
        "to" -> Res.string.tonga
        "tr" -> Res.string.turkey
        "tt" -> Res.string.trinidad_and_tobago
        "tv" -> Res.string.tuvalu
        "tw" -> Res.string.taiwan
        "tz" -> Res.string.tazmania
        "ua" -> Res.string.ukraina
        "ug" -> Res.string.uganda
        "us" -> Res.string.united_states_america
        "uy" -> Res.string.uruguay
        "uz" -> Res.string.uzbekistan
        "va" -> Res.string.holy_see
        "vc" -> Res.string.saint_vincent
        "ve" -> Res.string.venezuela
        "vg" -> Res.string.virgin_islands
        "vi" -> Res.string.virgin_island_us
        "vn" -> Res.string.vietnam
        "vu" -> Res.string.vanuatu
        "wf" -> Res.string.walli_and_fatuna
        "ws" -> Res.string.samoa
        "xk" -> Res.string.kosovo
        "ye" -> Res.string.yemen
        "yt" -> Res.string.mayotte
        "za" -> Res.string.south_africa
        "zm" -> Res.string.zambia
        "zw" -> Res.string.zimbabwe
        else -> Res.string.unknown
    }

    /**
     * [allCountries] is a list of all countries in the world sorted
     * alphabetically.
     */
    val allCountries: List<Country> = listOf(
        Country(
            code = "ad",
            phoneNoCode = "+376",
            name = "Andorra",
            flag = Res.drawable.ad,
        ),
        Country(
            code = "ae",
            phoneNoCode = "+971",
            name = "United Arab Emirates (UAE)",
            flag = Res.drawable.ae,
        ),
        Country(
            code = "af",
            phoneNoCode = "+93",
            name = "Afghanistan",
            flag = Res.drawable.af,
        ),
        Country(
            code = "ag",
            phoneNoCode = "+1",
            name = "Antigua and Barbuda",
            flag = Res.drawable.ag,
        ),
        Country(
            code = "ai",
            phoneNoCode = "+1",
            name = "Anguilla",
            flag = Res.drawable.ai,
        ),
        Country(
            "al",
            "+355",
            "Albania",
            Res.drawable.al,
        ),
        Country(
            "am",
            "+374",
            "Armenia",
            Res.drawable.am,
        ),
        Country(
            "ao",
            "+244",
            "Angola",
            Res.drawable.ao,
        ),
        Country(
            "aq",
            "+672",
            "Antarctica",
            Res.drawable.aq,
        ),
        Country(
            "ar",
            "+54",
            "Argentina",
            Res.drawable.ar,
        ),
        Country(
            "as",
            "+1",
            "American Samoa",
            Res.drawable.`as`,
        ),
        Country(
            "at",
            "+43",
            "Austria",
            Res.drawable.at,
        ),
        Country(
            "au",
            "+61",
            "Australia",
            Res.drawable.au,
        ),
        Country(
            "aw",
            "+297",
            "Aruba",
            Res.drawable.aw,
        ),
        Country(
            "ax",
            "+358",
            "\u00C5land Islands",
            Res.drawable.ax,
        ),
        Country(
            "az",
            "+994",
            "Azerbaijan",
            Res.drawable.az,
        ),
        Country(
            "ba",
            "+387",
            "Bosnia And Herzegovina",
            Res.drawable.ba,
        ),
        Country(
            "bb",
            "+1",
            "Barbados",
            Res.drawable.bb,
        ),
        Country(
            "bd",
            "+880",
            "Bangladesh",
            Res.drawable.bd,
        ),
        Country(
            "be",
            "+32",
            "Belgium",
            Res.drawable.be,
        ),
        Country(
            "bf",
            "+226",
            "Burkina Faso",
            Res.drawable.bf,
        ),
        Country(
            "bg",
            "+359",
            "Bulgaria",
            Res.drawable.bg,
        ),
        Country(
            "bh",
            "+973",
            "Bahrain",
            Res.drawable.bh,
        ),
        Country(
            "bi",
            "+257",
            "Burundi",
            Res.drawable.bi,
        ),
        Country(
            "bj",
            "+229",
            "Benin",
            Res.drawable.bj,
        ),
        Country(
            "bl",
            "+590",
            "Saint Barth\u00E9lemy",
            Res.drawable.bl,
        ),
        Country(
            "bm",
            "+1",
            "Bermuda",
            Res.drawable.bm,
        ),
        Country(
            "bn",
            "+673",
            "Brunei Darussalam",
            Res.drawable.bn,
        ),
        Country(
            "bo",
            "+591",
            "Bolivia, Plurinational State Of",
            Res.drawable.bo,
        ),
        Country(
            "br",
            "+55",
            "Brazil",
            Res.drawable.br,
        ),
        Country(
            "bs",
            "+1",
            "Bahamas",
            Res.drawable.bs,
        ),
        Country(
            "bt",
            "+975",
            "Bhutan",
            Res.drawable.bt,
        ),
        Country(
            "bw",
            "+267",
            "Botswana",
            Res.drawable.bw,
        ),
        Country(
            "by",
            "+375",
            "Belarus",
            Res.drawable.by,
        ),
        Country(
            "bz",
            "+501",
            "Belize",
            Res.drawable.bz,
        ),
        Country(
            "ca",
            "+1",
            "Canada",
            Res.drawable.ca,
        ),
        Country(
            "cc",
            "+61",
            "Cocos (keeling) Islands",
            Res.drawable.cc,
        ),
        Country(
            "cd",
            "+243",
            "Congo, The Democratic Republic Of The",
            Res.drawable.cd,
        ),
        Country(
            "cf",
            "+236",
            "Central African Republic",
            Res.drawable.cf,
        ),
        Country(
            "cg",
            "+242",
            "Congo",
            Res.drawable.cg,
        ),
        Country(
            "ch",
            "+41",
            "Switzerland",
            Res.drawable.ch,
        ),
        Country(
            "ci",
            "+225",
            "C\u00F4te D'ivoire",
            Res.drawable.ci,
        ),
        Country(
            "ck",
            "+682",
            "Cook Islands",
            Res.drawable.ck,
        ),
        Country(
            "cl",
            "+56",
            "Chile",
            Res.drawable.cl,
        ),
        Country(
            "cm",
            "+237",
            "Cameroon",
            Res.drawable.cm,
        ),
        Country(
            "cn",
            "+86",
            "China",
            Res.drawable.cn,
        ),
        Country(
            "co",
            "+57",
            "Colombia",
            Res.drawable.co,
        ),
        Country(
            "cr",
            "+506",
            "Costa Rica",
            Res.drawable.cr,
        ),
        Country(
            "cu",
            "+53",
            "Cuba",
            Res.drawable.cu,
        ),
        Country(
            "cv",
            "+238",
            "Cape Verde",
            Res.drawable.cv,
        ),
        Country(
            "cw",
            "+599",
            "Cura\u00E7ao",
            Res.drawable.cw,
        ),
        Country(
            "cx",
            "+61",
            "Christmas Island",
            Res.drawable.cx,
        ),
        Country(
            "cy",
            "+357",
            "Cyprus",
            Res.drawable.cy,
        ),
        Country(
            "cz",
            "+420",
            "Czech Republic",
            Res.drawable.cz,
        ),
        Country(
            "de",
            "+49",
            "Germany",
            Res.drawable.de,
        ),
        Country(
            "dj",
            "+253",
            "Djibouti",
            Res.drawable.dj,
        ),
        Country(
            "dk",
            "+45",
            "Denmark",
            Res.drawable.dk,
        ),
        Country(
            "dm",
            "+1",
            "Dominica",
            Res.drawable.dm,
        ),
        Country(
            "do",
            "+1",
            "Dominican Republic",
            Res.drawable.ic_do,
        ),
        Country(
            "dz",
            "+213",
            "Algeria",
            Res.drawable.dz,
        ),
        Country(
            "ec",
            "+593",
            "Ecuador",
            Res.drawable.ec,
        ),
        Country(
            "ee",
            "+372",
            "Estonia",
            Res.drawable.ee,
        ),
        Country(
            "eg",
            "+20",
            "Egypt",
            Res.drawable.eg,
        ),
        Country(
            "er",
            "+291",
            "Eritrea",
            Res.drawable.er,
        ),
        Country(
            "es",
            "+34",
            "Spain",
            Res.drawable.es,
        ),
        Country(
            "et",
            "+251",
            "Ethiopia",
            Res.drawable.et,
        ),
        Country(
            "fi",
            "+358",
            "Finland",
            Res.drawable.fi,
        ),
        Country(
            "fj",
            "+679",
            "Fiji",
            Res.drawable.fj,
        ),
        Country(
            "fk",
            "+500",
            "Falkland Islands (malvinas)",
            Res.drawable.fk,
        ),
        Country(
            "fm",
            "+691",
            "Micronesia, Federated States Of",
            Res.drawable.fm,
        ),
        Country(
            "fo",
            "+298",
            "Faroe Islands",
            Res.drawable.fo,
        ),
        Country(
            "fr",
            "+33",
            "France",
            Res.drawable.fr,
        ),
        Country(
            "ga",
            "+241",
            "Gabon",
            Res.drawable.ga,
        ),
        Country(
            "gb",
            "+44",
            "United Kingdom",
            Res.drawable.gb,
        ),
        Country(
            "gd",
            "+1",
            "Grenada",
            Res.drawable.gd,
        ),
        Country(
            "ge",
            "+995",
            "Georgia",
            Res.drawable.ge,
        ),
        Country(
            "gf",
            "+594",
            "French Guyana",
            Res.drawable.gf,
        ),
        Country(
            "gh",
            "+233",
            "Ghana",
            Res.drawable.gh,
        ),
        Country(
            "gi",
            "+350",
            "Gibraltar",
            Res.drawable.gi,
        ),
        Country(
            "gl",
            "+299",
            "Greenland",
            Res.drawable.gl,
        ),
        Country(
            "gm",
            "+220",
            "Gambia",
            Res.drawable.gm,
        ),
        Country(
            "gn",
            "+224",
            "Guinea",
            Res.drawable.gn,
        ),
        Country(
            "gp",
            "+590",
            "Guadeloupe",
            Res.drawable.gp,
        ),
        Country(
            "gq",
            "+240",
            "Equatorial Guinea",
            Res.drawable.gq,
        ),
        Country(
            "gr",
            "+30",
            "Greece",
            Res.drawable.gr,
        ),
        Country(
            "gt",
            "+502",
            "Guatemala",
            Res.drawable.gt,
        ),
        Country(
            "gu",
            "+1",
            "Guam",
            Res.drawable.gu,
        ),
        Country(
            "gw",
            "+245",
            "Guinea-bissau",
            Res.drawable.gw,
        ),
        Country(
            "gy",
            "+592",
            "Guyana",
            Res.drawable.gy,
        ),
        Country(
            "hk",
            "+852",
            "Hong Kong",
            Res.drawable.hk,
        ),
        Country(
            "hn",
            "+504",
            "Honduras",
            Res.drawable.hn,
        ),
        Country(
            "hr",
            "+385",
            "Croatia",
            Res.drawable.hr,
        ),
        Country(
            "ht",
            "+509",
            "Haiti",
            Res.drawable.ht,
        ),
        Country(
            "hu",
            "+36",
            "Hungary",
            Res.drawable.hu,
        ),
        Country(
            "id",
            "+62",
            "Indonesia",
            Res.drawable.id,
        ),
        Country(
            "ie",
            "+353",
            "Ireland",
            Res.drawable.ie,
        ),
        Country(
            "il",
            "+972",
            "Israel",
            Res.drawable.il,
        ),
        Country(
            "im",
            "+44",
            "Isle Of Man",
            Res.drawable.im,
        ),
        Country(
            "is",
            "+354",
            "Iceland",
            Res.drawable.`is`,
        ),
        Country(
            "in",
            "+91",
            "India",
            Res.drawable.`in`,
        ),
        Country(
            "io",
            "+246",
            "British Indian Ocean Territory",
            Res.drawable.io,
        ),
        Country(
            "iq",
            "+964",
            "Iraq",
            Res.drawable.iq,
        ),
        Country(
            "ir",
            "+98",
            "Iran, Islamic Republic Of",
            Res.drawable.ir,
        ),
        Country(
            "it",
            "+39",
            "Italy",
            Res.drawable.it,
        ),
        Country(
            "je",
            "+44",
            "Jersey ",
            Res.drawable.je,
        ),
        Country(
            "jm",
            "+1",
            "Jamaica",
            Res.drawable.jm,
        ),
        Country(
            "jo",
            "+962",
            "Jordan",
            Res.drawable.jo,
        ),
        Country(
            "jp",
            "+81",
            "Japan",
            Res.drawable.jp,
        ),
        Country(
            "ke",
            "+254",
            "Kenya",
            Res.drawable.ke,
        ),
        Country(
            "kg",
            "+996",
            "Kyrgyzstan",
            Res.drawable.kg,
        ),
        Country(
            "kh",
            "+855",
            "Cambodia",
            Res.drawable.kh,
        ),
        Country(
            "ki",
            "+686",
            "Kiribati",
            Res.drawable.ki,
        ),
        Country(
            "km",
            "+269",
            "Comoros",
            Res.drawable.km,
        ),
        Country(
            "kn",
            "+1",
            "Saint Kitts and Nevis",
            Res.drawable.kn,
        ),
        Country(
            "kp",
            "+850",
            "North Korea",
            Res.drawable.kp,
        ),
        Country(
            "kr",
            "+82",
            "South Korea",
            Res.drawable.kr,
        ),
        Country(
            "kw",
            "+965",
            "Kuwait",
            Res.drawable.kw,
        ),
        Country(
            "ky",
            "+1",
            "Cayman Islands",
            Res.drawable.ky,
        ),
        Country(
            "kz",
            "+7",
            "Kazakhstan",
            Res.drawable.kz,
        ),
        Country(
            "la",
            "+856",
            "Lao People's Democratic Republic",
            Res.drawable.la,
        ),
        Country(
            "lb",
            "+961",
            "Lebanon",
            Res.drawable.lb,
        ),
        Country(
            "lc",
            "+1",
            "Saint Lucia",
            Res.drawable.lc,
        ),
        Country(
            "li",
            "+423",
            "Liechtenstein",
            Res.drawable.li,
        ),
        Country(
            "lk",
            "+94",
            "Sri Lanka",
            Res.drawable.lk,
        ),
        Country(
            "lr",
            "+231",
            "Liberia",
            Res.drawable.lr,
        ),
        Country(
            "ls",
            "+266",
            "Lesotho",
            Res.drawable.ls,
        ),
        Country(
            "lt",
            "+370",
            "Lithuania",
            Res.drawable.lt,
        ),
        Country(
            "lu",
            "+352",
            "Luxembourg",
            Res.drawable.lu,
        ),
        Country(
            "lv",
            "+371",
            "Latvia",
            Res.drawable.lv,
        ),
        Country(
            "ly",
            "+218",
            "Libya",
            Res.drawable.ly,
        ),
        Country(
            "ma",
            "+212",
            "Morocco",
            Res.drawable.ma,
        ),
        Country(
            "mc",
            "+377",
            "Monaco",
            Res.drawable.mc,
        ),
        Country(
            "md",
            "+373",
            "Moldova, Republic Of",
            Res.drawable.md,
        ),
        Country(
            "me",
            "+382",
            "Montenegro",
            Res.drawable.me,
        ),
        Country(
            "mf",
            "+590",
            "Saint Martin",
            Res.drawable.mf,
        ),
        Country(
            "mg",
            "+261",
            "Madagascar",
            Res.drawable.mg,
        ),
        Country(
            "mh",
            "+692",
            "Marshall Islands",
            Res.drawable.mh,
        ),
        Country(
            "mk",
            "+389",
            "Macedonia (FYROM)",
            Res.drawable.mk,
        ),
        Country(
            "ml",
            "+223",
            "Mali",
            Res.drawable.ml,
        ),
        Country(
            "mm",
            "+95",
            "Myanmar",
            Res.drawable.mm,
        ),
        Country(
            "mn",
            "+976",
            "Mongolia",
            Res.drawable.mn,
        ),
        Country(
            "mo",
            "+853",
            "Macau",
            Res.drawable.mo,
        ),
        Country(
            "mp",
            "+1",
            "Northern Mariana Islands",
            Res.drawable.mp,
        ),
        Country(
            "mq",
            "+596",
            "Martinique",
            Res.drawable.mq,
        ),
        Country(
            "mr",
            "+222",
            "Mauritania",
            Res.drawable.mr,
        ),
        Country(
            "ms",
            "+1",
            "Montserrat",
            Res.drawable.ms,
        ),
        Country(
            "mt",
            "+356",
            "Malta",
            Res.drawable.mt,
        ),
        Country(
            "mu",
            "+230",
            "Mauritius",
            Res.drawable.mu,
        ),
        Country(
            "mv",
            "+960",
            "Maldives",
            Res.drawable.mv,
        ),
        Country(
            "mw",
            "+265",
            "Malawi",
            Res.drawable.mw,
        ),
        Country(
            "mx",
            "+52",
            "Mexico",
            Res.drawable.mx,
        ),
        Country(
            "my",
            "+60",
            "Malaysia",
            Res.drawable.my,
        ),
        Country(
            "mz",
            "+258",
            "Mozambique",
            Res.drawable.mz,
        ),
        Country(
            "na",
            "+264",
            "Namibia",
            Res.drawable.na,
        ),
        Country(
            "nc",
            "+687",
            "New Caledonia",
            Res.drawable.nc,
        ),
        Country(
            "ne",
            "+227",
            "Niger",
            Res.drawable.ne,
        ),
        Country(
            "nf",
            "+672",
            "Norfolk Islands",
            Res.drawable.nf,
        ),
        Country(
            "ng",
            "+234",
            "Nigeria",
            Res.drawable.ng,
        ),
        Country(
            "ni",
            "+505",
            "Nicaragua",
            Res.drawable.ni,
        ),
        Country(
            "nl",
            "+31",
            "Netherlands",
            Res.drawable.nl,
        ),
        Country(
            "no",
            "+47",
            "Norway",
            Res.drawable.no,
        ),
        Country(
            "np",
            "+977",
            "Nepal",
            Res.drawable.np,
        ),
        Country(
            "nr",
            "+674",
            "Nauru",
            Res.drawable.nr,
        ),
        Country(
            "nu",
            "+683",
            "Niue",
            Res.drawable.nu,
        ),
        Country(
            "nz",
            "+64",
            "New Zealand",
            Res.drawable.nz,
        ),
        Country(
            "om",
            "+968",
            "Oman",
            Res.drawable.om,
        ),
        Country(
            "pa",
            "+507",
            "Panama",
            Res.drawable.pa,
        ),
        Country(
            "pe",
            "+51",
            "Peru",
            Res.drawable.pe,
        ),
        Country(
            "pf",
            "+689",
            "French Polynesia",
            Res.drawable.pf,
        ),
        Country(
            "pg",
            "+675",
            "Papua New Guinea",
            Res.drawable.pg,
        ),
        Country(
            "ph",
            "+63",
            "Philippines",
            Res.drawable.ph,
        ),
        Country(
            "pk",
            "+92",
            "Pakistan",
            Res.drawable.pk,
        ),
        Country(
            "pl",
            "+48",
            "Poland",
            Res.drawable.pl,
        ),
        Country(
            "pm",
            "+508",
            "Saint Pierre And Miquelon",
            Res.drawable.pm,
        ),
        Country(
            "pn",
            "+870",
            "Pitcairn Islands",
            Res.drawable.pn,
        ),
        Country(
            "pr",
            "+1",
            "Puerto Rico",
            Res.drawable.pr,
        ),
        Country(
            "ps",
            "+970",
            "Palestine",
            Res.drawable.ps,
        ),
        Country(
            "pt",
            "+351",
            "Portugal",
            Res.drawable.pt,
        ),
        Country(
            "pw",
            "+680",
            "Palau",
            Res.drawable.pw,
        ),
        Country(
            "py",
            "+595",
            "Paraguay",
            Res.drawable.py,
        ),
        Country(
            "qa",
            "+974",
            "Qatar",
            Res.drawable.qa,
        ),
        Country(
            "re",
            "+262",
            "R\u00E9union",
            Res.drawable.re,
        ),
        Country(
            "ro",
            "+40",
            "Romania",
            Res.drawable.ro,
        ),
        Country(
            "rs",
            "+381",
            "Serbia",
            Res.drawable.rs,
        ),
        Country(
            "ru",
            "+7",
            "Russian Federation",
            Res.drawable.ru,
        ),
        Country(
            "rw",
            "+250",
            "Rwanda",
            Res.drawable.rw,
        ),
        Country(
            "sa",
            "+966",
            "Saudi Arabia",
            Res.drawable.sa,
        ),
        Country(
            "sb",
            "+677",
            "Solomon Islands",
            Res.drawable.sb,
        ),
        Country(
            "sc",
            "+248",
            "Seychelles",
            Res.drawable.sc,
        ),
        Country(
            "sd",
            "+249",
            "Sudan",
            Res.drawable.sd,
        ),
        Country(
            "se",
            "+46",
            "Sweden",
            Res.drawable.se,
        ),
        Country(
            "sg",
            "+65",
            "Singapore",
            Res.drawable.sg,
        ),
        Country(
            "sh",
            "+290",
            "Saint Helena, Ascension And Tristan Da Cunha",
            Res.drawable.sh,
        ),
        Country(
            "si",
            "+386",
            "Slovenia",
            Res.drawable.si,
        ),
        Country(
            "sk",
            "+421",
            "Slovakia",
            Res.drawable.sk,
        ),
        Country(
            "sl",
            "+232",
            "Sierra Leone",
            Res.drawable.sl,
        ),
        Country(
            "sm",
            "+378",
            "San Marino",
            Res.drawable.sm,
        ),
        Country(
            "sn",
            "+221",
            "Senegal",
            Res.drawable.sn,
        ),
        Country(
            "so",
            "+252",
            "Somalia",
            Res.drawable.so,
        ),
        Country(
            "sr",
            "+597",
            "Suriname",
            Res.drawable.sr,
        ),
        Country(
            "ss",
            "+211",
            "South Sudan",
            Res.drawable.ss,
        ),
        Country(
            "st",
            "+239",
            "Sao Tome And Principe",
            Res.drawable.st,
        ),
        Country(
            "sv",
            "+503",
            "El Salvador",
            Res.drawable.sv,
        ),
        Country(
            "sx",
            "+1",
            "Sint Maarten",
            Res.drawable.sx,
        ),
        Country(
            "sy",
            "+963",
            "Syrian Arab Republic",
            Res.drawable.sy,
        ),
        Country(
            "sz",
            "+268",
            "Swaziland",
            Res.drawable.sz,
        ),
        Country(
            "tc",
            "+1",
            "Turks and Caicos Islands",
            Res.drawable.tc,
        ),
        Country(
            "td",
            "+235",
            "Chad",
            Res.drawable.td,
        ),
        Country(
            "tg",
            "+228",
            "Togo",
            Res.drawable.tg,
        ),
        Country(
            "th",
            "+66",
            "Thailand",
            Res.drawable.th,
        ),
        Country(
            "tj",
            "+992",
            "Tajikistan",
            Res.drawable.tj,
        ),
        Country(
            "tk",
            "+690",
            "Tokelau",
            Res.drawable.tk,
        ),
        Country(
            "tl",
            "+670",
            "Timor-leste",
            Res.drawable.tl,
        ),
        Country(
            "tm",
            "+993",
            "Turkmenistan",
            Res.drawable.tm,
        ),
        Country(
            "tn",
            "+216",
            "Tunisia",
            Res.drawable.tn,
        ),
        Country(
            "to",
            "+676",
            "Tonga",
            Res.drawable.to,
        ),
        Country(
            "tr",
            "+90",
            "Turkey",
            Res.drawable.tr,
        ),
        Country(
            "tt",
            "+1",
            "Trinidad & Tobago",
            Res.drawable.tt,
        ),
        Country(
            "tv",
            "+688",
            "Tuvalu",
            Res.drawable.tv,
        ),
        Country(
            "tw",
            "+886",
            "Taiwan",
            Res.drawable.tw,
        ),
        Country(
            "tz",
            "+255",
            "Tanzania, United Republic Of",
            Res.drawable.tz,
        ),
        Country(
            "ua",
            "+380",
            "Ukraine",
            Res.drawable.ua,
        ),
        Country(
            "ug",
            "+256",
            "Uganda",
            Res.drawable.ug,
        ),
        Country(
            "us",
            "+1",
            "United States",
            Res.drawable.us,
        ),
        Country(
            "uy",
            "+598",
            "Uruguay",
            Res.drawable.uy,
        ),
        Country(
            "uz",
            "+998",
            "Uzbekistan",
            Res.drawable.uz,
        ),
        Country(
            "va",
            "+379",
            "Holy See (vatican City State)",
            Res.drawable.va,
        ),
        Country(
            "vc",
            "+1",
            "Saint Vincent & The Grenadines",
            Res.drawable.vc,
        ),
        Country(
            "ve",
            "+58",
            "Venezuela, Bolivarian Republic Of",
            Res.drawable.ve,
        ),
        Country(
            "vg",
            "+1",
            "British Virgin Islands",
            Res.drawable.vg,
        ),
        Country(
            "vi",
            "+1",
            "US Virgin Islands",
            Res.drawable.vi,
        ),
        Country(
            "vn",
            "+84",
            "Vietnam",
            Res.drawable.vn,
        ),
        Country(
            "vu",
            "+678",
            "Vanuatu",
            Res.drawable.vu,
        ),
        Country(
            "wf",
            "+681",
            "Wallis And Futuna",
            Res.drawable.wf,
        ),
        Country(
            "ws",
            "+685",
            "Samoa",
            Res.drawable.ws,
        ),
        Country(
            "xk",
            "+383",
            "Kosovo",
            Res.drawable.xk,
        ),
        Country(
            "ye",
            "+967",
            "Yemen",
            Res.drawable.ye,
        ),
        Country(
            "yt",
            "+262",
            "Mayotte",
            Res.drawable.yt,
        ),
        Country(
            "za",
            "+27",
            "South Africa",
            Res.drawable.za,
        ),
        Country(
            "zm",
            "+260",
            "Zambia",
            Res.drawable.zm,
        ),
        Country(
            "zw",
            "+263",
            "Zimbabwe",
            Res.drawable.zw,
        ),
    ).sortedBy { it.name }

    /**
     * [getNumberHint] Returns the hint of the country. [countryName] The name
     * of the country.
     *
     * @param countryName The name of the country.
     */
    fun getNumberHint(countryName: String): StringResource = when (countryName) {
        "ad" -> Res.string.andorra_hint
        "ae" -> Res.string.united_arab_emirates_hint
        "af" -> Res.string.afganistan_hint
        "ag" -> Res.string.antigua_and_barbuda_hint
        "ai" -> Res.string.anguilla_hint
        "al" -> Res.string.albania_hint
        "am" -> Res.string.armenia_hint
        "ao" -> Res.string.angola_hint
        "aq" -> Res.string.antarctica_hint
        "ar" -> Res.string.argentina_hint
        "as" -> Res.string.american_samoa_hint
        "at" -> Res.string.austria_hint
        "au" -> Res.string.australia_hint
        "aw" -> Res.string.aruba_hint
        "ax" -> Res.string.aland_islands_hint
        "az" -> Res.string.azerbaijan_hint
        "ba" -> Res.string.bosnia_hint
        "bb" -> Res.string.barbados_hint
        "bd" -> Res.string.bangladesh_hint
        "be" -> Res.string.belgium_hint
        "bf" -> Res.string.burkina_faso_hint
        "bg" -> Res.string.bulgaria_hint
        "bh" -> Res.string.bahrain_hint
        "bi" -> Res.string.burundi_hint
        "bj" -> Res.string.benin_hint
        "bl" -> Res.string.saint_barhelemy_hint
        "bm" -> Res.string.bermuda_hint
        "bn" -> Res.string.brunei_darussalam_hint
        "bo" -> Res.string.bolivia_hint
        "br" -> Res.string.brazil_hint
        "bs" -> Res.string.bahamas_hint
        "bt" -> Res.string.bhutan_hint
        "bw" -> Res.string.botswana_hint
        "by" -> Res.string.belarus_hint
        "bz" -> Res.string.belize_hint
        "ca" -> Res.string.canada_hint
        "cc" -> Res.string.cocos_hint
        "cd" -> Res.string.congo_democratic_hint
        "cf" -> Res.string.central_african_hint
        "cg" -> Res.string.congo_hint
        "ch" -> Res.string.switzerland_hint
        "ci" -> Res.string.cote_dlvoire_hint
        "ck" -> Res.string.cook_islands_hint
        "cl" -> Res.string.chile_hint
        "cm" -> Res.string.cameroon_hint
        "cn" -> Res.string.china_hint
        "co" -> Res.string.colombia_hint
        "cr" -> Res.string.costa_rica_hint
        "cu" -> Res.string.cuba_hint
        "cv" -> Res.string.cape_verde_hint
        "cw" -> Res.string.curacao_hint
        "cx" -> Res.string.christmas_island_hint
        "cy" -> Res.string.cyprus_hint
        "cz" -> Res.string.czech_republic_hint
        "de" -> Res.string.germany_hint
        "dj" -> Res.string.djibouti_hint
        "dk" -> Res.string.denmark_hint
        "dm" -> Res.string.dominica_hint
        "do" -> Res.string.dominician_republic_hint
        "dz" -> Res.string.algeria_hint
        "ec" -> Res.string.ecuador_hint
        "ee" -> Res.string.estonia_hint
        "eg" -> Res.string.egypt_hint
        "er" -> Res.string.eritrea_hint
        "es" -> Res.string.spain_hint
        "et" -> Res.string.ethiopia_hint
        "fi" -> Res.string.finland_hint
        "fj" -> Res.string.fiji_hint
        "fk" -> Res.string.falkland_islands_hint
        "fm" -> Res.string.micro_hint
        "fo" -> Res.string.faroe_islands_hint
        "fr" -> Res.string.france_hint
        "ga" -> Res.string.gabon_hint
        "gb" -> Res.string.united_kingdom_hint
        "gd" -> Res.string.grenada_hint
        "ge" -> Res.string.georgia_hint
        "gf" -> Res.string.french_guyana_hint
        "gg" -> Res.string.guernsey_hint
        "gh" -> Res.string.ghana_hint
        "gi" -> Res.string.unknown
        "gl" -> Res.string.greenland_hint
        "gm" -> Res.string.gambia_hint
        "gn" -> Res.string.guinea_hint
        "gp" -> Res.string.guadeloupe_hint
        "gq" -> Res.string.equatorial_guinea_hint
        "gr" -> Res.string.greece_hint
        "gt" -> Res.string.guatemala_hint
        "gu" -> Res.string.guam_hint
        "gw" -> Res.string.guinea_bissau_hint
        "gy" -> Res.string.guyana_hint
        "hk" -> Res.string.hong_kong_hint
        "hn" -> Res.string.honduras_hint
        "hr" -> Res.string.croatia_hint
        "ht" -> Res.string.haiti_hint
        "hu" -> Res.string.hungary_hint
        "id" -> Res.string.indonesia_hint
        "ie" -> Res.string.ireland_hint
        "il" -> Res.string.israil_hint
        "im" -> Res.string.isle_of_man
        "is" -> Res.string.iceland
        "in" -> Res.string.india_hint
        "io" -> Res.string.british_indian_ocean
        "iq" -> Res.string.iraq_hint
        "ir" -> Res.string.iran_hint
        "it" -> Res.string.italia_hint
        "je" -> Res.string.jersey_hint
        "jm" -> Res.string.jamaica_hint
        "jo" -> Res.string.jordan_hint
        "jp" -> Res.string.japan_hint
        "ke" -> Res.string.kenya_hint
        "kg" -> Res.string.kyrgyzstan_hint
        "kh" -> Res.string.cambodia_hint
        "ki" -> Res.string.kiribati
        "km" -> Res.string.comoros_hint
        "kn" -> Res.string.saint_kitts_hint
        "kp" -> Res.string.north_korea_hint
        "kr" -> Res.string.south_korea_hint
        "kw" -> Res.string.kuwait_hint
        "ky" -> Res.string.cayman_islands_hint
        "kz" -> Res.string.kazakhstan_hint
        "la" -> Res.string.laos_hint
        "lb" -> Res.string.lebanon_hint
        "lc" -> Res.string.saint_lucia_hint
        "li" -> Res.string.liechtenstein
        "lk" -> Res.string.siri_lanka_hint
        "lr" -> Res.string.liberia_hint
        "ls" -> Res.string.lesotho_hint
        "lt" -> Res.string.lithuania_hint
        "lu" -> Res.string.luxembourg_hint
        "lv" -> Res.string.latvia_hint
        "ly" -> Res.string.libya_hint
        "ma" -> Res.string.marocco_hint
        "mc" -> Res.string.monaco_hint
        "md" -> Res.string.moldova_hint
        "me" -> Res.string.montenegro_hint
        "mf" -> Res.string.saint_martin_hint
        "mg" -> Res.string.madagascar_hint
        "mh" -> Res.string.marshall_islands_hint
        "mk" -> Res.string.north_macedonia_hint
        "ml" -> Res.string.mali_hint
        "mm" -> Res.string.myanmar_hint
        "mn" -> Res.string.mongolia_hint
        "mo" -> Res.string.macau_hint
        "mp" -> Res.string.northern_mariana_hint
        "mq" -> Res.string.martinique_hint
        "mr" -> Res.string.mauriatana_hint
        "ms" -> Res.string.montserrat_hint
        "mt" -> Res.string.malta_hint
        "mu" -> Res.string.mauritius_hint
        "mv" -> Res.string.maldives_hint
        "mw" -> Res.string.malawi_hint
        "mx" -> Res.string.mexico_hint
        "my" -> Res.string.malaysia_hint
        "mz" -> Res.string.mozambique_hint
        "na" -> Res.string.namibia_hint
        "nc" -> Res.string.new_caledonia_hint
        "ne" -> Res.string.niger_hint
        "nf" -> Res.string.norfolk_hint
        "ng" -> Res.string.nigeria_hint
        "ni" -> Res.string.nicaragua
        "nl" -> Res.string.netherlands_hint
        "no" -> Res.string.norway_hint
        "np" -> Res.string.nepal_hint
        "nr" -> Res.string.nauru_hint
        "nu" -> Res.string.niue_hint
        "nz" -> Res.string.new_zealand_hint
        "om" -> Res.string.oman_hint
        "pa" -> Res.string.panama_hint
        "pe" -> Res.string.peru_hint
        "pf" -> Res.string.french_polynesia_hint
        "pg" -> Res.string.papua_new_guinea_hint
        "ph" -> Res.string.philippinies_hint
        "pk" -> Res.string.pakistan_hint
        "pl" -> Res.string.poland_hint
        "pm" -> Res.string.saint_pierre_hint
        "pn" -> Res.string.pitcairn
        "pr" -> Res.string.puerto_rico_hint
        "ps" -> Res.string.state_of_palestine_hint
        "pt" -> Res.string.portugal_hint
        "pw" -> Res.string.palau_hint
        "py" -> Res.string.paraguay_hint
        "qa" -> Res.string.qatar_hint
        "re" -> Res.string.reunion_hint
        "ro" -> Res.string.romania_hint
        "rs" -> Res.string.serbia_hint
        "ru" -> Res.string.russia_hint
        "rw" -> Res.string.rwanda_hint
        "sa" -> Res.string.saudi_arabia_hint
        "sb" -> Res.string.solomon_islands_hint
        "sc" -> Res.string.seychelles_hint
        "sd" -> Res.string.sudan_hint
        "se" -> Res.string.sweden_hint
        "sg" -> Res.string.singapore_hint
        "sh" -> Res.string.saint_helena_hint
        "si" -> Res.string.slovenia_hint
        "sk" -> Res.string.slovakia_hint
        "sl" -> Res.string.sierra_leone_hint
        "sm" -> Res.string.san_marino_hint
        "sn" -> Res.string.senegal_hint
        "so" -> Res.string.somali_hint
        "sr" -> Res.string.suriname_hint
        "ss" -> Res.string.south_sudan_hint
        "st" -> Res.string.sao_tome_hint
        "sv" -> Res.string.el_salvador_hint
        "sx" -> Res.string.sint_maarten_hint
        "sy" -> Res.string.syrian_hint
        "sz" -> Res.string.swaziland_hint
        "tc" -> Res.string.turks_and_caicos_hint
        "td" -> Res.string.chad_hint
        "tg" -> Res.string.togo_hint
        "th" -> Res.string.thailand_hint
        "tj" -> Res.string.taijikistan_hint
        "tk" -> Res.string.tokelau_hint
        "tl" -> Res.string.timor_leste_hint
        "tm" -> Res.string.turkmenistan_hint
        "tn" -> Res.string.tunisia_hint
        "to" -> Res.string.tonga_hint
        "tr" -> Res.string.turkey_hint
        "tt" -> Res.string.trinidad_and_tobago_hint
        "tv" -> Res.string.tuvalu_hint
        "tw" -> Res.string.taiwan_hint
        "tz" -> Res.string.tazmania_hint
        "ua" -> Res.string.ukraina_hint
        "ug" -> Res.string.uganda_hint
        "us" -> Res.string.united_states_america_hint
        "uy" -> Res.string.uruguay_hint
        "uz" -> Res.string.uzbekistan_hint
        "va" -> Res.string.holy_see
        "vc" -> Res.string.saint_vincent_hint
        "ve" -> Res.string.venezuela_hint
        "vg" -> Res.string.virgin_islands_hint
        "vi" -> Res.string.virgin_island_us
        "vn" -> Res.string.vietnam_hint
        "vu" -> Res.string.vanuatu_hint
        "wf" -> Res.string.walli_and_fatuna_hint
        "ws" -> Res.string.samoa_hint
        "xk" -> Res.string.kosovo_hint
        "ye" -> Res.string.yemen_hint
        "yt" -> Res.string.mayotte_hint
        "za" -> Res.string.south_africa_hint
        "zm" -> Res.string.zambia_hint
        "zw" -> Res.string.zimbabwe_hint
        else -> Res.string.unknown
    }
}
