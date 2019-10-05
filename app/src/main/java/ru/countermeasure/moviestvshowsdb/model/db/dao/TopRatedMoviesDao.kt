package ru.countermeasure.moviestvshowsdb.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.countermeasure.moviestvshowsdb.model.db.entity.Movie

@Dao
interface TopRatedMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    fun getMovies() : LiveData<List<Movie>>
}