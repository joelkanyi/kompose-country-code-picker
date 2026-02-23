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
package com.joelkanyi.jcomposecountrycodepicker.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.joelkanyi.jcomposecountrycodepicker.data.Country
import com.joelkanyi.jcomposecountrycodepicker.resources.Res
import com.joelkanyi.jcomposecountrycodepicker.resources.ic_arrow_back
import com.joelkanyi.jcomposecountrycodepicker.resources.ic_search
import com.joelkanyi.jcomposecountrycodepicker.resources.search_country
import com.joelkanyi.jcomposecountrycodepicker.resources.select_country
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.searchForAnItem
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * [CountrySelectionDialog] is a composable that displays a dialog for
 * selecting a country.
 *
 * All lambda and composable parameters are invoked synchronously on the
 * main thread during composition. Exceptions thrown inside these lambdas
 * will propagate to the caller and may crash the composition.
 *
 * @param countryList The list of countries to be displayed in the dialog.
 * @param containerColor The color of the dialog container.
 * @param contentColor The color of the dialog content.
 * @param onDismissRequest Called on the main thread when the user
 *    requests to dismiss the dialog (e.g. back press, outside tap).
 *    Exceptions thrown in this lambda will propagate to the caller.
 * @param onSelect Called on the main thread when a country is selected.
 *    The selected [Country] is passed as a parameter. Exceptions thrown
 *    in this lambda will propagate to the caller.
 * @param modifier Modifier to be applied to the layout.
 * @param properties The properties of the dialog.
 * @param title A composable lambda to display the title of the dialog.
 * @param backIcon A composable lambda to display the back icon in the
 *    top app bar.
 * @param searchIcon A composable lambda to display the search icon in
 *    the top app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun CountrySelectionDialog(
    countryList: List<Country>,
    containerColor: Color,
    contentColor: Color,
    onDismissRequest: () -> Unit,
    onSelect: (item: Country) -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false,
    ),
    title: @Composable () -> Unit = {
        Text(
            modifier = Modifier
                .offset(y = (-2).dp)
                .qaAutomationTestTag("countryDialogTitle"),
            text = stringResource(Res.string.select_country),
            style = MaterialTheme.typography.titleMedium,
            color = contentColor,
        )
    },
    backIcon: @Composable () -> Unit = {
        Icon(
            painter = painterResource(Res.drawable.ic_arrow_back),
            contentDescription = "Back",
            tint = contentColor,
        )
    },
    searchIcon: @Composable () -> Unit = {
        Icon(
            painter = painterResource(Res.drawable.ic_search),
            contentDescription = "Search",
            tint = contentColor,
        )
    },
) {
    var searchValue by remember { mutableStateOf("") }
    var isSearch by remember { mutableStateOf(false) }
    var filteredItems by remember { mutableStateOf(countryList) }
    var focusedIndex by remember { mutableIntStateOf(-1) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val listFocusRequester = remember { FocusRequester() }

    val countriesData = if (searchValue.isEmpty()) countryList else filteredItems

    // Reset focused index when search results change
    LaunchedEffect(searchValue) {
        focusedIndex = -1
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        BoxWithConstraints {
            val isCompact = maxWidth < 600.dp
            val dialogModifier = if (isCompact) {
                modifier
                    .fillMaxSize()
                    .qaAutomationTestTag("countrySelectionDialog")
            } else {
                modifier
                    .widthIn(min = 280.dp, max = 560.dp)
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(0.75f)
                    .qaAutomationTestTag("countrySelectionDialog")
            }
            val dialogShape = if (isCompact) {
                RectangleShape
            } else {
                RoundedCornerShape(16.dp)
            }

            Surface(
                modifier = dialogModifier,
                shape = dialogShape,
                color = containerColor,
                tonalElevation = 6.dp,
            ) {
                Scaffold(
                    containerColor = containerColor,
                    topBar = {
                        val focusRequester = remember { FocusRequester() }
                        LaunchedEffect(isSearch) {
                            if (isSearch) {
                                focusRequester.requestFocus()
                            }
                        }
                        CenterAlignedTopAppBar(
                            title = {
                                if (isSearch) {
                                    TextField(
                                        modifier = Modifier
                                            .focusRequester(focusRequester)
                                            .qaAutomationTestTag("searchField"),
                                        value = searchValue,
                                        onValueChange = { searchStr ->
                                            searchValue = searchStr
                                            filteredItems =
                                                countryList.searchForAnItem(searchStr)
                                        },
                                        placeholder = {
                                            Text(
                                                text = stringResource(Res.string.search_country),
                                                color = contentColor.copy(alpha = 0.5f),
                                            )
                                        },
                                        colors = TextFieldDefaults.colors(
                                            disabledContainerColor = Color.Transparent,
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            unfocusedTextColor = contentColor,
                                            focusedTextColor = contentColor,
                                            focusedPlaceholderColor = contentColor,
                                            unfocusedPlaceholderColor = contentColor,
                                            cursorColor = contentColor,
                                        ),
                                        textStyle = MaterialTheme.typography.labelLarge.copy(
                                            color = contentColor,
                                        ),
                                    )
                                } else {
                                    title()
                                }
                            },
                            navigationIcon = {
                                IconButton(
                                    modifier = Modifier
                                        .qaAutomationTestTag("backIcon"),
                                    onClick = {
                                        onDismissRequest()
                                    },
                                ) {
                                    backIcon()
                                }
                            },
                            actions = {
                                IconButton(
                                    modifier = Modifier
                                        .qaAutomationTestTag("searchIcon"),
                                    onClick = {
                                        isSearch = !isSearch
                                    },
                                ) {
                                    searchIcon()
                                }
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = containerColor,
                                titleContentColor = contentColor,
                            ),
                        )
                    },
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .focusRequester(listFocusRequester)
                            .focusable()
                            .onPreviewKeyEvent { keyEvent ->
                                if (keyEvent.type == KeyEventType.KeyDown) {
                                    when (keyEvent.key) {
                                        Key.DirectionDown -> {
                                            if (focusedIndex < countriesData.lastIndex) {
                                                focusedIndex++
                                                coroutineScope.launch {
                                                    listState.animateScrollToItem(focusedIndex)
                                                }
                                            }
                                            true
                                        }

                                        Key.DirectionUp -> {
                                            if (focusedIndex > 0) {
                                                focusedIndex--
                                                coroutineScope.launch {
                                                    listState.animateScrollToItem(focusedIndex)
                                                }
                                            }
                                            true
                                        }

                                        Key.Enter -> {
                                            if (focusedIndex in countriesData.indices) {
                                                onSelect(countriesData[focusedIndex])
                                            }
                                            true
                                        }

                                        Key.Escape -> {
                                            onDismissRequest()
                                            true
                                        }

                                        else -> false
                                    }
                                } else {
                                    false
                                }
                            },
                    ) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .padding(paddingValues)
                                .qaAutomationTestTag("countryList")
                                .fillMaxSize(),
                        ) {
                            itemsIndexed(
                                items = countriesData,
                                key = { _, item -> item.code },
                            ) { index, countryItem ->
                                val isFocused = index == focusedIndex
                                val itemBackground = if (isFocused) {
                                    contentColor.copy(alpha = 0.12f)
                                } else {
                                    Color.Transparent
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(itemBackground)
                                        .qaAutomationTestTag("countryListItem")
                                        .clickable {
                                            onSelect(countryItem)
                                        }
                                        .padding(horizontal = 16.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .width(30.dp)
                                            .qaAutomationTestTag("countryFlag"),
                                        painter = painterResource(countryItem.flag),
                                        contentDescription = null,
                                    )
                                    Text(
                                        modifier = Modifier
                                            .weight(1f)
                                            .qaAutomationTestTag("countryName"),
                                        text = countryItem.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = contentColor,
                                    )
                                    Text(
                                        modifier = Modifier
                                            .qaAutomationTestTag("countryPhoneCode"),
                                        text = countryItem.phoneNoCode,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = contentColor,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
