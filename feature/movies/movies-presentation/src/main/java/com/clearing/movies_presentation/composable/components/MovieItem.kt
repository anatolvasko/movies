package com.clearing.movies_presentation.composable.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.clearing.core_ui.Constants.BASE_URL_IMAGES
import com.clearing.core_ui.R
import com.clearing.core_ui.theme.ClearingTheme
import com.clearing.core_ui.theme.secondary5
import com.clearing.core_ui.util.noRippleClickable
import com.clearing.movies_domain.model.Movie

@Composable
fun MovieItem(
    movie: Movie,
    isFavorite: Boolean,
    height: Dp = 150.dp,
    onFavoritesClick: (Int, Boolean) -> Unit,
    onItemClick: (Movie) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(shape = ClearingTheme.shapes.roundedCornerShape12)
            .background(color = ClearingTheme.colors.cardBackgroundColor)
            .noRippleClickable {
                onItemClick(movie)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val painter = rememberAsyncImagePainter(model = "$BASE_URL_IMAGES${movie.posterPath}")

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(height * 2 / 3)
                .clip(shape = ClearingTheme.shapes.roundedCornerShape12),
        ) {
            if (painter.state is AsyncImagePainter.State.Loading) {
                ImageLoadingPlaceHolder()
            }

            if (painter.state is AsyncImagePainter.State.Error) {
                ImageErrorPlaceHolder()
            }

            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(height * 2 / 3),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                text = movie.originalTitle,
                style = ClearingTheme.typography.default,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Icon(
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.TopEnd)
                    .noRippleClickable {
                        onFavoritesClick(movie.id, !isFavorite)
                    },
                painter = painterResource(
                    id = if (isFavorite) R.drawable.ic_star_filled
                    else R.drawable.ic_star_outlined
                ),
                contentDescription = null,
                tint = secondary5,
            )
        }
    }
}