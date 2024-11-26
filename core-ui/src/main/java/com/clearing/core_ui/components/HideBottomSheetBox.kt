package com.clearing.core_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.clearing.core_ui.util.noRippleClickable

@Composable
fun HideBottomSheetBox(
    isVisible: Boolean,
    onClick: () -> Unit,
) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable { onClick() }
        )
    }
}