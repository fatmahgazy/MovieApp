package org.codeforegypt.features.feature_movie_details.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import org.codeforegypt.features.feature_movie_details.data.remote.DetailsApiService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailsModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): DetailsApiService {
        return retrofit.create(DetailsApiService::class.java)
    }
}
