package ru.countermeasure.moviestvshowsdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.countermeasure.moviestvshowsdb.R
import ru.countermeasure.moviestvshowsdb.di.provideViewModel
import ru.countermeasure.moviestvshowsdb.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModel : MainViewModel by provideViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeViewModelData()
    }

    private fun observeViewModelData() {
        viewModel.getTopRatedMovies().observe(this) {
            textView.text = it[0].toString()
        }
    }
}