package ru.countermeasure.moviestvshowsdb.ui.main.master

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movies_list.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

import ru.countermeasure.moviestvshowsdb.R
import ru.countermeasure.moviestvshowsdb.data.util.Result
import ru.countermeasure.moviestvshowsdb.ui.main.SharedViewModel
import ru.countermeasure.moviestvshowsdb.extension.provideViewModelWithActivity

class MoviesListFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModel: SharedViewModel by provideViewModelWithActivity()

    private lateinit var viewAdapter: MoviesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var listener: OnItemSelectedListener

    interface OnItemSelectedListener {
        fun onMovieItemSelected(movieId: Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        require(context is OnItemSelectedListener)
        listener = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModelData()
    }

    private fun observeViewModelData() {
        viewModel.getTopRatedMovies().observe(this) {
            when (it) {
                is Result.loading -> {
                    viewAdapter.submitList(it.cachedData)
                    progressBar.visibility = View.VISIBLE
                }
                is Result.error -> {
                    progressBar.visibility = View.GONE
                    viewAdapter.submitList(it.cachedData)
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }
                is Result.success -> {
                    progressBar.visibility = View.GONE
                    viewAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun initViews() {
        viewManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            } else {
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }

        viewAdapter = MoviesAdapter { movieItem ->
            listener.onMovieItemSelected(movieItem.id)
        }

        rv_topRated.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        floatingActionButton.setOnClickListener {
            viewModel.onRefreshAction()
        }
    }
}
