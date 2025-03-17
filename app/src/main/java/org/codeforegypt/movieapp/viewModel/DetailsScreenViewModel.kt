package org.codeforegypt.movieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.codeforegypt.movieapp.UiState
import org.codeforegypt.movieapp.data.local.model.MovieEntity
import org.codeforegypt.movieapp.data.model.MovieResult
import org.codeforegypt.movieapp.data.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieState = MutableStateFlow<UiState<MovieResult>>(UiState.Loading)
    val movieState: StateFlow<UiState<MovieResult>> = _movieState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            val movie = repository.getDetailsById(movieId)
            if (movie != null) {
                _movieState.value = UiState.Success(movie)
                _isFavorite.value = repository.isMovieFavorite(movie.id)
            } else {
                _movieState.value = UiState.Error("Failed to load movie details")
            }
        }
    }

    fun toggleFavorite(movie: MovieResult) {
        viewModelScope.launch {
            val isCurrentlyFavorite = repository.isMovieFavorite(movie.id)
            val movieEntity = MovieEntity(
                movieId = movie.id,
                title = movie.title,
                releaseDate = movie.release_date,
                voteAverage = movie.vote_average,
                posterPath = movie.poster_path,
                isFavorite = !isCurrentlyFavorite
            )
            repository.toggleFavorite(movieEntity)
            _isFavorite.value = !isCurrentlyFavorite

        }
    }
}
