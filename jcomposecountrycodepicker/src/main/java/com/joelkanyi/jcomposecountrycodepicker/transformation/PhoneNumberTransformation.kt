package com.joelkanyi.jcomposecountrycodepicker.transformation

import android.telephony.PhoneNumberUtils
import android.text.Selection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.*

/**
 * [PhoneNumberTransformation] is a visual transformation that formats the phone number.
 * [countryCode] The country code of the phone number.
 */
class PhoneNumberTransformation(countryCode: String = Locale.getDefault().country) :
    VisualTransformation {

    private val phoneNumberFormatter =
        PhoneNumberUtil.getInstance().getAsYouTypeFormatter(countryCode)

    /**
     * [filter] Returns the formatted phone number.
     * [text] The phone number.
     */
    override fun filter(text: AnnotatedString): TransformedText {
        val transformation =
            reformat(text, Selection.getSelectionEnd(text))

        return TransformedText(
            AnnotatedString(transformation.formatted ?: ""),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return try {
                        transformation.originalToTransformed[offset]
                    } catch (ex: Exception) {
                        transformation.transformedToOriginal.lastIndex
                    }
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return transformation.transformedToOriginal[offset]
                }
            },
        )
    }

    /**
     * [reformat] Returns the formatted phone number.
     * [s] The phone number.
     * [cursor] The cursor position.
     */
    private fun reformat(s: CharSequence, cursor: Int): Transformation {
        phoneNumberFormatter.clear()

        val curIndex = cursor - 1
        var formatted: String? = null
        var lastNonSeparator = 0.toChar()
        var hasCursor = false

        s.forEachIndexed { index, char ->
            if (PhoneNumberUtils.isNonSeparator(char)) {
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
            if (!PhoneNumberUtils.isNonSeparator(char)) {
                specialCharsCount++
            } else {
                originalToTransformed.add(index)
            }
            transformedToOriginal.add(index - specialCharsCount)
        }
        originalToTransformed.add(originalToTransformed.maxOrNull()?.plus(1) ?: 0)
        transformedToOriginal.add(transformedToOriginal.maxOrNull()?.plus(1) ?: 0)

        return Transformation(formatted, originalToTransformed, transformedToOriginal)
    }

    /**
     * [getFormattedNumber] Returns the formatted phone number.
     * [lastNonSeparator] The last non separator character of the phone number.
     * [hasCursor] If true, the cursor is at the end of the phone number.
     */
    private fun getFormattedNumber(lastNonSeparator: Char, hasCursor: Boolean): String? {
        return if (hasCursor) {
            phoneNumberFormatter.inputDigitAndRememberPosition(lastNonSeparator)
        } else {
            phoneNumberFormatter.inputDigit(lastNonSeparator)
        }
    }

    /**
     * [Transformation] is a data class that holds the data of the formatted phone number.
     */
    private data class Transformation(
        val formatted: String?,
        val originalToTransformed: List<Int>,
        val transformedToOriginal: List<Int>,
    )
}
