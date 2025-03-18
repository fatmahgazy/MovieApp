package org.codeforegypt.domain.usecase

import org.codeforegypt.data.repository.MovieRepository
import org.codeforegypt.features.feature_home.data.local.MovieEntity
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(movie: MovieEntity) {
        repository.toggleFavorite(movie)
    }
}