package org.codeforegypt.features.feature_home.presentation.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StarRating(
    voteAverage: Double,
    maxRating: Int = 5
) {
    val rating = (voteAverage / 2)
    val fillStar = rating.toInt()
    val halfStar = rating - fillStar >= 0.5

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 2.dp)
    ) {
        for (i in 1..maxRating) {
            when {
                i <= fillStar -> FullStar()
                i == fillStar + 1 && halfStar -> HalfStar()
                else -> EmptyStar()
            }
        }
    }
}


@Composable
fun FullStar(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Filled.Star,
        contentDescription = " Star",
        tint = Color.Yellow,
        modifier = modifier
            .size(24.dp)
            .padding(bottom = 6.dp)
    )
}

@Composable
fun EmptyStar(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Rounded.StarBorder,
        contentDescription = "Empty Star",
        tint = Color.Yellow,
        modifier = modifier
            .size(24.dp)
            .padding(bottom = 6.dp)
    )
}

@Composable
fun HalfStar() {
    Icon(
        modifier = Modifier
            .padding(bottom = 6.dp)
            .size(20.dp),
        imageVector = Icons.Rounded.StarHalf,
        contentDescription = "Half Star",
        tint = Color.Yellow
    )

}



