package org.codeforegypt.features.feature_home.data.repository

import kotlinx.coroutines.flow.Flow
import org.codeforegypt.common.model.MovieResult
import org.codeforegypt.features.feature_home.data.local.MovieEntity

interface IHomeRepository {
    suspend fun getMoviesFromApi(): List<MovieResult>
    fun getFavoriteMovies(): Flow<List<MovieEntity>>
}