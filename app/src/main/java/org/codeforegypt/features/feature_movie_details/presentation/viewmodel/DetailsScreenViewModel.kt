package org.codeforegypt.features.feature_movie_details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.codeforegypt.features.feature_movie_details.presentation.State.UiState
import org.codeforegypt.features.feature_home.data.local.MovieEntity
import org.codeforegypt.domain.model.MovieResult
import org.codeforegypt.domain.usecase.IsMovieFavoriteUseCase
import org.codeforegypt.domain.usecase.ToggleFavoriteUseCase
import org.codeforegypt.features.feature_movie_details.domain.usecase.GetDetailsByIdUseCase
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val getDetailsByIdUseCase: GetDetailsByIdUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val isMovieFavoriteUseCase: IsMovieFavoriteUseCase,

    ) : ViewModel() {

    private val _movieState = MutableStateFlow<UiState<MovieResult>>(UiState.Loading)
    val movieState: StateFlow<UiState<MovieResult>> = _movieState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            val movie = getDetailsByIdUseCase(movieId)
            if (movie != null) {
                _movieState.value = UiState.Success(movie)
                _isFavorite.value = isMovieFavoriteUseCase(movie.id)
            } else {
                _movieState.value = UiState.Error("Failed to load movie details")
            }
        }
    }

    fun toggleFavorite(movie: MovieResult) {
        viewModelScope.launch {
            val isCurrentlyFavorite = isMovieFavoriteUseCase(movie.id)
            val movieEntity = MovieEntity(
                movieId = movie.id,
                title = movie.title,
                releaseDate = movie.release_date,
                voteAverage = movie.vote_average,
                posterPath = movie.poster_path,
                isFavorite = !isCurrentlyFavorite
            )
            toggleFavoriteUseCase(movieEntity)
            _isFavorite.value = !isCurrentlyFavorite

        }
    }
}
