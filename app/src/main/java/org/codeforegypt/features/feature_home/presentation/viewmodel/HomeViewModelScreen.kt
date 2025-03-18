package org.codeforegypt.features.feature_home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.codeforegypt.features.feature_home.domain.usecase.GetFavoriteMoviesUseCase
import org.codeforegypt.features.feature_home.domain.usecase.GetMoviesFromApiUseCase
import org.codeforegypt.domain.usecase.IsMovieFavoriteUseCase
import org.codeforegypt.domain.usecase.ToggleFavoriteUseCase
import org.codeforegypt.features.feature_home.data.local.MovieEntity
import org.codeforegypt.domain.model.MovieResult
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesFromApiUseCase: GetMoviesFromApiUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val isMovieFavoriteUseCase: IsMovieFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow<List<MovieResult>>(emptyList())
    val movies: StateFlow<List<MovieResult>> = _movies.asStateFlow()

    val allFavoriteMovies: StateFlow<List<MovieEntity>> = getFavoriteMoviesUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            try {
                val moviesFromApi =getMoviesFromApiUseCase()
                _movies.value = moviesFromApi
            } catch (e: Exception) {
                Log.e("ViewModel", "Failed to fetch movies: ${e.message}")
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
        }
    }
}