package com.clearing.movies_presentation.composable.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.clearing.core_ui.R
import com.clearing.core_ui.theme.ClearingTheme
import com.clearing.core_ui.theme.neutral4
import com.clearing.core_ui.theme.neutral6

@Composable
internal fun ImageErrorPlaceHolder(
    modifier: Modifier = Modifier
        .fillMaxHeight()
        .width(100.dp)
) {
    Box(
        modifier = modifier
            .clip(shape = ClearingTheme.shapes.roundedCornerShape12)
            .background(color = neutral4.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(28.dp),
            painter = painterResource(R.drawable.ic_image),
            contentDescription = null,
            tint = neutral6,
        )
    }
}