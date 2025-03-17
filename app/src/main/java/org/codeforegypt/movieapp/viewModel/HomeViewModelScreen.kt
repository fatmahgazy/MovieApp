package org.codeforegypt.movieapp.viewModel

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
import org.codeforegypt.movieapp.data.local.model.MovieEntity
import org.codeforegypt.movieapp.data.model.MovieResult
import org.codeforegypt.movieapp.data.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<MovieResult>>(emptyList())
    val movies: StateFlow<List<MovieResult>> = _movies.asStateFlow()

    val allFavoriteMovies: StateFlow<List<MovieEntity>> = repository.getFavoriteMovies()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            try {
                val moviesFromApi = repository.getMoviesFromApi()
                _movies.value = moviesFromApi
            } catch (e: Exception) {
                Log.e("ViewModel", "Failed to fetch movies: ${e.message}")
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
        }
    }
}