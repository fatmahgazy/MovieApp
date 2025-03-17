package org.codeforegypt.movieapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)