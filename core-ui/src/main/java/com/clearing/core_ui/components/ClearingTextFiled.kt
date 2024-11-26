package com.clearing.core_ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import com.clearing.core_ui.theme.ClearingTheme
import com.clearing.core_ui.theme.neutral3
import com.clearing.core_ui.theme.neutral4
import com.clearing.core_ui.theme.neutral5
import com.clearing.core_ui.theme.secondary5

@Composable
fun ClearingTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = ClearingTheme.typography.text14,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = ClearingTheme.shapes.roundedCornerShape8,
    colors: TextFieldColors = clearingTextFieldColors(),
    isError: Boolean = false,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        isError = isError,
    )
}

@Composable
private fun clearingTextFieldColors(
    textColor: Color = ClearingTheme.colors.textColor,
    placeholderColor: Color = neutral4,
    backgroundColor: Color = Color.Unspecified,
    unfocusedBorderColor: Color = neutral3,
    focusedBorderColor: Color = secondary5,
    cursorColor: Color = ClearingTheme.colors.primaryColor,
    errorBorderColor: Color = ClearingTheme.colors.errorColor,
    focusedTrailingIconColor: Color = neutral5,
    unfocusedTrailingIconColor: Color = neutral5,
) = OutlinedTextFieldDefaults.colors(
    focusedTextColor = textColor,
    unfocusedTextColor = textColor,
    unfocusedPlaceholderColor = placeholderColor,
    focusedPlaceholderColor = placeholderColor,
    focusedContainerColor = backgroundColor,
    unfocusedContainerColor = backgroundColor,
    unfocusedBorderColor = unfocusedBorderColor,
    focusedBorderColor = focusedBorderColor,
    cursorColor = cursorColor,
    errorBorderColor = errorBorderColor,
    focusedTrailingIconColor = focusedTrailingIconColor,
    unfocusedTrailingIconColor = unfocusedTrailingIconColor,
)