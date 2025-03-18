package org.codeforegypt.common.repository

import org.codeforegypt.features.feature_home.data.local.MovieEntity


interface MovieRepository {
        suspend fun toggleFavorite(movie: MovieEntity)
        suspend fun isMovieFavorite(movieId: Int): Boolean
}
