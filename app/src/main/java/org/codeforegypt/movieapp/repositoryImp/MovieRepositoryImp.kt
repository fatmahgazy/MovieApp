package org.codeforegypt.movieapp.repositoryImp

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import org.codeforegypt.movieapp.data.local.dao.MovieDao
import org.codeforegypt.movieapp.data.local.model.MovieEntity
import org.codeforegypt.movieapp.data.model.MovieResult
import org.codeforegypt.movieapp.data.remote.ApiService
import org.codeforegypt.movieapp.data.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao
): MovieRepository {

    override suspend fun getMoviesFromApi(): List<MovieResult> {
        return try {
            val response = apiService.getMoviePopular()
            if (response.isSuccessful) {
                response.body()?.results ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("Repository", "API Exception: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getDetailsById(movieId: Int): MovieResult? {
        return try {
            val response = apiService.getMovieDetailsById(movieId)
            if (response.isSuccessful)
                response.body()
            else
                null
        } catch (e: Exception) {
            Log.d("Repository", "API Exception: ${e.message}")
            null
        }
    }

    override fun getFavoriteMovies(): Flow<List<MovieEntity>> {
        return movieDao.getFavoriteMovies().onEach { movies ->
            Log.d("Repository", "Favorite Movies in Room: ${movies.map { it.title }}")
        }
    }

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
