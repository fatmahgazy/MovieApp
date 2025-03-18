package org.codeforegypt.features.feature_movie_details.data.repository

import org.codeforegypt.domain.model.MovieResult

interface IRepository {
    suspend fun getDetailsById(movieId: Int): MovieResult?
}