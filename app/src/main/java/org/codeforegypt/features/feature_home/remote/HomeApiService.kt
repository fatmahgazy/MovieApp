package org.codeforegypt.features.feature_home.remote


import org.codeforegypt.features.feature_home.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {
    @GET("movie/popular")
    suspend fun getMoviePopular(
        @Query("page") page: Int = 1
    ): Response<Movie>
}