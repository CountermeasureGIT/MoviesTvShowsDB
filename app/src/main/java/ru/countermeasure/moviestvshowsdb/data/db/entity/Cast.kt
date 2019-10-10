package ru.countermeasure.moviestvshowsdb.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.countermeasure.moviestvshowsdb.BuildConfig

@Entity(
    tableName = "cast",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Movie::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("movie_id")
        )
    )
)
data class Cast(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val movie_id: Int,
    val order: Int,
    val character: String,
    val name: String,
    val profile_path: String?
) {
    fun getProfilePathUrl(): String? {
        var url: String? = null
        if (!profile_path.isNullOrBlank()) {
            url =
                "${BuildConfig.BASE_IMAGE_API_URL}/w185/$profile_path" +
                        "?${BuildConfig.API_KEY_QUERY_NAME}=${BuildConfig.API_KEY}"
        }
        return url
    }
}