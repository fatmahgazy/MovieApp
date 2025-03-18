package org.codeforegypt.features.feature_home.domain.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.codeforegypt.data.repository.MovieRepository
import org.codeforegypt.data.repository.MovieRepositoryImpl
import org.codeforegypt.features.feature_home.data.local.MovieDao
import org.codeforegypt.features.feature_home.domain.usecase.GetFavoriteMoviesUseCase
import org.codeforegypt.features.feature_home.domain.usecase.GetMoviesFromApiUseCase
import org.codeforegypt.features.feature_home.remote.HomeApiService
import org.codeforegypt.movieapp.BuildConfig
import org.codeforegypt.movieapp.data.local.roomdb.MovieDatabase
import org.codeforegypt.movieapp.data.remote.ApiService
import org.codeforegypt.core.network.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao
    }


    @Provides
    @Singleton
    fun provideMovieRepository(apiService: ApiService, movieDao: MovieDao): MovieRepository {
        return MovieRepositoryImpl(apiService, movieDao)
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
    fun provideApiService(retrofit: Retrofit): HomeApiService {
        return retrofit.create(HomeApiService::class.java)
    }

    @Provides
    fun provideGetMoviesFromApiUseCase(repository: MovieRepository) =
        GetMoviesFromApiUseCase(repository)

    @Provides
    fun provideGetFavoriteMoviesUseCase(repository: MovieRepository) =
        GetFavoriteMoviesUseCase(repository)


}
