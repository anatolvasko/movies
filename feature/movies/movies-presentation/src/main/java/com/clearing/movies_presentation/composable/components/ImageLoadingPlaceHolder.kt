package com.clearing.movies_presentation.composable.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clearing.core_ui.components.LoadingBox
import com.clearing.core_ui.theme.neutral6

@Composable
internal fun ImageLoadingPlaceHolder(
    modifier: Modifier = Modifier
        .fillMaxHeight()
        .width(100.dp)
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LoadingBox(
            modifier = Modifier.size(28.dp),
            color = neutral6
        )
    }
}