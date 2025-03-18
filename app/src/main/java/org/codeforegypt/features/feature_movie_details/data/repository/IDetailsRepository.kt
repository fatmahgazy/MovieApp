package org.codeforegypt.features.feature_movie_details.data.repository

import org.codeforegypt.common.model.MovieResult

interface IDetailsRepository {
    suspend fun getDetailsById(movieId: Int): MovieResult?
}