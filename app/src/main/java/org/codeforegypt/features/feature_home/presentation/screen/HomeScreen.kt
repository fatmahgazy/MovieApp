package org.codeforegypt.features.feature_home.presentation.screen

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import org.codeforegypt.features.feature_home.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val movieResults by viewModel.movies.collectAsState()
    val favoriteMovies by viewModel.allFavoriteMovies.collectAsState()
    val isOnline = remember { mutableStateOf(true) }

    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    DisposableEffect(Unit) {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                isOnline.value = false
            }

            override fun onAvailable(network: Network) {
                isOnline.value = true
            }
        }

        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, callback)

        onDispose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            text = if (isOnline.value) "Popular Movies" else "Favorite Movies (Offline)",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 40.dp, start = 10.dp)
        )
        val moviesToShow = remember(isOnline.value, favoriteMovies, movieResults) {
            if (isOnline.value && movieResults.isNotEmpty()) {
                movieResults
            } else {
                favoriteMovies.map { it.toMovieResult() }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(moviesToShow.chunked(5)) { movieList ->
                LazyRow {
                    items(movieList, key = { it.id }) { movie ->
                        val isFavorite = favoriteMovies.any { it.movieId == movie.id }
                        MovieItem(
                            onClick = {
                                navController.navigate("details_screen/${movie.id}")
                            },
                            posterPath = movie.poster_path,
                            title = movie.title,
                            date = movie.release_date,
                            voteAverage = movie.vote_average,
                            isFavorite = isFavorite,
                            onFavoriteClick = {
                                viewModel.toggleFavorite(movie)
                            }
                        )
                    }
                }
            }
        }
    }
}


@SuppressLint("DefaultLocale")
@Composable
fun MovieItem(
    onClick: () -> Unit,
    posterPath: String?,
    title: String,
    date: String,
    voteAverage: Double,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    val fullPosterUrl by remember { derivedStateOf { "https://image.tmdb.org/t/p/w500${posterPath ?: ""}" } }

    Card(
        modifier = Modifier
            .width(220.dp)
            .wrapContentHeight()
            .padding(16.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.8f))
    ) {
        Column{
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = fullPosterUrl,
                    contentDescription = "Movie Poster",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 3f),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { onFavoriteClick() },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                        contentDescription = "Favourite Icon",
                        tint = if (isFavorite) Color.Red else Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = date,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StarRating(voteAverage)
                Text(
                    modifier = Modifier.padding(end = 4.dp, bottom = 6.dp),
                    text = String.format("%.1f", voteAverage),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
