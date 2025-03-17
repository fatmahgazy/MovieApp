package org.codeforegypt.movieapp.data.local.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import org.codeforegypt.movieapp.data.local.dao.MovieDao
import org.codeforegypt.movieapp.data.local.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 4,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase(){
    abstract val movieDao: MovieDao
}