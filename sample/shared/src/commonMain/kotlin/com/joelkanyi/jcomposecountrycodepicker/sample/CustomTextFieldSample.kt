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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.joelkanyi.jcomposecountrycodepicker.annotation.RestrictedApi
import com.joelkanyi.jcomposecountrycodepicker.component.CountrySelectionDialog
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState
import org.jetbrains.compose.resources.painterResource

@OptIn(RestrictedApi::class)
@Composable
internal fun CustomTextFieldSample() {
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val state = rememberKomposeCountryCodePickerState(
        showCountryCode = true,
        showCountryFlag = true,
    )

    if (showDialog) {
        CountrySelectionDialog(
            countryList = state.countryList,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            onDismissRequest = { showDialog = false },
            onSelect = { country ->
                state.setCode(country.code)
                showDialog = false
            },
        )
    }

    LazyColumn(
        modifier = Modifier
            .widthIn(max = 480.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(vertical = 24.dp),
    ) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Build your own phone input using CountrySelectionDialog " +
                    "and state.selectedCountry — no KomposeCountryCodePicker needed.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        // -- Example 1: OutlinedTextField with country picker leading icon --
        item {
            SectionHeader(
                title = "With OutlinedTextField",
                subtitle = "Your own OutlinedTextField with the flag and phone code as a leading icon",
            )
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                placeholder = {
                    Text(
                        text = "Phone Number",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                ),
                leadingIcon = {
                    Row(
                        modifier = Modifier
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                            ) { showDialog = true }
                            .padding(start = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            modifier = Modifier
                                .size(width = 28.dp, height = 18.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            painter = painterResource(state.selectedCountry.flag),
                            contentDescription = state.selectedCountry.name,
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = state.selectedCountry.phoneNoCode,
                            style = MaterialTheme.typography.bodyLarge,
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        // Dropdown chevron
                        Text(
                            text = "\u25BE",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        // Vertical separator
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(24.dp)
                                .background(
                                    MaterialTheme.colorScheme.outlineVariant,
                                ),
                        )
                    }
                },
            )
        }

        // -- Example 2: BasicTextField with custom styling --
        item {
            SectionHeader(
                title = "With BasicTextField",
                subtitle = "Fully custom design — your own borders, background, and layout",
            )
        }

        item {
            BasicTextFieldWithCountryPicker(
                phoneNumber = phoneNumber,
                onPhoneNumberChange = { phoneNumber = it },
                flag = {
                    Image(
                        modifier = Modifier
                            .size(width = 28.dp, height = 18.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        painter = painterResource(state.selectedCountry.flag),
                        contentDescription = state.selectedCountry.name,
                    )
                },
                countryCode = state.selectedCountry.phoneNoCode,
                onCountryClick = { showDialog = true },
            )
        }

        // -- Selected Country Info --
        item {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.outlineVariant,
            )
        }

        item {
            SectionHeader(
                title = "state.selectedCountry",
                subtitle = "All the data you get from the selected country — flag, name, code, and phone code",
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                ),
                shape = RoundedCornerShape(12.dp),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Image(
                            modifier = Modifier
                                .size(width = 40.dp, height = 26.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            painter = painterResource(state.selectedCountry.flag),
                            contentDescription = state.selectedCountry.name,
                        )
                        Column {
                            Text(
                                text = state.selectedCountry.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                ),
                            )
                            Text(
                                text = "${state.selectedCountry.phoneNoCode} (${state.selectedCountry.code.uppercase()})",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                    DetailRow(label = "Country Code", value = state.selectedCountry.code.uppercase())
                    DetailRow(label = "Phone Code", value = state.selectedCountry.phoneNoCode)
                    DetailRow(label = "Country Name", value = state.selectedCountry.name)
                    if (phoneNumber.isNotBlank()) {
                        DetailRow(
                            label = "Full Number",
                            value = "${state.getCountryPhoneCode()}$phoneNumber",
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
            ),
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun BasicTextFieldWithCountryPicker(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    flag: @Composable () -> Unit,
    countryCode: String,
    onCountryClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(12.dp),
            )
            .clip(RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Country selector section
        Row(
            modifier = Modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                ) { onCountryClick() }
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            flag()

            Text(
                text = countryCode,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                ),
            )

            Text(
                text = "\u25BE",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        // Vertical separator
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(32.dp)
                .background(MaterialTheme.colorScheme.outlineVariant),
        )

        // Phone number input
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            if (phoneNumber.isEmpty()) {
                Text(
                    text = "Phone Number",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                )
            }
            BasicTextField(
                value = phoneNumber,
                onValueChange = onPhoneNumberChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            )
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
            ),
        )
    }
}
