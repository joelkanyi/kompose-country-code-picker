package com.joelkanyi.jcomposecountrycodepicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.joelkanyi.jcomposecountrycodepicker.component.ComposeCountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.getCountryCodeWithoutPrefix
import com.joelkanyi.jcomposecountrycodepicker.component.getCountryName
import com.joelkanyi.jcomposecountrycodepicker.component.getCountryPhoneCode
import com.joelkanyi.jcomposecountrycodepicker.component.getCountryPhoneCodeWithoutPrefix
import com.joelkanyi.jcomposecountrycodepicker.component.getFullPhoneNumber
import com.joelkanyi.jcomposecountrycodepicker.component.getFullPhoneNumberWithoutPrefix
import com.joelkanyi.jcomposecountrycodepicker.component.getPhoneNumber
import com.joelkanyi.jcomposecountrycodepicker.component.getPhoneNumberWithoutPrefix
import com.joelkanyi.jcomposecountrycodepicker.component.isPhoneNumberValid
import com.joelkanyi.jcomposecountrycodepicker.ui.theme.ComposePickerTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
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
                val context = LocalContext.current

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Jetpack Compose Country Code Picker",
                                    style = MaterialTheme.typography.titleSmall,
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
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        CountryCodePick()

                        Spacer(modifier = Modifier.height(16.dp))

                        /**
                         * Call functions to get this values
                         * +254
                         * 254
                         * 0706003891
                         * 706003891
                         * +254706003891
                         * 254706003891
                         * KE
                         * Kenya
                         */

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
                                text = getCountryPhoneCodeWithoutPrefix(),
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
                                text = getCountryPhoneCode(),
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
                                text = getCountryName(),
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
                                text = getCountryCodeWithoutPrefix().uppercase(),
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
                                text = getPhoneNumber(),
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
                                text = getPhoneNumberWithoutPrefix(),
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
                                text = getFullPhoneNumber(),
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
                                text = getFullPhoneNumberWithoutPrefix(),
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
                                text = if (isPhoneNumberValid()) "Valid" else "Invalid",
                                color = if (isPhoneNumberValid()) Color.Green else Color.Red,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CountryCodePick() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val phoneNumber = rememberSaveable { mutableStateOf("") }

        ComposeCountryCodePicker(
            modifier = Modifier
                .fillMaxWidth(),
            text = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            placeholder = { Text(text = "Phone Number") },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
            ),
        )
    }
}
