package org.codeforegypt.features.feature_movie_details.domain.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.codeforegypt.core.network.Constants
import org.codeforegypt.data.repository.MovieRepository
import org.codeforegypt.data.repository.MovieRepositoryImpl
import org.codeforegypt.features.feature_home.data.local.MovieDao
import org.codeforegypt.features.feature_home.remote.HomeApiService
import org.codeforegypt.features.feature_movie_details.data.remote.DetailsApiService
import org.codeforegypt.features.feature_movie_details.domain.usecase.GetDetailsByIdUseCase
import org.codeforegypt.movieapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object DetailsModule {

    @Provides
    fun provideGetDetailsByIdUseCase(repository: MovieRepository) =
        GetDetailsByIdUseCase(repository)

    @Provides
    @Singleton
    fun provideMovieRepository(
        homeApi: HomeApiService,
        detailsApi: DetailsApiService,
        movieDao: MovieDao)
    : MovieRepository {
        return MovieRepositoryImpl(homeApi,detailsApi, movieDao)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor {
        val token = "Bearer ${BuildConfig.TMDB_TOKEN}"
        return Interceptor { chain ->
            val original = chain.request()
            Log.d("Interceptor",  token)
            val request = original.newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_TOKEN}")
                .build()
            chain.proceed(request)
        }
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): DetailsApiService {
        return retrofit.create(DetailsApiService::class.java)
    }
}
