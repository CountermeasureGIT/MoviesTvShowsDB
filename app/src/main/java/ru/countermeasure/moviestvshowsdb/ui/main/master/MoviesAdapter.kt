package ru.countermeasure.moviestvshowsdb.ui.main.master

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_item.view.*
import ru.countermeasure.moviestvshowsdb.R
import ru.countermeasure.moviestvshowsdb.extension.truncate
import ru.countermeasure.moviestvshowsdb.model.db.entity.Movie

class MoviesAdapter(
    private var movies: List<Movie>
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    fun setMovies(newMovies: List<Movie>?) {
        movies = newMovies ?: emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = movies[position]

        holder.apply {
            tvTitle.text = item.title
            tvOverview.text = item.overview.truncate()
            Glide.with(ivPoster.context)
                .load(item.getPosterPathUrl())
                .thumbnail(Glide.with(ivPoster.context).load(R.drawable.poster_placeholder))
                .centerCrop()
                .into(ivPoster)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.tv_title
        val ivPoster: ImageView = itemView.iv_poster
        val tvOverview: TextView = itemView.tv_overview
    }
}