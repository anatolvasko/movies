package com.clearing.core_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clearing.core_ui.R
import com.clearing.core_ui.theme.ClearingTheme

@Composable
fun ClearingErrorBox(
    text: String = stringResource(R.string.general_error)
) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = ClearingTheme.colors.iconTint
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = text,
                style = ClearingTheme.typography.default.copy(
                    color = ClearingTheme.colors.secondaryColor
                )
            )
        }
    }
}