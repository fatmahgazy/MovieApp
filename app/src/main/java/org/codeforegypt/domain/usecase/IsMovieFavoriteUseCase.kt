package org.codeforegypt.domain.usecase

import org.codeforegypt.data.repository.MovieRepository
import javax.inject.Inject

class IsMovieFavoriteUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: Int): Boolean {
        return repository.isMovieFavorite(movieId)
    }
}
