package ru.countermeasure.moviestvshowsdb.model.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.countermeasure.moviestvshowsdb.model.util.EnumMovieTypeDataConverter

@Entity(
    tableName = "movie_to_movie_category",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Movie::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("movie_id")
        )
    )
)
@TypeConverters(EnumMovieTypeDataConverter::class)
data class MovieToMovieCategory(
    val movie_id: Int,
    val type: MovieCategoryType,
    @PrimaryKey
    val id: Int? = null
)

enum class MovieCategoryType(val type_id: Int) {
    NEW(1),
    SOON(2)
}