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
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joelkanyi.jcomposecountrycodepicker.component.CustomComposeCountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState
import com.joelkanyi.jcomposecountrycodepicker.ui.theme.ComposePickerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ComposePickerTheme {
                PickerContent()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PickerContent() {
    var error by remember { mutableStateOf(false) }
    Scaffold(modifier = Modifier
        .fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(text = "Custom Country Code Picker",
                    style = MaterialTheme.typography.titleMedium)
            })
        }) { paddingValues ->
        var phoneNumber by rememberSaveable { mutableStateOf("") }
        val state = rememberKomposeCountryCodePickerState(
            showCountryCode = true,
            showCountryFlag = true,
            defaultCountryCode = "US")

        LazyColumn(modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = paddingValues) {
            item {
                CustomComposeCountryCodePicker(modifier = Modifier.fillMaxWidth(),
                    phoneNumber = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                        error = false
                    },
                    phoneNumberTextStyle = TextStyle.Default.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal),
                    colors = getColors(),
                    error = error,
                    state = state,
                    countrySelectionDialogContentColor = Color.Black,
                    interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Release) {
                                    Log.e("CountryCodePicker",
                                        "clicked")
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(4.dp),
                    focusedBorderThickness = 1.dp,
                    unfocusedBorderThickness = 1.dp,
                    flagPaddingValues = PaddingValues(start = 16.dp),
                    countryPaddingValues = PaddingValues(start = 12.dp),
                    countryCodeTextStyle = TextStyle.Default.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black),
                    defaultPlaceholderTextColor = Color.Gray,
                    defaultPlaceholderTextStyle = TextStyle(fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        letterSpacing = 0.5.sp),
                    countrySelectionDialogModifier = Modifier,
                    countrySelectionDialogTitleTextStr = null,
                    countrySelectionDialogTitleTextStyle = null,
                    countrySelectionDialogNavigationIconImageVector = null,
                    countrySelectionDialogSearchPlaceholderTextStr = null,
                    countrySelectionDialogSearchPlaceholderTextColor = Color.Black,
                    countrySelectionDialogSearchPlaceholderTextStyle = LocalTextStyle.current,
                    countrySelectionDialogSearchTextFieldColors = TextFieldDefaults.colors(
                        disabledContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray,
                        cursorColor = Color.Black,
                    ),
                    countrySelectionDialogSearchFieldBorderWidth = 1.dp,
                    countrySelectionDialogSearchFieldBorderColorShape = RoundedCornerShape(4.dp),
                    countrySelectionDialogSearchTextFieldStyle = null,
                    countrySelectionDialogSearchIconImageVector = null,
                    countrySelectionDialogCountryListItemModifier = Modifier,
                    countrySelectionDialogFlagIconWidth = 30.dp)
            }

            item {
                TextButton(modifier = Modifier.padding(16.dp),
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    onClick = {
                        error = !state.isPhoneNumberValid()
                    }) {
                    Text(text = "Validate",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black)
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    /** Prefixed country code */
                    /** Prefixed country code */
                    Text(text = "Prefixed Country Phone No Code: ",
                        style = MaterialTheme.typography.bodySmall)
                    Text(text = state.getCountryPhoneCode(),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold))
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    /** Get country name */
                    /** Get country name */
                    Text(text = "Country Name: ",
                        style = MaterialTheme.typography.bodySmall)
                    Text(text = state.getCountryName(),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold))
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    /** Get country language code */
                    /** Get country language code */
                    Text(text = "Country Language Code: ",
                        style = MaterialTheme.typography.bodySmall)
                    Text(text = state.countryCode
                        .uppercase(),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold))
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    /** Get phone number */
                    /** Get phone number */
                    Text(text = "Phone Number: ",
                        style = MaterialTheme.typography.bodySmall)
                    Text(text = state.phoneNumber,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold))
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    /** Get phone number without prefix */
                    /** Get phone number without prefix */
                    Text(text = "Phone Number Without Prefix: ",
                        style = MaterialTheme.typography.bodySmall)
                    Text(text = state.getPhoneNumberWithoutPrefix(),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold))
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    /** Get full phone number */
                    /** Get full phone number */
                    Text(text = "Full Phone Number: ",
                        style = MaterialTheme.typography.bodySmall)
                    Text(text = state.getFullPhoneNumber(),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold))
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    /** Get full phone number without prefix */
                    /** Get full phone number without prefix */
                    Text(text = "Full Phone Number Without Prefix: ",
                        style = MaterialTheme.typography.bodySmall)
                    Text(text = state.getFullPhoneNumberWithoutPrefix(),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold))
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    /** Get full phone number without prefix */
                    /** Get full phone number without prefix */
                    Text(text = "Fully Formatted Phone Number: ",
                        style = MaterialTheme.typography.bodySmall)
                    Text(text = state.getFullyFormattedPhoneNumber(),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold))
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    /** Check if phone number is valid */
                    /** Check if phone number is valid */
                    Text(text = "Phone Number State: ",
                        style = MaterialTheme.typography.bodySmall)
                    Text(text = if (state.isPhoneNumberValid()) "Valid" else "Invalid",
                        color = if (state.isPhoneNumberValid()) Color.Green else Color.Red,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.ExtraBold))
                }
            }
        }
    }
}

@Composable
private fun getColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        disabledTextColor = Color.Gray,
        errorTextColor = Color.Red,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        disabledContainerColor = Color.LightGray,
        errorContainerColor = Color.White,
        cursorColor = Color.Black,
        focusedSupportingTextColor = Color.Black,
        unfocusedPlaceholderColor = Color.Gray,
        focusedPlaceholderColor = Color.Gray,
        errorPlaceholderColor = Color.Red,
        unfocusedIndicatorColor = Color.LightGray,
        disabledIndicatorColor = Color.LightGray,
        errorIndicatorColor = Color.Red)
}

@Preview
@Composable
private fun Preview() {
    ComposePickerTheme {
        PickerContent()
    }
}
