package ru.countermeasure.moviestvshowsdb.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movie_item.view.*
import ru.countermeasure.moviestvshowsdb.R
import ru.countermeasure.moviestvshowsdb.model.network.response.movie_discover.MovieDiscoverResult

class MoviesAdapter(
    private var movies: List<MovieDiscoverResult>
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    fun setMovies(newMovies: List<MovieDiscoverResult>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MoviesAdapter.ViewHolder, position: Int) {
        val item = movies[position]

        holder.apply {
            tvId.text = item.id.toString()
            tvTitle.text = item.title
            tvOverview.text = item.overview
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvId: TextView = itemView.tv_id
        val tvTitle: TextView = itemView.tv_title
        val tvOverview: TextView = itemView.tv_overview
    }
}