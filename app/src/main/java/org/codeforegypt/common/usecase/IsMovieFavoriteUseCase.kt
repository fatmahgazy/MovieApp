package org.codeforegypt.common.usecase

import org.codeforegypt.common.repository.MovieRepository
import javax.inject.Inject

class IsMovieFavoriteUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: Int): Boolean {
        return repository.isMovieFavorite(movieId)
    }
}
