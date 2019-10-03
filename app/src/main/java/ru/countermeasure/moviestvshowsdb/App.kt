package ru.countermeasure.moviestvshowsdb

import android.app.Application
import android.content.Context
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import ru.countermeasure.moviestvshowsdb.di.module.appModule
import ru.countermeasure.moviestvshowsdb.di.module.networkModule
import ru.countermeasure.moviestvshowsdb.di.module.repositoryModule
import ru.countermeasure.moviestvshowsdb.di.module.viewModelModule

class App : Application(), KodeinAware {
    override val kodein: Kodein by Kodein.lazy {
        bind<Context>() with instance(this@App)
        //import(androidXModule(this@App))
        import(appModule)
        //import(dbModule)
        import(networkModule)
        import(viewModelModule)
        import(repositoryModule)
    }
}