package ru.countermeasure.moviestvshowsdb.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.countermeasure.moviestvshowsdb.R
import ru.countermeasure.moviestvshowsdb.extension.provideViewModel
import ru.countermeasure.moviestvshowsdb.ui.main.detail.MovieDetailFragment
import ru.countermeasure.moviestvshowsdb.ui.main.master.MoviesListFragment

class MainActivity : AppCompatActivity(), MoviesListFragment.OnItemSelectedListener, KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModel: SharedViewModel by provideViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews(savedInstanceState)
    }

    private fun initViews(savedInstanceState: Bundle?) {
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mi_popular -> {
                    viewModel.screenChanged(AppScreen.POPULAR)
                    true
                }
                R.id.mi_new -> {
                    viewModel.screenChanged(AppScreen.NEW)
                    true
                }
                R.id.mi_soon -> {
                    viewModel.screenChanged(AppScreen.SOON)
                    true
                }
                else -> false
            }
        }
        bottom_navigation.setOnNavigationItemReselectedListener {}

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fl_placeholder, MoviesListFragment(), MOVIES_LIST_FRAGMENT_TAG)
                .commit()
        }
    }

    override fun onMovieItemSelected(movieId: Int) {
        //bottom_navigation.visibility = View.GONE
        val movieDetailFragment = MovieDetailFragment.newInstance(movieId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_placeholder, movieDetailFragment)
            .addToBackStack("movie_item_selected")
            .commit()
    }

    companion object {
        const val MOVIES_LIST_FRAGMENT_TAG = "MOVIES_LIST_FRAGMENT_TAG"
    }
}