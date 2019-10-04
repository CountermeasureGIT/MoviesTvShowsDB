package ru.countermeasure.moviestvshowsdb.di.module

import android.content.Context
import androidx.room.Room
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.countermeasure.moviestvshowsdb.model.db.AppDatabase
import ru.countermeasure.moviestvshowsdb.model.db.dao.TopRatedMoviesDao

val dbModule = Kodein.Module(name = "dbModule") {
    bind<AppDatabase>() with singleton { provideDatabase(instance()) }

    bind<TopRatedMoviesDao>() with singleton { instance<AppDatabase>().topRatedMoviesDao() }
}

private fun provideDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase")
        .fallbackToDestructiveMigration()
        .build()