package org.codeforegypt.features.feature_home.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import org.codeforegypt.domain.model.MovieResult

@Serializable
@Entity(tableName = "favorite_movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieId: Int,
    val title: String,
    val releaseDate: String,
    val voteAverage: Double,
    val posterPath: String?,
    val isFavorite: Boolean = false
) {

    fun toMovieResult(): MovieResult {
        return MovieResult(
            adult = false,
            backdrop_path = "",
            genre_ids = emptyList(),
            id = movieId,
            original_language = "",
            original_title = title,
            overview = "",
            popularity = 0.0,
            poster_path = posterPath ?: "",
            release_date = releaseDate,
            title = title,
            video = false,
            vote_average = voteAverage,
            vote_count = 0,
            isFavorite = isFavorite
        )
    }

}
