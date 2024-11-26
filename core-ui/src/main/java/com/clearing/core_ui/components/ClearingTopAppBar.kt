package com.clearing.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.clearing.core_ui.theme.ClearingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClearingTopAppBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = ClearingTheme.colors.primaryColor,
        scrolledContainerColor = Color.Unspecified,
        navigationIconContentColor = Color.Unspecified,
        titleContentColor = Color.Unspecified,
        actionIconContentColor = Color.Unspecified,
    ),
    navigationIconVisible: Boolean = true,
    expandedHeight: Dp = TopAppBarDefaults.TopAppBarExpandedHeight,
    navigationIcon: @Composable () -> Unit = {},
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(expandedHeight)
            .background(color = backgroundColor),
        colors = colors,
        expandedHeight = expandedHeight,
        navigationIcon = {
            if (navigationIconVisible) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    navigationIcon()
                }
            }
        },
        title = {
            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                title()
            }
        },
        actions = {
            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                actions()
            }
        }
    )
}