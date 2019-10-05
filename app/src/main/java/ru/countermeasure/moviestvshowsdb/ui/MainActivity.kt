package ru.countermeasure.moviestvshowsdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.countermeasure.moviestvshowsdb.R
import ru.countermeasure.moviestvshowsdb.di.provideViewModel
import ru.countermeasure.moviestvshowsdb.ui.viewmodels.MainViewModel
import ru.countermeasure.moviestvshowsdb.util.Result

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModel : MainViewModel by provideViewModel()

    private lateinit var viewAdapter: MoviesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        observeViewModelData()
    }

    private fun initViews() {
        viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewAdapter = MoviesAdapter(emptyList())
        rv_topRated.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        floatingActionButton.setOnClickListener {
            viewModel.onRefreshAction()
        }
    }

    private fun observeViewModelData() {
        viewModel.getTopRatedMovies().observe(this) {
            viewAdapter.setMovies(it.data)
            when(it.status) {
                Result.Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    Log.d("MY_LOG_TAG " + this.javaClass.simpleName, "Loading")
                    textView.text = textView.text.toString() + "\nLoading"
                }
                Result.Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    Log.d("MY_LOG_TAG " + this.javaClass.simpleName, "Error")
                    textView.text = textView.text.toString() + "\nError"
                }
                Result.Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    Log.d("MY_LOG_TAG " + this.javaClass.simpleName, "Success")
                    textView.text = textView.text.toString() + "\nSuccess"
                }
            }
        }
    }
}