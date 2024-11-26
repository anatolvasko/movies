package com.clearing.core_ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalClearingColorScheme = staticCompositionLocalOf {
    colorsExt
}

val colorsExt = ClearingColorScheme(
    backgroundColor = neutral0,
    textColor = neutral8,
    iconTint = secondary5,
    primaryColor = primary5,
    secondaryColor = secondary5,
    errorColor = error5,
    actionTextColor = secondary5,
    drawerBackgroundColor = neutral9,
    cardBackgroundColor = primary1
)

data class ClearingColorScheme(
    val backgroundColor: Color = Color.Unspecified,
    val textColor: Color = Color.Unspecified,
    val iconTint: Color = Color.Unspecified,
    val primaryColor: Color = Color.Unspecified,
    val secondaryColor: Color = Color.Unspecified,
    val errorColor: Color = Color.Unspecified,
    val actionTextColor: Color = Color.Unspecified,
    val drawerBackgroundColor: Color = Color.Unspecified,
    val cardBackgroundColor: Color = Color.Unspecified,
)