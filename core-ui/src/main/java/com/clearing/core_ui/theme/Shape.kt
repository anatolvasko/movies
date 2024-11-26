package com.clearing.core_ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

val LocalClearingShapes = staticCompositionLocalOf {
    shapes
}

val shapes = ClearingShapes(
    roundedCornerShape6 = RoundedCornerShape(6.dp),
    roundedCornerShape8 = RoundedCornerShape(8.dp),
    roundedCornerShape12 = RoundedCornerShape(12.dp),
    roundedCornerShape16 = RoundedCornerShape(16.dp),
    topRoundedCornerShape12 = RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp)
)

data class ClearingShapes(
    val roundedCornerShape6: Shape = RectangleShape,
    val roundedCornerShape8: Shape = RectangleShape,
    val roundedCornerShape12: Shape = RectangleShape,
    val roundedCornerShape16: Shape = RectangleShape,
    val topRoundedCornerShape12: Shape = RectangleShape,
)