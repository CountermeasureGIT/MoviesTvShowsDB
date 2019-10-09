package ru.countermeasure.moviestvshowsdb.di.module

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.countermeasure.moviestvshowsdb.data.network.MoviesRemoteDataSource

val dataSourceModule = Kodein.Module(name = "dataSourceModule") {
    bind<MoviesRemoteDataSource>() with singleton {
        MoviesRemoteDataSource(
            instance()
        )
    }
}