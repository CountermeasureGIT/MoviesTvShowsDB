package ru.countermeasure.moviestvshowsdb.di.module

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.countermeasure.moviestvshowsdb.repository.MovieDiscoverRepository

val repositoryModule = Kodein.Module(name = "repositoryModule") {
    bind<MovieDiscoverRepository>() with singleton { MovieDiscoverRepository(instance(), instance()) }
}