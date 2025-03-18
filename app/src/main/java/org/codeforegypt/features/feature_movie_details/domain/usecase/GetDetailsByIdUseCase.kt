package org.codeforegypt.features.feature_movie_details.domain.usecase

import org.codeforegypt.data.repository.MovieRepository
import org.codeforegypt.domain.model.MovieResult
import org.codeforegypt.features.feature_movie_details.data.repository.IRepository
import javax.inject.Inject

class GetDetailsByIdUseCase @Inject constructor(private val repository: IRepository) {
    suspend operator fun invoke(movieId: Int): MovieResult? {
        return repository.getDetailsById(movieId)
    }

}