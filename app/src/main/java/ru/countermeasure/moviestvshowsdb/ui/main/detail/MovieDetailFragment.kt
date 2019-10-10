package ru.countermeasure.moviestvshowsdb.ui.main.detail

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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

import ru.countermeasure.moviestvshowsdb.data.db.entity.MovieDetail
import ru.countermeasure.moviestvshowsdb.data.util.Result
import ru.countermeasure.moviestvshowsdb.extension.provideViewModel
import ru.countermeasure.moviestvshowsdb.R
import ru.countermeasure.moviestvshowsdb.data.db.entity.Cast


class MovieDetailFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    private val viewModel: MovieDetailViewModel by provideViewModel()
    private var movieId: Int = -1

    private lateinit var viewAdapter: ActorsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(ARG_MOVIE_ID)
        }
//        require(movieId >= 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeViewModelData()
    }

    private fun initViews() {
        viewModel.setMovieId(movieId)
        viewManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewAdapter = ActorsAdapter()

        rv_actors.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun observeViewModelData() {
        viewModel.getMovieDetail().observe(this) { movieDetail ->
            when (movieDetail) {
                is Result.loading -> {
                    showLoading(true)
                    showMovieDetailData(movieDetail.cachedData)
                }
                is Result.success -> {
                    showLoading(false)
                    showMovieDetailData(movieDetail.data)
                }
                is Result.error -> {
                    showLoading(false)
                    Toast.makeText(this.context, "Error ${movieDetail.message}", Toast.LENGTH_SHORT)
                        .show()
                    showMovieDetailData(movieDetail.cachedData)
                }
            }
        }

        viewModel.getMovieCredits().observe(this) { movieCredits ->
            when (movieCredits) {
                is Result.loading -> {
                    showLoading(true)
                    showMovieCredits(movieCredits.cachedData)
                }
                is Result.success -> {
                    showLoading(false)
                    showMovieCredits(movieCredits.data)
                }
                is Result.error -> {
                    showLoading(false)
                    Toast.makeText(
                        this.context,
                        "Error ${movieCredits.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    showMovieCredits(movieCredits.cachedData)
                }
            }
        }
    }

    private fun showMovieCredits(data: List<Cast>?) {
        viewAdapter.submitList(data)
    }

    private fun showLoading(loadingStatus: Boolean) {
        if (loadingStatus) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showMovieDetailData(data: MovieDetail?) {
        if (data == null) {
            group_content.visibility = View.GONE
            return
        }
        Glide.with(iv_backdrop.context)
            .load(data.getBackdropUrl())
            .into(iv_backdrop)
        tv_title.text = data.title
        tv_rating.text = data.vote_average.toString()
        tv_release_date.text = data.release_date
        tv_genre.text = data.genres
        tv_overview.text = data.overview

        group_content.visibility = View.VISIBLE
    }

    companion object {
        private const val ARG_MOVIE_ID = "MOVIE_ID_ARG"

        @JvmStatic
        fun newInstance(movieId: Int) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_MOVIE_ID, movieId)
                }
            }
    }
}
