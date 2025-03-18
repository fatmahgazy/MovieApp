package org.codeforegypt.features.feature_home.model

import kotlinx.serialization.Serializable
import org.codeforegypt.common.model.MovieResult

@Serializable
data class Movie(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)