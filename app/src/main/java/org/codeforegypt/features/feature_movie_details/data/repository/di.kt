package org.codeforegypt.features.feature_movie_details.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class di {
    @Binds
    abstract fun provideRepositoryImp(detailsRepositoryImp: DetailsRepositoryImp): IDetailsRepository
}
