package org.codeforegypt.features.feature_home.domain.usecase

import org.codeforegypt.data.repository.MovieRepository
import org.codeforegypt.domain.model.MovieResult
import org.codeforegypt.features.feature_home.data.repository.IRepository
import javax.inject.Inject

class GetMoviesFromApiUseCase @Inject constructor(private val repository: IRepository) {
    suspend operator fun invoke(): List<MovieResult> {
        return repository.getMoviesFromApi()
    }
}