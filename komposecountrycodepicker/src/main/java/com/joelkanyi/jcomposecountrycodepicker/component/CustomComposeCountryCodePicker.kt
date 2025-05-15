package com.joelkanyi.jcomposecountrycodepicker.component


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joelkanyi.jcomposecountrycodepicker.annotation.RestrictedApi
import com.joelkanyi.jcomposecountrycodepicker.data.Country
import com.joelkanyi.jcomposecountrycodepicker.data.FlagSize
import com.joelkanyi.jcomposecountrycodepicker.utils.PhoneNumberTransformation
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.extractCountryCodeAndPhoneNumber
import com.joelkanyi.jcomposecountrycodepicker.utils.PickerUtils.getCountry

/**
 * [CustomComposeCountryCodePicker] is based on "KomposeCountryCodePicker" with more UI customization
 * parameters. It is a composable that displays a text field with a country code picker dialog.
 *
 * @param modifier Modifier to be applied to the layout.
 * @param state The state of the country code picker.
 * @param phoneNumber The text to be displayed in the text field.
 * @param phoneNumberTextStyle The style to be used for displaying phone number text
 * @param onValueChange Called when the value is changed.
 * @param error If true, the text field will be displayed in the error state.
 * @param shape The shape of the text field's outline.
 * @param defaultPlaceholderTextColor The color to be used to display the phone number's default placeholder text
 * @param defaultPlaceholderTextStyle The style to be used to display the phone number's default placeholder text
 * @param placeholder The placeholder to be displayed in the text field.
 * @param downIcon The icon to be used to display the drop down.
 * @param colors The colors to be used to display the text field.
 * @param trailingIcon The trailing icon to be displayed in the text field.
 * @param countrySelectionDialogContainerColor The color to be used to display the country selection
 *                                             dialog container.
 * @param countrySelectionDialogContentColor The color to be used to display the country selection
 *                                            dialog content.
 * @param interactionSource The MutableInteractionSource representing the stream of Interactions for
 *                          this text field.
 * @param selectedCountryFlagSize The size of the selected country flag (width and height in `.dp`).
 * @param countryCodeTextStyle The style to be used for displaying country code text
 * @param enabled Controls the enabled state of the text field.
 * @param keyboardOptions The keyboard options to be used to display the keyboard.
 * @param keyboardActions The keyboard actions to be used to display the keyboard.
 * @param focusedBorderThickness The thickness of the border when the text field is focused.
 * @param unfocusedBorderThickness The thickness of the border when the text field is unfocused.
 * @param flagPaddingValues The padding to be applied to the selected country flag.
 * @param countryPaddingValues The padding to be applied to the country code.
 * @param countrySelectionDialogModifier The modifier to be applied to the country selection dialog.
 * @param countrySelectionDialogTitleTextStr The title text to be displayed in the country selection dialog.
 * @param countrySelectionDialogTitleTextStyle The style to be used to display the title text in the
 *                                             country selection dialog.
 * @param countrySelectionDialogNavigationIconImageVector Navigation icon for country selection dialog.
 * @param countrySelectionDialogSearchPlaceholderTextStr The search placeholder text in country selection dialog.
 * @param countrySelectionDialogSearchPlaceholderTextColor The search placeholder text color in country selection dialog.
 * @param countrySelectionDialogSearchPlaceholderTextStyle The search placeholder text style in country selection dialog.
 * @param countrySelectionDialogSearchTextFieldColors The colors to be used to display the search text in country selection dialog.
 * @param countrySelectionDialogSearchTextFieldStyle The style to be used to display the search text in country selection dialog.
 * @param countrySelectionDialogSearchFieldBorderWidth The border width of the search text field in country selection dialog.
 * @param countrySelectionDialogSearchFieldBorderColor The border color of the search text field in country selection dialog.
 * @param countrySelectionDialogSearchFieldBorderColorShape The border shape of the search text field in country selection dialog.
 * @param countrySelectionDialogSearchIconImageVector The search icon to be used to display the search text in country selection dialog.
 * @param countrySelectionDialogCountryListItemModifier The modifier to be applied to the country list item in country selection dialog.
 * @param countrySelectionDialogCountryListItemColors The colors to be used to display the country list item in country selection dialog.
 * @param countrySelectionDialogFlagIconWidth The width of the flag icon in country selection dialog.
 */
