package com.joelkanyi.jcomposecountrycodepicker.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.joelkanyi.jcomposecountrycodepicker.R
import com.joelkanyi.jcomposecountrycodepicker.data.Country
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.getCountryName
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.getFlags
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.searchForAnItem

/**
 * [CustomCountrySelectionDialog] is a custom composable component based on "CountrySelectionDialog"
 * with more UI customization parameters that displays a dialog for selecting a country.
 *
 * @param modifier Modifier to be applied to the dialog layout.
 * @param countryList The list of countries to be displayed in the dialog.
 * @param dialogTitleTextStr The title text of the dialog (optional).
 * @param dialogTitleTextStyle The style of the dialog title text (optional).
 * @param navigationIconImageVector The image vector for the navigation icon (optional).
 * @param searchPlaceholderTextStr The placeholder text for the search field (optional).
 * @param searchPlaceholderTextColor The color of the search placeholder text (optional).
 * @param searchPlaceholderTextStyle The style of the search placeholder text (optional).
 * @param searchFieldBorderWidth The border width of the search field (optional).
 * @param searchFieldBorderColor The border color of the search field (optional).
 * @param searchFieldBorderColorShape The shape of the search field border (optional).
 * @param searchTextFieldColors The colors for the search text field (optional).
 * @param searchTextFieldStyle The style of the search text field (optional).
 * @param searchIconImageVector The image vector for the search icon (optional).
 * @param countryListItemModifier Modifier to be applied to each country list item (optional).
 * @param countryListItemTextStyle The style of the country list item text (optional).
 * @param countryListItemColors The colors for the country list item (optional).
 * @param flagIconWidth The width of the flag icon (optional)
 * @param containerColor The color of the dialog container.
 * @param contentColor The color of the dialog content.
 * @param onDismissRequest Called when the dialog is dismissed.
 * @param onSelect Called when a country is selected.
 * @param properties The properties of the dialog.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun CustomCountrySelectionDialog(modifier: Modifier = Modifier,
                                        countryList: List<Country>,
                                        dialogTitleTextStr: String? = null,
                                        dialogTitleTextStyle: TextStyle? = null,
                                        navigationIconImageVector: ImageVector? = null,
                                        searchPlaceholderTextStr: String? = null,
                                        searchPlaceholderTextColor: Color? = null,
                                        searchPlaceholderTextStyle: TextStyle = LocalTextStyle.current,
                                        searchFieldBorderWidth: Dp = 1.dp,
                                        searchFieldBorderColor: Color = Color.Transparent,
                                        searchFieldBorderColorShape: Shape = RoundedCornerShape(0.dp),
                                        searchTextFieldColors: TextFieldColors? = null,
                                        searchTextFieldStyle: TextStyle? = null,
                                        searchIconImageVector: ImageVector? = null,
                                        countryListItemModifier: Modifier = Modifier,
                                        countryListItemTextStyle: TextStyle? = null,
                                        countryListItemColors: ListItemColors? = null,
                                        flagIconWidth: Dp = 30.dp,
                                        containerColor: Color,
                                        contentColor: Color,
                                        onDismissRequest: () -> Unit,
                                        onSelect: (item: Country) -> Unit,
                                        properties: DialogProperties = DialogProperties().let {
                                            DialogProperties(
                                                dismissOnBackPress = it.dismissOnBackPress,
                                                dismissOnClickOutside = it.dismissOnClickOutside,
                                                securePolicy = it.securePolicy,
                                                usePlatformDefaultWidth = false)
                                        }) {
    var searchValue by remember { mutableStateOf("") }
    var isSearch by remember { mutableStateOf(false) }
    var filteredItems by remember { mutableStateOf(countryList) }

    AlertDialog(modifier = modifier
        .fillMaxSize()
        .qaAutomationTestTag("countrySelectionDialog"),
        containerColor = containerColor,
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(0),
        onDismissRequest = onDismissRequest,
        properties = properties,
        text = {
            Scaffold(modifier = Modifier,
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
                                TextField(modifier = Modifier
                                    .border(width = searchFieldBorderWidth,
                                        color = searchFieldBorderColor,
                                        shape = searchFieldBorderColorShape)
                                    .focusRequester(focusRequester)
                                    .qaAutomationTestTag("searchField"),
                                    value = searchValue,
                                    onValueChange = { searchStr ->
                                        searchValue = searchStr
                                        filteredItems =
                                            countryList.searchForAnItem(searchStr)
                                    },
                                    placeholder = {
                                        Text(modifier = Modifier,
                                            text = searchPlaceholderTextStr
                                                ?: stringResource(R.string.search_country),
                                            color = searchPlaceholderTextColor
                                                ?: contentColor.copy(
                                                    alpha = 0.5f),
                                            style = searchPlaceholderTextStyle)
                                    },
                                    colors = searchTextFieldColors
                                        ?: TextFieldDefaults.colors(
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
                                            cursorColor = contentColor),
                                    textStyle = searchTextFieldStyle
                                        ?: MaterialTheme.typography.labelLarge.copy(
                                            color = contentColor))
                            } else {
                                Text(modifier = Modifier
                                    .offset(y = (-2).dp)
                                    .qaAutomationTestTag("countryDialogTitle"),
                                    text = dialogTitleTextStr
                                        ?: stringResource(id = R.string.select_country),
                                    style = dialogTitleTextStyle
                                        ?: MaterialTheme.typography.titleMedium,
                                    color = contentColor)
                            }
                        },
                        navigationIcon = {
                            IconButton(modifier = Modifier
                                .qaAutomationTestTag("backIcon"),
                                onClick = {
                                    if (isSearch) {
                                        isSearch = !isSearch
                                        searchValue = ""
                                    } else {
                                        onDismissRequest()
                                    }
                                }) {
                                Icon(imageVector = navigationIconImageVector ?: Icons.Default.Close,
                                    contentDescription = "close icon",
                                    tint = contentColor)
                            }
                        },
                        actions = {
                            if (!isSearch) {
                                IconButton(modifier = Modifier
                                    .qaAutomationTestTag("searchIcon"),
                                    onClick = {
                                        isSearch = !isSearch
                                    }) {
                                    Icon(imageVector = searchIconImageVector
                                        ?: Icons.Default.Search,
                                        contentDescription = "search icon",
                                        tint = contentColor)
                                }
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = containerColor,
                            titleContentColor = contentColor))
                }) { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    LazyColumn(modifier = Modifier
                        .qaAutomationTestTag("countryList")
                        .fillMaxSize()) {
                        val countriesData =
                            if (searchValue.isEmpty()) {
                                countryList
                            } else {
                                filteredItems
                            }

                        items(countriesData) { countryItem ->
                            ListItem(modifier = countryListItemModifier
                                .fillMaxWidth()
                                .qaAutomationTestTag("countryListItem")
                                .clickable {
                                    onSelect(countryItem)
                                },
                                colors = countryListItemColors ?: ListItemDefaults.colors(
                                    containerColor = containerColor,
                                    trailingIconColor = contentColor,
                                    headlineColor = contentColor),
                                leadingContent = {
                                    Image(modifier = Modifier
                                        .width(flagIconWidth)
                                        .qaAutomationTestTag("countryFlag"),
                                        painter = painterResource(id = getFlags(countryItem.code)),
                                        contentDescription = "country flag")
                                },
                                trailingContent = {
                                    Text(modifier = Modifier
                                        .qaAutomationTestTag("countryPhoneCode"),
                                        text = countryItem.phoneNoCode,
                                        style = countryListItemTextStyle
                                            ?: MaterialTheme.typography.bodyMedium)
                                },
                                headlineContent = {
                                    Text(modifier = Modifier
                                        .qaAutomationTestTag("countryName"),
                                        text = stringResource(id = getCountryName(countryItem.code.lowercase())),
                                        style = countryListItemTextStyle
                                            ?: MaterialTheme.typography.bodyMedium)
                                })
                        }
                    }
                }
            }
        },
        confirmButton = {})
}
