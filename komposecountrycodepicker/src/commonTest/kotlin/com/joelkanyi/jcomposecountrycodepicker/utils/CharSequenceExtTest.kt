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
package com.joelkanyi.jcomposecountrycodepicker.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class CharSequenceExtTest {

    @Test
    fun unaccentRemovesAccents() {
        assertEquals("Cote d'Ivoire", "Côte d'Ivoire".unaccent())
    }

    @Test
    fun unaccentRemovesUmlaut() {
        assertEquals("Aland Islands", "Åland Islands".unaccent())
    }

    @Test
    fun unaccentHandlesPlainAscii() {
        assertEquals("Kenya", "Kenya".unaccent())
    }

    @Test
    fun unaccentHandlesEmptyString() {
        assertEquals("", "".unaccent())
    }

    @Test
    fun unaccentRemovesCedilla() {
        assertEquals("Curacao", "Curaçao".unaccent())
    }

    @Test
    fun unaccentRemovesTilde() {
        assertEquals("Sao Tome", "São Tomé".unaccent())
    }

    @Test
    fun unaccentRemovesReunionAccent() {
        assertEquals("Reunion", "Réunion".unaccent())
    }
}
