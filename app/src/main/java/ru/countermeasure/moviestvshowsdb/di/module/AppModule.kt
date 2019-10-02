package ru.countermeasure.moviestvshowsdb.di.module

import android.content.Context
import android.content.SharedPreferences
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val appModule = Kodein.Module(name = "appModule") {
    bind<SharedPreferences>() with singleton { instance<Context>().getSharedPreferences("MoviesTvShowsDB_SP", Context.MODE_PRIVATE) }
    //bind<Router>() with singleton { Router(instance()) }
    //bind<SessionManager>() with singleton { SessionManager(instance()) }
}