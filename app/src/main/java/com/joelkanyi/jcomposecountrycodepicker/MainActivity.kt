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
package com.joelkanyi.jcomposecountrycodepicker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.joelkanyi.jcomposecountrycodepicker.component.KomposeCountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState
import com.joelkanyi.jcomposecountrycodepicker.ui.theme.ComposePickerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePickerTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(
                    color = MaterialTheme.colorScheme.primary,
                    false,
                )
                systemUiController.setSystemBarsColor(
                    color = MaterialTheme.colorScheme.primary,
                    false,
                )
                PickerContent()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PickerContent() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Kompose Country Code Picker",
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            var phoneNumber by rememberSaveable { mutableStateOf("") }
            val state = rememberKomposeCountryCodePickerState(
                // limitedCountries = listOf("KE", "UG", "TZ", "RW", "SS"),
                showCountryCode = true,
                showCountryFlag = true,
                // defaultCountryCode = "KE",
            )

            KomposeCountryCodePicker(
                modifier = Modifier
                    .fillMaxWidth(),
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
                countrySelectionDialogContainerColor = MaterialTheme.colorScheme.background,
                countrySelectionDialogContentColor = MaterialTheme.colorScheme.onBackground,
                interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                Log.e(
                                    "CountryCodePicker",
                                    "clicked",
                                )
                            }
                        }
                    }
                },
            )

            /*TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = phoneNumber.value,
                onValueChange = { phoneNumber.value = it },
                placeholder = { Text(text = "Phone Number") },
                leadingIcon = {
                    KomposeCountryCodePicker(
                        modifier = Modifier,
                        showOnlyCountryCodePicker = true,
                        text = phoneNumber.value,
                        state = state,
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                ),
            )*/

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                /**
                 * Get country code
                 */
                Text(text = "Country Phone No Code: ")
                Text(
                    text = state.getCountryPhoneCodeWithoutPrefix(),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                /**
                 * Prefixed country code
                 */
                Text(text = "Prefixed Country Phone No Code: ")
                Text(
                    text = state.getCountryPhoneCode(),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                /**
                 * Get country name
                 */
                Text(text = "Country Name: ")
                Text(
                    text = state.getCountryName(),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                /**
                 * Get country language code
                 */
                Text(text = "Country Language Code: ")
                Text(
                    text = state.countryCode
                        .uppercase(),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                /**
                 * Get phone number
                 */
                Text(text = "Phone Number: ")
                Text(
                    text = state.phoneNumber,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                /**
                 * Get phone number without prefix
                 */
                Text(text = "Phone Number Without Prefix: ")
                Text(
                    text = state.getPhoneNumberWithoutPrefix(),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                /**
                 * Get full phone number
                 */
                Text(text = "Full Phone Number: ")
                Text(
                    text = state.getFullPhoneNumber(),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                /**
                 * Get full phone number without prefix
                 */
                Text(text = "Full Phone Number Without Prefix: ")
                Text(
                    text = state.getFullPhoneNumberWithoutPrefix(),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                /**
                 * Check if phone number is valid
                 */
                Text(text = "Phone Number State: ")
                Text(
                    text = if (state.isPhoneNumberValid()) "Valid" else "Invalid",
                    color = if (state.isPhoneNumberValid()) Color.Green else Color.Red,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ComposePickerTheme {
        PickerContent()
    }
}
