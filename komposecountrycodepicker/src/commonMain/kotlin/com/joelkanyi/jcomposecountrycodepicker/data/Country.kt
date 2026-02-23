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
package com.joelkanyi.jcomposecountrycodepicker.data

import org.jetbrains.compose.resources.DrawableResource

/**
 * [Country] holds the data of a country.
 *
 * This class is intentionally not a `data class` to preserve binary
 * compatibility when properties are added or reordered in future versions.
 *
 * @param code The ISO 3166-1 alpha-2 code of the country (e.g. "ke").
 * @param phoneNoCode The phone number code of the country (e.g. "+254").
 * @param name The display name of the country (e.g. "Kenya").
 * @param flag The flag drawable resource of the country.
 */
public class Country(
    public val code: String,
    public val phoneNoCode: String,
    public val name: String,
    public val flag: DrawableResource,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Country) return false
        return code == other.code &&
            phoneNoCode == other.phoneNoCode &&
            name == other.name &&
            flag == other.flag
    }

    override fun hashCode(): Int {
        var result = code.hashCode()
        result = 31 * result + phoneNoCode.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + flag.hashCode()
        return result
    }

    override fun toString(): String = "Country(code=$code, phoneNoCode=$phoneNoCode, name=$name)"
}
