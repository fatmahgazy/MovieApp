package org.codeforegypt.movieapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieResult(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val isFavorite: Boolean = false
) {
    val fullPosterPath: String
        get() = "https://image.tmdb.org/t/p/w500$poster_path"
}
