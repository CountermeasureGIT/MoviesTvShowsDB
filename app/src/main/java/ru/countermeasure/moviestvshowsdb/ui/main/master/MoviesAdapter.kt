package ru.countermeasure.moviestvshowsdb.ui.main.master

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_item.view.*
import ru.countermeasure.moviestvshowsdb.R
import ru.countermeasure.moviestvshowsdb.extension.truncate
import ru.countermeasure.moviestvshowsdb.data.db.entity.Movie

class MoviesAdapter(private val clickListener: (Movie) -> Unit) :
    ListAdapter<Movie, MoviesAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie, clickListener: (Movie) -> Unit) {
            itemView.apply {
                tv_title.text = movie.title
                tv_overview.text = movie.overview.truncate()
                tv_rating.text = "${movie.vote_average} (${movie.vote_count})"
                tv_release_date.text = movie.release_date

                Glide.with(iv_poster.context)
                    .load(movie.getPosterPathUrl())
                    .thumbnail(Glide.with(iv_poster.context).load(R.drawable.poster_placeholder))
                    .centerCrop()
                    .into(iv_poster)
                setOnClickListener { clickListener(movie) }
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}