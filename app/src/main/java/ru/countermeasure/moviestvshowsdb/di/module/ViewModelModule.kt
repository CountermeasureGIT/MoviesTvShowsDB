package ru.countermeasure.moviestvshowsdb.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.generic.*
import ru.countermeasure.moviestvshowsdb.ui.viewmodels.MainViewModel
import ru.countermeasure.moviestvshowsdb.di.bindViewModel

val viewModelModule = Kodein.Module(name = "viewModelModule") {
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(dkodein) }

    bindViewModel<MainViewModel>() with provider {
        MainViewModel(instance())
    }
}

class ViewModelFactory(private val injector: DKodein) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        injector.instanceOrNull<ViewModel>(tag = modelClass.simpleName) as T?
            ?: modelClass.newInstance()
}