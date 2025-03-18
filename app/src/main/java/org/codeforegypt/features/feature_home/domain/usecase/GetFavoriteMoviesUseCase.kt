package org.codeforegypt.features.feature_home.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.codeforegypt.data.repository.MovieRepository
import org.codeforegypt.features.feature_home.data.local.MovieEntity
import org.codeforegypt.features.feature_home.data.repository.IRepository
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(private val repository: IRepository) {
    operator fun invoke(): Flow<List<MovieEntity>> {
        return repository.getFavoriteMovies()
    }
}