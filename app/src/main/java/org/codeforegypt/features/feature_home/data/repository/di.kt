package org.codeforegypt.features.feature_home.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun provideRepositoryImp(homeRepositoryImp: HomeRepositoryImp): IHomeRepository
}