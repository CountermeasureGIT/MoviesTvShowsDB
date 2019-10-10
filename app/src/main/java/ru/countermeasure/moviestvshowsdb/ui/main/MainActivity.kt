package ru.countermeasure.moviestvshowsdb.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.countermeasure.moviestvshowsdb.R
import ru.countermeasure.moviestvshowsdb.extension.provideViewModel
import ru.countermeasure.moviestvshowsdb.ui.main.detail.MovieDetailFragment
import ru.countermeasure.moviestvshowsdb.ui.main.master.MoviesListFragment

class MainActivity : AppCompatActivity(), MoviesListFragment.OnItemSelectedListener,
    FragmentManager.OnBackStackChangedListener, KodeinAware {

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

        supportFragmentManager.addOnBackStackChangedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fl_placeholder, MoviesListFragment(), MOVIES_LIST_FRAGMENT_TAG)
                .addToBackStack(MOVIES_LIST_FRAGMENT_TAG)
                .commit()
        } else {
            savedInstanceState.getString(ACTIONBAR_TITLE)?.let { title ->
                setActionBarTitle(title)
            }
            isInRootFragment().apply {
                supportActionBar?.setDisplayHomeAsUpEnabled(!this)
                bottom_navigation.visibility = if (this) View.VISIBLE else View.GONE
            }
        }
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onBackStackChanged() {
        if (!isInRootFragment()) {
            bottom_navigation.visibility = View.GONE
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            bottom_navigation.visibility = View.VISIBLE
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            setActionBarTitle(resources.getString(R.string.movie_discover_title))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!isInRootFragment()) {
            supportFragmentManager.popBackStack()
        }
        return super.onSupportNavigateUp()
    }

    private fun isInRootFragment(): Boolean {
        return supportFragmentManager.backStackEntryCount == 1
    }

    override fun onMovieItemSelected(movieId: Int) {
        val movieDetailFragment = MovieDetailFragment.newInstance(movieId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_placeholder, movieDetailFragment)
            .addToBackStack("movie_item_selected")
            .commit()
        setActionBarTitle(resources.getString(R.string.movies_overview_title))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(BOTTOM_NAV_VISIBLE, bottom_navigation.visibility == View.VISIBLE)
        outState.putBoolean(HOME_AS_UP_ENABLED, !isInRootFragment())
        outState.putString(ACTIONBAR_TITLE, supportActionBar?.title.toString())
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val MOVIES_LIST_FRAGMENT_TAG = "MOVIES_LIST_FRAGMENT_TAG"

        const val BOTTOM_NAV_VISIBLE = "BOTTOM_NAV_VISIBLE"
        const val HOME_AS_UP_ENABLED = "HOME_AS_UP_ENABLED"
        const val ACTIONBAR_TITLE = "ACTIONBAR_TITLE"
    }
}