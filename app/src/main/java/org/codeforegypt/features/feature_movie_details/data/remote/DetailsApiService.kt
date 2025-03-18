package org.codeforegypt.features.feature_movie_details.data.remote

import org.codeforegypt.domain.model.MovieResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsApiService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetailsById(@Path("movie_id") movieId: Int): Response<MovieResult>

}