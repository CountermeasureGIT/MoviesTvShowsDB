package ru.countermeasure.moviestvshowsdb.ui.main.master

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movies_list.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

import ru.countermeasure.moviestvshowsdb.R
import ru.countermeasure.moviestvshowsdb.model.util.Result
import ru.countermeasure.moviestvshowsdb.ui.main.SharedViewModel
import ru.countermeasure.moviestvshowsdb.extension.provideViewModelWithActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MoviesListFragment : Fragment(), KodeinAware {
    private var param1: String? = null
    private var param2: String? = null

    override val kodein: Kodein by closestKodein()
    private val viewModel: SharedViewModel by provideViewModelWithActivity()

    private lateinit var viewAdapter: MoviesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
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
                    viewAdapter.submitList(it.data)
                    progressBar.visibility = View.VISIBLE
                    Log.d(this.javaClass.name, "RESULT LOADING ${it.data?.size}")
                }
                is Result.error -> {
                    progressBar.visibility = View.GONE
                    viewAdapter.submitList(it.cachedData)
                    Log.d(this.javaClass.name, "RESULT ERROR ${it.cachedData?.size}")
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }
                is Result.success -> {
                    progressBar.visibility = View.GONE
                    viewAdapter.submitList(it.data)
                    Log.d(this.javaClass.name, "RESULT SUCCESS ${it.data.size}")
                }
            }
        }
    }

    private fun initViews() {
        viewManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewAdapter = MoviesAdapter {
            Toast.makeText(
                this.context,
                "Item clicked ${it.title}",
                Toast.LENGTH_SHORT
            ).show()
        }
        rv_topRated.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        floatingActionButton.setOnClickListener {
            viewModel.onRefreshAction()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoviesListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
