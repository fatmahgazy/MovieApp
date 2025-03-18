package org.codeforegypt.features.feature_movie_details.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.codeforegypt.features.feature_home.presentation.screen.StarRating
import org.codeforegypt.features.feature_movie_details.presentation.State.UiState
import org.codeforegypt.features.feature_movie_details.presentation.viewmodel.DetailsScreenViewModel

@Composable
fun MovieDetailsScreen(
    movieId: Int
) {
    val viewModel: DetailsScreenViewModel = hiltViewModel()
    val movieState by viewModel.movieState.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    LaunchedEffect(movieId) {
        if (movieId > 0) {
            viewModel.fetchMovieDetails(movieId)
        }
    }
    when (movieState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
        is UiState.Success -> {
            val movie = (movieState as? UiState.Success)?.data
            if (movie != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        model = movie.fullPosterPath,
                        contentDescription = "Movie Poster",
                        modifier = Modifier
                            .padding( top = 18.dp)
                            .fillMaxWidth()
                            .height(400.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Release Date: ${movie.release_date}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                        IconButton(
                            onClick = { viewModel.toggleFavorite(movie) },
                            modifier = Modifier
                                .background(color = Color.DarkGray, shape = CircleShape)
                                .padding(4.dp)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                                contentDescription = "Favorite Icon",
                                tint = if (isFavorite) Color.Red else Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        StarRating(voteAverage = movie.vote_average)
                        Text(
                            text = String.format("%.1f", movie.vote_average),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Yellow
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Language: ${movie.original_language.uppercase()}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Overview",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 28.dp)
                    )
                }
            }
        }
        is UiState.Error -> {
            val errorMessage = (movieState as UiState.Error).message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: $errorMessage",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
