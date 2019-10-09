package ru.countermeasure.moviestvshowsdb.ui.main.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

import ru.countermeasure.moviestvshowsdb.R
import ru.countermeasure.moviestvshowsdb.extension.provideViewModel

class MovieDetailFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    private val viewModel: MovieDetailViewModel by provideViewModel()
    private var movieId: Int = -1

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
    }

    private fun initViews() {
        viewModel.setMovieId(movieId)
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
