package org.codeforegypt.features.feature_home.domain.usecase

import org.codeforegypt.common.model.MovieResult
import org.codeforegypt.features.feature_home.data.repository.IHomeRepository
import javax.inject.Inject

class GetMoviesFromApiUseCase @Inject constructor(private val repository: IHomeRepository) {
    suspend operator fun invoke(): List<MovieResult> {
        return repository.getMoviesFromApi()
    }
}