@OptIn(RestrictedApi::class, ExperimentalMaterial3Api::class)
@Composable
public fun CustomComposeCountryCodePicker(modifier: Modifier = Modifier,
                                          state: CountryCodePicker,
                                          phoneNumber: String,
                                          phoneNumberTextStyle: TextStyle = TextStyle.Default,
                                          onValueChange: (String) -> Unit = {},
                                          error: Boolean = false,
                                          shape: Shape = MaterialTheme.shapes.medium,
                                          defaultPlaceholderTextColor: Color? = null,
                                          defaultPlaceholderTextStyle: TextStyle? = null,
                                          placeholder: @Composable ((defaultLang: String) -> Unit) = { defaultLang ->
                                              CustomDefaultPlaceholder(defaultLang = defaultLang,
                                                  textColor = defaultPlaceholderTextColor,
                                                  textStyle = defaultPlaceholderTextStyle)
                                          },
                                          downIcon: ImageVector = Icons.Default.ArrowDropDown,
                                          colors: TextFieldColors = TextFieldDefaults.colors(),
                                          trailingIcon: @Composable (() -> Unit)? = null,
                                          countrySelectionDialogContainerColor: Color = MaterialTheme.colorScheme.background,
                                          countrySelectionDialogContentColor: Color = MaterialTheme.colorScheme.onBackground,
                                          interactionSource: MutableInteractionSource = MutableInteractionSource(),
                                          selectedCountryFlagSize: FlagSize = FlagSize(28.dp,
                                              18.dp),
                                          countryCodeTextStyle: TextStyle = LocalTextStyle.current,
                                          enabled: Boolean = true,
                                          keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
                                              keyboardType = KeyboardType.Phone,
                                              imeAction = ImeAction.Next),
                                          keyboardActions: KeyboardActions = KeyboardActions.Default,
                                          focusedBorderThickness: Dp = 2.dp,
                                          unfocusedBorderThickness: Dp = 1.dp,
                                          flagPaddingValues: PaddingValues = PaddingValues(0.dp),
                                          countryPaddingValues: PaddingValues = PaddingValues(4.dp),
                                          countrySelectionDialogModifier: Modifier = Modifier,
                                          countrySelectionDialogTitleTextStr: String? = null,
                                          countrySelectionDialogTitleTextStyle: TextStyle? = null,
                                          countrySelectionDialogNavigationIconImageVector: ImageVector? = null,
                                          countrySelectionDialogSearchPlaceholderTextStr: String? = null,
                                          countrySelectionDialogSearchPlaceholderTextColor: Color? = null,
                                          countrySelectionDialogSearchPlaceholderTextStyle: TextStyle = LocalTextStyle.current,
                                          countrySelectionDialogSearchTextFieldColors: TextFieldColors? = null,
                                          countrySelectionDialogSearchTextFieldStyle: TextStyle? = null,
                                          countrySelectionDialogSearchFieldBorderWidth: Dp = 1.dp,
                                          countrySelectionDialogSearchFieldBorderColor: Color = Color.Transparent,
                                          countrySelectionDialogSearchFieldBorderColorShape: Shape = RoundedCornerShape(
                                              0.dp),
                                          countrySelectionDialogSearchIconImageVector: ImageVector? = null,
                                          countrySelectionDialogCountryListItemModifier: Modifier = Modifier,
                                          countrySelectionDialogCountryListItemColors: ListItemColors? = null,
                                          countrySelectionDialogFlagIconWidth: Dp = 30.dp) {

    var openCountrySelectionDialog by rememberSaveable { mutableStateOf(false) }

    val phoneTextPair = extractCountryCodeAndPhoneNumber(phoneNumber)
    val countryCode = phoneTextPair.first
    val phoneNo = phoneTextPair.second

    LaunchedEffect(countryCode) {
        countryCode?.let { state.setCode(it) }
    }

    LaunchedEffect(phoneNo) {
        state.setPhoneNo(phoneNo)
    }

    if (openCountrySelectionDialog) {
        CustomCountrySelectionDialog(modifier = countrySelectionDialogModifier,
            dialogTitleTextStr = countrySelectionDialogTitleTextStr,
            dialogTitleTextStyle = countrySelectionDialogTitleTextStyle,
            navigationIconImageVector = countrySelectionDialogNavigationIconImageVector,
            searchPlaceholderTextStr = countrySelectionDialogSearchPlaceholderTextStr,
            searchPlaceholderTextColor = countrySelectionDialogSearchPlaceholderTextColor,
            searchPlaceholderTextStyle = countrySelectionDialogSearchPlaceholderTextStyle,
            searchTextFieldColors = countrySelectionDialogSearchTextFieldColors,
            searchTextFieldStyle = countrySelectionDialogSearchTextFieldStyle,
            searchFieldBorderWidth = countrySelectionDialogSearchFieldBorderWidth,
            searchFieldBorderColor = countrySelectionDialogSearchFieldBorderColor,
            searchFieldBorderColorShape = countrySelectionDialogSearchFieldBorderColorShape,
            searchIconImageVector = countrySelectionDialogSearchIconImageVector,
            countryListItemModifier = countrySelectionDialogCountryListItemModifier,
            countryListItemColors = countrySelectionDialogCountryListItemColors,
            flagIconWidth = countrySelectionDialogFlagIconWidth,
            countryList = state.countryList,
            onDismissRequest = {
                openCountrySelectionDialog = false
            },
            onSelect = { countryItem ->
                state.setCode(countryItem.code)
                openCountrySelectionDialog = false
            },
            containerColor = countrySelectionDialogContainerColor,
            contentColor = countrySelectionDialogContentColor)
    }

    CompositionLocalProvider(LocalTextSelectionColors provides colors.textSelectionColors) {
        BasicTextField(modifier = modifier.defaultMinSize(
            minWidth = OutlinedTextFieldDefaults.MinWidth,
            minHeight = OutlinedTextFieldDefaults.MinHeight),
            value = phoneNo,
            visualTransformation = PhoneNumberTransformation(state.countryCode.uppercase()),
            onValueChange = onValueChange,
            enabled = enabled,
            textStyle = phoneNumberTextStyle,
            cursorBrush = SolidColor(Color.Black),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = true,
            decorationBox = @Composable { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = phoneNo,
                    visualTransformation = VisualTransformation.None,
                    innerTextField = innerTextField,
                    placeholder = {
                        placeholder(state.countryCode)
                    },
                    leadingIcon = {
                        CustomSelectedCountryComponent(modifier = Modifier.padding(),
                            showFlag = state.showCountryFlag,
                            selectedCountry = state.countryCode.getCountry(),
                            flagSize = selectedCountryFlagSize,
                            textStyle = countryCodeTextStyle,
                            showCountryCode = state.showCountryCode,
                            flagPadding = flagPaddingValues,
                            countryCodePadding = countryPaddingValues,
                            downIcon = downIcon,
                            onClickSelectedCountry = {
                                if (enabled) {
                                    openCountrySelectionDialog = true
                                }
                            })
                    },
                    trailingIcon = trailingIcon,
                    singleLine = true,
                    enabled = enabled,
                    isError = error,
                    interactionSource = interactionSource,
                    colors = colors,
                    container = {
                        OutlinedTextFieldDefaults.Container(
                            enabled = enabled,
                            isError = error,
                            interactionSource = interactionSource,
                            colors = colors,
                            shape = shape,
                            focusedBorderThickness = focusedBorderThickness,
                            unfocusedBorderThickness = unfocusedBorderThickness)
                    }
                )
            }
        )
    }
}

