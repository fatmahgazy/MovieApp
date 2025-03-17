package org.codeforegypt.movieapp.data.remote

import org.codeforegypt.movieapp.data.model.Movie
import org.codeforegypt.movieapp.data.model.MovieResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getMoviePopular(
        @Query("page") page: Int = 1
    ): Response<Movie>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailsById(@Path("movie_id") movieId: Int): Response<MovieResult>
}