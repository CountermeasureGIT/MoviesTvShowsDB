package ru.countermeasure.moviestvshowsdb.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.countermeasure.moviestvshowsdb.model.db.dao.TopRatedMoviesDao
import ru.countermeasure.moviestvshowsdb.model.db.entity.Movie


@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topRatedMoviesDao(): TopRatedMoviesDao
}