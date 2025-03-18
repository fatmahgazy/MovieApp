package org.codeforegypt.features.feature_home.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.codeforegypt.features.feature_home.data.local.MovieEntity
import org.codeforegypt.features.feature_home.data.repository.IHomeRepository
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(private val repository: IHomeRepository) {
    operator fun invoke(): Flow<List<MovieEntity>> {
        return repository.getFavoriteMovies()
    }
}