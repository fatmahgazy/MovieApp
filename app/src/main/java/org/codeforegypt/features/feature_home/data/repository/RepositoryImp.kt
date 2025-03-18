package org.codeforegypt.features.feature_home.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import org.codeforegypt.domain.model.MovieResult
import org.codeforegypt.features.feature_home.data.local.MovieDao
import org.codeforegypt.features.feature_home.data.local.MovieEntity
import org.codeforegypt.features.feature_home.remote.HomeApiService
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val apiService: HomeApiService,
    private val movieDao: MovieDao
): IRepository {
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

    override fun getFavoriteMovies(): Flow<List<MovieEntity>> {
        return movieDao.getFavoriteMovies().onEach { movies ->
            Log.d("Repository", "Favorite Movies in Room: ${movies.map { it.title }}")
        }
    }

}