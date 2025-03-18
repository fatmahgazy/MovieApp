package org.codeforegypt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.codeforegypt.data.repository.MovieRepository
import org.codeforegypt.domain.usecase.IsMovieFavoriteUseCase
import org.codeforegypt.domain.usecase.ToggleFavoriteUseCase
@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    @Provides
    fun provideToggleFavoriteUseCase(repository: MovieRepository) = ToggleFavoriteUseCase(repository)

    @Provides
    fun provideIsMovieFavoriteUseCase(repository: MovieRepository) =
        IsMovieFavoriteUseCase(repository)
}