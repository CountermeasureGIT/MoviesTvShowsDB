package ru.countermeasure.moviestvshowsdb.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.countermeasure.moviestvshowsdb.data.db.dao.MoviesDao
import ru.countermeasure.moviestvshowsdb.data.db.entity.Movie
import ru.countermeasure.moviestvshowsdb.data.db.entity.MovieDetail
import ru.countermeasure.moviestvshowsdb.data.db.entity.MovieToMovieCategory


@Database(
    entities = [Movie::class, MovieToMovieCategory::class, MovieDetail::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}