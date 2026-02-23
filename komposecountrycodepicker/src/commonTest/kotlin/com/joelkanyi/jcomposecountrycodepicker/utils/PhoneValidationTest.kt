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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PhoneValidationTest {

    // --- Valid phone numbers ---

    @Test
    fun validUSPhoneNumber() {
        assertTrue(PhoneValidation.isValidPhoneNumber("+12025551234"))
    }

    @Test
    fun validUKPhoneNumber() {
        assertTrue(PhoneValidation.isValidPhoneNumber("+447911123456"))
    }

    @Test
    fun validKenyaPhoneNumber() {
        assertTrue(PhoneValidation.isValidPhoneNumber("+254712345678"))
    }

    @Test
    fun validGermanyPhoneNumber() {
        assertTrue(PhoneValidation.isValidPhoneNumber("+4915123456789"))
    }

    @Test
    fun validJapanPhoneNumber() {
        assertTrue(PhoneValidation.isValidPhoneNumber("+819012345678"))
    }

    @Test
    fun validIndiaPhoneNumber() {
        assertTrue(PhoneValidation.isValidPhoneNumber("+919876543210"))
    }

    @Test
    fun validBrazilPhoneNumber() {
        assertTrue(PhoneValidation.isValidPhoneNumber("+5511987654321"))
    }

    @Test
    fun validAustraliaPhoneNumber() {
        assertTrue(PhoneValidation.isValidPhoneNumber("+61412345678"))
    }

    @Test
    fun validFrancePhoneNumber() {
        assertTrue(PhoneValidation.isValidPhoneNumber("+33612345678"))
    }

    @Test
    fun validNigeriaPhoneNumber() {
        assertTrue(PhoneValidation.isValidPhoneNumber("+2348012345678"))
    }

    @Test
    fun validSouthAfricaPhoneNumber() {
        assertTrue(PhoneValidation.isValidPhoneNumber("+27821234567"))
    }

    @Test
    fun validChinaPhoneNumber() {
        assertTrue(PhoneValidation.isValidPhoneNumber("+8613912345678"))
    }

    // --- Invalid phone numbers ---

    @Test
    fun invalidMissingPlusPrefix() {
        assertFalse(PhoneValidation.isValidPhoneNumber("12025551234"))
    }

    @Test
    fun invalidEmptyString() {
        assertFalse(PhoneValidation.isValidPhoneNumber(""))
    }

    @Test
    fun invalidJustPlus() {
        assertFalse(PhoneValidation.isValidPhoneNumber("+"))
    }

    @Test
    fun invalidTooShortForUS() {
        // US numbers must be exactly 11 digits total
        assertFalse(PhoneValidation.isValidPhoneNumber("+120255512"))
    }

    @Test
    fun invalidTooLongForUS() {
        assertFalse(PhoneValidation.isValidPhoneNumber("+120255512345"))
    }

    @Test
    fun invalidTooShortForKenya() {
        // Kenya numbers must be 12 digits total
        assertFalse(PhoneValidation.isValidPhoneNumber("+25471234567"))
    }

    @Test
    fun invalidTooLongForKenya() {
        assertFalse(PhoneValidation.isValidPhoneNumber("+2547123456789"))
    }

    @Test
    fun invalidLettersInNumber() {
        assertFalse(PhoneValidation.isValidPhoneNumber("+1202abc1234"))
    }

    @Test
    fun validWithWhitespace() {
        // Leading/trailing whitespace should be trimmed
        assertTrue(PhoneValidation.isValidPhoneNumber("  +12025551234  "))
    }

    @Test
    fun validSamoaWithFixedCode() {
        // Samoa: +685 XXXXXXX to XXXXXXXXX (10-12 digits total)
        assertTrue(PhoneValidation.isValidPhoneNumber("+6857212345"))
    }
}
