package com.clearing.core_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clearing.core_ui.R
import com.clearing.core_ui.theme.ClearingTheme

@Composable
fun EmptyBox() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_not_found),
                contentDescription = null,
                tint = ClearingTheme.colors.iconTint
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.no_result_found),
                style = ClearingTheme.typography.default.copy(
                    color = ClearingTheme.colors.secondaryColor
                )
            )
        }
    }
}