/**
 * [CustomDefaultPlaceholder] is based off "DefaultPlaceholder" with more UI customization. It is a
 * composable that displays the default placeholder.
 *
 * @param modifier Modifier to be applied to the layout.
 * @param defaultLang The default language code.
 * @param textColor The color to be used to display the text(optional).
 * @param textStyle The style to be used to display the text(optional).
 */
@Composable
private fun CustomDefaultPlaceholder(modifier: Modifier = Modifier,
                                     defaultLang: String,
                                     textColor: Color? = null,
                                     textStyle: TextStyle? = null) {
    Text(modifier = modifier.qaAutomationTestTag("defaultPlaceholder"),
        text = stringResource(id = PickerUtils.getNumberHint(PickerUtils.allCountries.single { it.code == defaultLang }.code.lowercase())),
        style = textStyle
            ?: MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.ExtraLight),
        color = textColor ?: Color.Unspecified)
}

/**
 * [CustomSelectedCountryComponent] is based on "SelectedCountryComponent" with more UI customization
 *
 * @param modifier Modifier to be applied to the layout.
 * @param showFlag If true, the country flag will be shown.
 * @param selectedCountry The selected country.
 * @param flagSize The size of the selected country flag.
 * @param textStyle The style to be used to display the text.
 * @param flagPadding The padding to be applied to the selected country.
 * @param downIcon The icon to be used to display the drop down.
 * @param iconPaddingValues The padding to be applied to the icon.
 * @param showCountryCode If true, the country code will be shown.
 * @param countryCodePadding The padding to be applied to the country code.
 * @param onClickSelectedCountry Called when the selected country is clicked.
 */
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
private fun CustomSelectedCountryComponent(modifier: Modifier = Modifier,
                                           showFlag: Boolean = true,
                                           selectedCountry: Country,
                                           flagSize: FlagSize,
                                           textStyle: TextStyle,
                                           flagPadding: PaddingValues = PaddingValues(0.dp),
                                           downIcon: ImageVector,
                                           iconPaddingValues: PaddingValues = PaddingValues(2.dp),
                                           showCountryCode: Boolean = true,
                                           countryCodePadding: PaddingValues = PaddingValues(4.dp),
                                           onClickSelectedCountry: () -> Unit) {
    Row(modifier = modifier
        .qaAutomationTestTag("selectedCountryComponent")
        .clickable(
            interactionSource = MutableInteractionSource(),
            indication = null) {
            onClickSelectedCountry()
        },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        if (showFlag) {
            Image(modifier = Modifier
                .padding(flagPadding)
                .width(flagSize.width)
                .height(flagSize.height)
                .qaAutomationTestTag("countryFlag"),
                painter = painterResource(id = PickerUtils.getFlags(selectedCountry.code)),
                contentDescription = selectedCountry.name)
        }

        Icon(modifier = Modifier
            .qaAutomationTestTag("countryDropDown")
            .padding(iconPaddingValues),
            imageVector = downIcon,
            contentDescription = null)

        if (showCountryCode) {
            Text(modifier = Modifier
                .padding(countryCodePadding)
                .qaAutomationTestTag("countryCode"),
                text = selectedCountry.phoneNoCode,
                style = textStyle)
        }
    }
}
