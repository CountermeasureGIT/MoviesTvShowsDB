package ru.countermeasure.moviestvshowsdb.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.countermeasure.moviestvshowsdb.data.db.entity.*
import ru.countermeasure.moviestvshowsdb.data.util.EnumMovieTypeDataConverter

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<Movie>)

    @Transaction
    suspend fun replaceMovieToCategoryList(
        movies: List<MovieToMovieCategory>,
        type: MovieCategoryType
    ) {
        deleteMovieToMovieCategory(type)
        saveMovieToMovieCategory(movies)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovieToMovieCategory(movies: List<MovieToMovieCategory>)

    @Query("DELETE FROM movie_to_movie_category WHERE type = :type")
    suspend fun deleteMovieToMovieCategory(@TypeConverters(EnumMovieTypeDataConverter::class) type: MovieCategoryType)

    @Query("SELECT * FROM movies ORDER BY vote_average DESC, vote_count DESC")
    fun getTopRatedMovies(): LiveData<List<Movie>>

    @Query("SELECT m.* FROM movies as m JOIN movie_to_movie_category as mmc ON m.id = mmc.movie_id WHERE mmc.type = :type ORDER BY m.release_date ASC")
    fun getMoviesByCategory(@TypeConverters(EnumMovieTypeDataConverter::class) type: MovieCategoryType): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieDetail(movieDetail: MovieDetail)

    @Query("SELECT * FROM movie_detail WHERE movie_id = :movieId")
    fun getMovieDetail(movieId: Int): LiveData<MovieDetail>

    @Query("SELECT * FROM `cast` WHERE movie_id = :movieId")
    fun getMovieCredits(movieId: Int): LiveData<List<Cast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovieCredits(cast: List<Cast>)
}