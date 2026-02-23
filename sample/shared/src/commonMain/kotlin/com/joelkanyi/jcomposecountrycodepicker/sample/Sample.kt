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
package com.joelkanyi.jcomposecountrycodepicker.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joelkanyi.jcomposecountrycodepicker.component.KomposeCountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState

@Composable
fun Sample(modifier: Modifier = Modifier) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TabRow(
                modifier = Modifier.statusBarsPadding(),
                selectedTabIndex = selectedTab,
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Picker") },
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Login") },
                )
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter,
        ) {
            when (selectedTab) {
                0 -> PickerSample()
                1 -> LoginSample()
            }
        }
    }
}

@Composable
private fun PickerSample() {
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    val state = rememberKomposeCountryCodePickerState(
        showCountryCode = true,
        showCountryFlag = true,
    )

    LazyColumn(
        modifier = Modifier
            .widthIn(max = 480.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
        item {
            KomposeCountryCodePicker(
                modifier = Modifier.fillMaxWidth(),
                text = phoneNumber,
                onValueChange = { phoneNumber = it },
                placeholder = {
                    Text(
                        text = "Phone Number",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.ExtraLight,
                        ),
                    )
                },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                ),
                state = state,
            )
        }

        item {
            InfoRow(
                label = "Country Phone No Code:",
                value = state.getCountryPhoneCodeWithoutPrefix(),
            )
        }

        item {
            InfoRow(
                label = "Prefixed Country Phone No Code:",
                value = state.getCountryPhoneCode(),
            )
        }

        item {
            InfoRow(
                label = "Country Name:",
                value = state.getCountryName(),
            )
        }

        item {
            InfoRow(
                label = "Country Language Code:",
                value = state.countryCode.uppercase(),
            )
        }

        item {
            InfoRow(
                label = "Phone Number:",
                value = state.phoneNumber,
            )
        }

        item {
            InfoRow(
                label = "Phone Number Without Prefix:",
                value = state.getPhoneNumberWithoutPrefix(),
            )
        }

        item {
            InfoRow(
                label = "Full Phone Number:",
                value = state.getFullPhoneNumber(),
            )
        }

        item {
            InfoRow(
                label = "Full Phone Number Without Prefix:",
                value = state.getFullPhoneNumberWithoutPrefix(),
            )
        }

        item {
            InfoRow(
                label = "Fully Formatted Phone Number:",
                value = state.getFullyFormattedPhoneNumber(),
            )
        }

        item {
            val isValid = state.isPhoneNumberValid()
            InfoRow(
                label = "Phone Number State:",
                value = if (isValid) "Valid" else "Invalid",
                valueColor = if (isValid) Color.Green else Color.Red,
                valueFontWeight = FontWeight.ExtraBold,
            )
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    valueColor: Color = Color.Unspecified,
    valueFontWeight: FontWeight = FontWeight.Bold,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = value,
            color = valueColor,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = valueFontWeight,
            ),
        )
    }
}
