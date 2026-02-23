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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.joelkanyi.jcomposecountrycodepicker.component.KomposeCountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState

@Composable
internal fun LoginSample() {
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var phoneError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }
    var loginMessage by rememberSaveable { mutableStateOf<String?>(null) }
    val state = rememberKomposeCountryCodePickerState(
        showCountryCode = true,
        showCountryFlag = true,
    )

    Column(
        modifier = Modifier
            .widthIn(max = 480.dp)
            .padding(horizontal = 16.dp)
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
            ),
        )

        Text(
            text = "Sign in with your phone number",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(8.dp))

        KomposeCountryCodePicker(
            modifier = Modifier.fillMaxWidth(),
            text = phoneNumber,
            onValueChange = {
                phoneNumber = it
                phoneError = null
                loginMessage = null
            },
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
            error = phoneError != null,
        )
        if (phoneError != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = phoneError!!,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
            )
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
                passwordError = null
                loginMessage = null
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            isError = passwordError != null,
            supportingText = if (passwordError != null) {
                { Text(passwordError!!) }
            } else {
                null
            },
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            onClick = {
                var hasError = false

                // Validate phone number
                if (phoneNumber.isBlank()) {
                    phoneError = "Phone number is required"
                    hasError = true
                } else if (!state.isPhoneNumberValid()) {
                    phoneError = "Invalid phone number"
                    hasError = true
                } else {
                    phoneError = null
                }

                // Validate password
                if (password.isBlank()) {
                    passwordError = "Password is required"
                    hasError = true
                } else {
                    passwordError = null
                }

                if (!hasError) {
                    loginMessage = "Login successful! Phone: ${state.getFullPhoneNumber()}"
                }
            },
            shape = MaterialTheme.shapes.medium,
        ) {
            Text("Login")
        }

        if (loginMessage != null) {
            Text(
                text = loginMessage!!,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
