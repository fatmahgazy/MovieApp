package org.codeforegypt.core.offlineDb

import androidx.room.Database
import androidx.room.RoomDatabase
import org.codeforegypt.features.feature_home.data.local.MovieDao
import org.codeforegypt.features.feature_home.data.local.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 4,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase(){
    abstract val movieDao: MovieDao
}