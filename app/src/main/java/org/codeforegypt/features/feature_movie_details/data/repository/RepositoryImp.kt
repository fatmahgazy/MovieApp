package org.codeforegypt.features.feature_movie_details.data.repository

import android.util.Log
import org.codeforegypt.domain.model.MovieResult
import org.codeforegypt.features.feature_movie_details.data.remote.DetailsApiService
import javax.inject.Inject

class RepositoryImp @Inject constructor( private val apiService: DetailsApiService): IRepository {
    override suspend fun getDetailsById(movieId: Int): MovieResult? {
        return try {
            val response = apiService.getMovieDetailsById(movieId)
            if (response.isSuccessful)
                response.body()
            else
                null
        } catch (e: Exception) {
            null
        }
    }
}