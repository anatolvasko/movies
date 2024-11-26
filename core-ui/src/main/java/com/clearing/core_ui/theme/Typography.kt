package com.clearing.core_ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

val LocalClearingTypography = staticCompositionLocalOf {
    ClearingTypography()
}

@Immutable
data class ClearingTypography(
    val default: TextStyle = DefaultStyle,
    val text12: TextStyle = Text12,
    val text14: TextStyle = Text14,
    val text16: TextStyle = Text16,
    val text18: TextStyle = Text18,
    val text20: TextStyle = Text20,
    val text22: TextStyle = Text22,
    val text24: TextStyle = Text24,
    val text30: TextStyle = Text30,
)

val fontFamily = FontFamily.Default

val DefaultStyle = TextStyle(
    fontFamily = fontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    color = neutral8,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    )
)

val Text10 = TextStyle(
    fontFamily = fontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 10.sp,
    lineHeight = 13.sp,
    color = neutral8,
    letterSpacing = (-0.1).sp
)

val Text12 = Text10.copy(
    fontFamily = fontFamily,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    color = neutral8,
    letterSpacing = (-0.12).sp
)

val Text14 = Text10.copy(
    fontFamily = fontFamily,
    fontSize = 14.sp,
    lineHeight = 18.sp,
    color = neutral8,
    letterSpacing = (-0.14).sp
)

val Text16 = Text10.copy(
    fontFamily = fontFamily,
    fontSize = 16.sp,
    lineHeight = 22.sp,
    color = neutral8,
    letterSpacing = (-0.16).sp
)

val Text18 = Text10.copy(
    fontFamily = fontFamily,
    fontSize = 18.sp,
    lineHeight = 24.sp,
    color = neutral8,
    letterSpacing = (-0.18).sp
)

val Text20 = Text10.copy(
    fontFamily = fontFamily,
    fontSize = 20.sp,
    lineHeight = 26.sp,
    color = neutral8,
    letterSpacing = (-0.2).sp
)

val Text22 = Text10.copy(
    fontFamily = fontFamily,
    fontSize = 22.sp,
    lineHeight = 28.sp,
    color = neutral8,
    letterSpacing = (-0.22).sp
)

val Text24 = Text10.copy(
    fontFamily = fontFamily,
    fontSize = 24.sp,
    lineHeight = 32.sp,
    color = neutral8,
    letterSpacing = (-0.24).sp
)

val Text30 = Text10.copy(
    fontFamily = fontFamily,
    fontSize = 30.sp,
    lineHeight = 40.sp,
    color = neutral8,
    letterSpacing = (-0.30).sp
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)