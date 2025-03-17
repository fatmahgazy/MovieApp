package org.codeforegypt.movieapp.data.local.dao


import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.codeforegypt.movieapp.data.local.model.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("SELECT * FROM favorite_movies")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM favorite_movies WHERE movieId = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Int): MovieEntity?
}
