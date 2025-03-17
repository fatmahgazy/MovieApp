package org.codeforegypt.movieapp.data.repository

import kotlinx.coroutines.flow.Flow
import org.codeforegypt.movieapp.data.local.model.MovieEntity
import org.codeforegypt.movieapp.data.model.Movie
import org.codeforegypt.movieapp.data.model.MovieResult
import retrofit2.Response


interface MovieRepository {
        suspend fun getMoviesFromApi(): List<MovieResult>
        suspend fun getDetailsById(movieId: Int): MovieResult?
        fun getFavoriteMovies(): Flow<List<MovieEntity>>
        suspend fun toggleFavorite(movie: MovieEntity)
        suspend fun isMovieFavorite(movieId: Int): Boolean
}
