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
package com.joelkanyi.jcomposecountrycodepicker.data

import androidx.compose.ui.unit.Dp

/**
 * Holds the size of a country flag image.
 *
 * This class is intentionally not a `data class` to preserve binary
 * compatibility when properties are added or reordered in future versions.
 *
 * @param width The width of the flag in [Dp].
 * @param height The height of the flag in [Dp].
 */
public class FlagSize(
    public val width: Dp,
    public val height: Dp,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FlagSize) return false
        return width == other.width && height == other.height
    }

    override fun hashCode(): Int {
        var result = width.hashCode()
        result = 31 * result + height.hashCode()
        return result
    }

    override fun toString(): String = "FlagSize(width=$width, height=$height)"
}
