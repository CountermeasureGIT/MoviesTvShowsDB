package ru.countermeasure.moviestvshowsdb.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.countermeasure.moviestvshowsdb.model.db.entity.Movie
import ru.countermeasure.moviestvshowsdb.model.db.entity.MovieCategoryType
import ru.countermeasure.moviestvshowsdb.model.db.entity.MovieToMovieCategory

@Dao
interface TopRatedMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMoviesCategory(movies: List<MovieToMovieCategory>)

    @Query("DELETE FROM movie_to_movie_category WHERE type = :type")
    suspend fun deleteMovieToMovieCategory(type: MovieCategoryType)

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun getTopRatedMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies as m JOIN movie_to_movie_category as mmc ON m.id = mmc.movie_id WHERE mmc.type = :type")
    fun getMoviesByCategory(type: MovieCategoryType): LiveData<List<Movie>>
}