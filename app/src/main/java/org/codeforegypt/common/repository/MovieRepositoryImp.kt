package org.codeforegypt.common.repository

import org.codeforegypt.features.feature_home.data.local.MovieDao
import org.codeforegypt.features.feature_home.data.local.MovieEntity
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao
): MovieRepository {

    override suspend fun toggleFavorite(movie: MovieEntity) {
        val existingMovie = movieDao.getMovieById(movie.movieId)
        if (existingMovie != null) {
            movieDao.deleteMovie(existingMovie)
        } else {
            movieDao.insertMovie(movie.copy(isFavorite = true))
        }
    }

    override suspend fun isMovieFavorite(movieId: Int): Boolean {
        return movieDao.getMovieById(movieId) != null
    }
}
