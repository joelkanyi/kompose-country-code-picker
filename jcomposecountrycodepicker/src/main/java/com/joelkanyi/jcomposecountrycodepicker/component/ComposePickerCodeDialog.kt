package com.joelkanyi.jcomposecountrycodepicker.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.joelkanyi.jcomposecountrycodepicker.R
import com.joelkanyi.jcomposecountrycodepicker.data.CountryData
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getCountryName
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getFlags
import com.joelkanyi.jcomposecountrycodepicker.data.utils.getLibCountries

@Composable
fun ComposePickerCodeDialog(
    modifier: Modifier = Modifier,
    padding: Dp = 15.dp,
    defaultSelectedCountry: CountryData = getLibCountries.first(),
    showCountryCode: Boolean = true,
    pickedCountry: (CountryData) -> Unit,
    showFlag: Boolean = true,
    showCountryName: Boolean = false,
) {
    val context = LocalContext.current

    val countryList: List<CountryData> = getLibCountries
    var isPickCountry by remember {
        mutableStateOf(defaultSelectedCountry)
    }
    var isOpenDialog by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .padding(padding)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                isOpenDialog = true
            },
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showFlag) {
                Image(
                    modifier = modifier.width(34.dp),
                    painter = painterResource(
                        id = getFlags(
                            isPickCountry.countryCode,
                        ),
                    ),
                    contentDescription = null,
                )
            }
            if (showCountryCode) {
                Text(
                    text = isPickCountry.countryPhoneCode,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
            if (showCountryName) {
                Text(
                    text = stringResource(id = getCountryName(isPickCountry.countryCode.lowercase())),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
        }

        if (isOpenDialog) {
            CountryDialog(
                countryList = countryList,
                onDismissRequest = { isOpenDialog = false },
                context = context,
                dialogStatus = isOpenDialog,
                onSelected = { countryItem ->
                    pickedCountry(countryItem)
                    isPickCountry = countryItem
                    isOpenDialog = false
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDialog(
    modifier: Modifier = Modifier,
    countryList: List<CountryData>,
    onDismissRequest: () -> Unit,
    onSelected: (item: CountryData) -> Unit,
    context: Context,
    dialogStatus: Boolean,
    properties: DialogProperties = DialogProperties(),
) {
    var searchValue by remember { mutableStateOf("") }
    if (!dialogStatus) searchValue = ""
    var isSearch by remember { mutableStateOf(false) }
    var filteredItems = mutableListOf<CountryData>()
    val fruits = listOf(
        CountryData(cCodes = "ad", cNames = "Andorra", countryPhoneCode = "+376"),
        CountryData(cCodes = "ae", cNames = "United Arab Emirates", countryPhoneCode = "+971"),
        CountryData(cCodes = "af", cNames = "Afghanistan", countryPhoneCode = "+93"),
    )

    AlertDialog(
        modifier = Modifier
            .fillMaxSize(),
        onDismissRequest = onDismissRequest,
        properties = properties.let {
            DialogProperties(
                dismissOnBackPress = it.dismissOnBackPress,
                dismissOnClickOutside = it.dismissOnClickOutside,
                securePolicy = it.securePolicy,
                usePlatformDefaultWidth = false,
            )
        },
        content = {
            Surface(
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                if (isSearch) {
                                    TextField(
                                        modifier = Modifier,
                                        value = searchValue,
                                        onValueChange = {
                                            searchValue = it
                                            filteredItems = countryList.filter { cData ->
                                                cData.cNames.lowercase().contains(
                                                    it,
                                                    ignoreCase = true,
                                                ) ||
                                                    cData.countryPhoneCode.contains(
                                                        it,
                                                        ignoreCase = true,
                                                    ) ||
                                                    cData.countryCode.lowercase().contains(
                                                        it,
                                                        ignoreCase = true,
                                                    )
                                            }.toMutableList()
                                            // CountryData(cCodes = "ad", countryPhoneCode = "+376", cNames = "Andorra"),
                                        },
                                        placeholder = {
                                            Text(
                                                text = "Search...",
                                                color = MaterialTheme.colorScheme.onSurface,
                                            )
                                        },
                                        colors = TextFieldDefaults.colors(
                                            disabledContainerColor = Color.Transparent,
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                        ),
                                        textStyle = MaterialTheme.typography.labelLarge,
                                    )
                                } else {
                                    Text(
                                        modifier = Modifier.offset(y = (-2).dp),
                                        text = "Select Country",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    onDismissRequest()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = null,
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = {
                                    isSearch = !isSearch
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null,
                                    )
                                }
                            },
                        )
                    },
                ) { scaffold ->
                    scaffold.calculateBottomPadding()
                    Column(modifier = Modifier.fillMaxSize()) {
                        val items = if (searchValue.isEmpty()) {
                            countryList
                        } else {
                            filteredItems
                        }
                        LazyColumn {
                            items(items) { countryItem ->
                                Row(
                                    Modifier
                                        .padding(18.dp)
                                        .fillMaxWidth()
                                        .clickable(onClick = { onSelected(countryItem) }),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Image(
                                            modifier = modifier.width(30.dp),
                                            painter = painterResource(
                                                id = getFlags(
                                                    countryItem.countryCode,
                                                ),
                                            ),
                                            contentDescription = null,
                                        )
                                        Text(
                                            stringResource(id = getCountryName(countryItem.countryCode.lowercase())),
                                            Modifier.padding(horizontal = 18.dp),
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily.Serif,
                                        )
                                    }

                                    Text(
                                        text = countryItem.countryPhoneCode,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
    )
}

@Composable
private fun SearchTextField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    value: String,
    textColor: Color = Color.Black,
    onValueChange: (String) -> Unit,
    hint: String = stringResource(id = R.string.search),
    fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
) {
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = LocalTextStyle.current.copy(
            color = textColor,
            fontSize = fontSize,
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            hint,
                            style = LocalTextStyle.current.copy(
                                color = textColor,
                                fontSize = fontSize,
                            ),
                        )
                    }
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        },
    )
}
