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

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.joelkanyi.jcomposecountrycodepicker.data.Transformation

/**
 * [PhoneNumberTransformation] is a visual transformation that formats the
 * phone number.
 *
 * @param countryCode The country code of the phone number.
 */
internal class PhoneNumberTransformation(
    private val countryCode: String,
) : VisualTransformation {

    private val phoneNumberFormatter = createAsYouTypeFormatter(countryCode)

    /**
     * [filter] Returns the formatted phone number.
     *
     * @param text The phone number.
     */
    override fun filter(text: AnnotatedString): TransformedText {
        val transformation = try {
            reformat(text, text.length)
        } catch (e: Exception) {
            Transformation(text.text, List(text.length) { it }, List(text.length) { it })
        }

        return TransformedText(
            AnnotatedString(transformation.formatted ?: text.text),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int = transformation.originalToTransformed[offset.coerceIn(transformation.originalToTransformed.indices)]

                override fun transformedToOriginal(offset: Int): Int = transformation.transformedToOriginal[offset.coerceIn(transformation.transformedToOriginal.indices)]
            },
        )
    }

    /**
     * [reformat] Returns the formatted phone number.
     *
     * @param s The phone number.
     * @param cursor The cursor position.
     */
    private fun reformat(s: CharSequence, cursor: Int): Transformation = try {
        phoneNumberFormatter.clear()

        val curIndex = cursor - 1
        var formatted: String? = null
        var lastNonSeparator = 0.toChar()
        var hasCursor = false

        s.forEachIndexed { index, char ->
            if (isNonSeparator(char)) {
                if (lastNonSeparator.code != 0) {
                    formatted = getFormattedNumber(lastNonSeparator, hasCursor)
                    hasCursor = false
                }
                lastNonSeparator = char
            }
            if (index == curIndex) {
                hasCursor = true
            }
        }

        if (lastNonSeparator.code != 0) {
            formatted = getFormattedNumber(lastNonSeparator, hasCursor)
        }

        val originalToTransformed = mutableListOf<Int>()
        val transformedToOriginal = mutableListOf<Int>()
        var specialCharsCount = 0

        formatted?.forEachIndexed { index, char ->
            if (!isNonSeparator(char)) {
                specialCharsCount++
            } else {
                originalToTransformed.add(index)
            }
            transformedToOriginal.add(maxOf(index - specialCharsCount, 0))
        }

        val lastOriginalOffset = s.length
        val lastTransformedOffset = formatted?.length ?: 0

        originalToTransformed.add(lastTransformedOffset)
        transformedToOriginal.add(lastOriginalOffset)

        Transformation(formatted, originalToTransformed, transformedToOriginal)
    } catch (e: Exception) {
        Transformation(s.toString(), List(s.length) { it }, List(s.length) { it })
    }

    /**
     * [getFormattedNumber] Returns the formatted phone number.
     *
     * @param lastNonSeparator The last non separator character of the phone
     *    number.
     * @param hasCursor If true, the cursor is at the end of the phone number.
     */
    private fun getFormattedNumber(lastNonSeparator: Char, hasCursor: Boolean): String? = if (hasCursor) {
        phoneNumberFormatter.inputDigitAndRememberPosition(lastNonSeparator)
    } else {
        phoneNumberFormatter.inputDigit(lastNonSeparator)
    }
}
