package org.codeforegypt.features.feature_movie_details.domain.usecase

import org.codeforegypt.common.model.MovieResult
import org.codeforegypt.features.feature_movie_details.data.repository.IDetailsRepository
import javax.inject.Inject

class GetDetailsByIdUseCase @Inject constructor(private val repository: IDetailsRepository) {
    suspend operator fun invoke(movieId: Int): MovieResult? {
        return repository.getDetailsById(movieId)
    }

}