package ru.countermeasure.moviestvshowsdb.di.module

import android.content.Context
import androidx.room.Room
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.countermeasure.moviestvshowsdb.data.db.AppDatabase
import ru.countermeasure.moviestvshowsdb.data.db.dao.MoviesDao

val dbModule = Kodein.Module(name = "dbModule") {
    bind<AppDatabase>() with singleton { provideDatabase(instance()) }

    bind<MoviesDao>() with singleton { instance<AppDatabase>().moviesDao() }
}

private fun provideDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase")
        .fallbackToDestructiveMigration()
        .build()