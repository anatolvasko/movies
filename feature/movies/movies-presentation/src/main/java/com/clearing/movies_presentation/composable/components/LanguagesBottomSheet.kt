package com.clearing.movies_presentation.composable.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clearing.core_ui.R
import com.clearing.core_ui.theme.ClearingTheme

@Composable
fun LanguagesBottomSheet(
    languages: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ClearingTheme.colors.backgroundColor)
    ) {
        Text(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.movie_details_languages),
            style = ClearingTheme.typography.text22,
            textAlign = TextAlign.Start
        )

        languages.forEach { language ->
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 12.dp)
                    .fillMaxWidth(),
                text = language,
                style = ClearingTheme.typography.default,
                textAlign = TextAlign.Start
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}