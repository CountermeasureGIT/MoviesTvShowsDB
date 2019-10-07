package ru.countermeasure.moviestvshowsdb.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.generic.*
import ru.countermeasure.moviestvshowsdb.extension.bindViewModel
import ru.countermeasure.moviestvshowsdb.ui.main.SharedViewModel

val viewModelModule = Kodein.Module(name = "viewModelModule") {
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(dkodein) }

    bindViewModel<SharedViewModel>() with provider {
        SharedViewModel(instance())
    }
}

class ViewModelFactory(private val injector: DKodein) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        injector.instanceOrNull<ViewModel>(tag = modelClass.simpleName) as T?
            ?: modelClass.newInstance()
}