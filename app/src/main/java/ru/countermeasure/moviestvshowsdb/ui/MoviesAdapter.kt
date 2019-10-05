package ru.countermeasure.moviestvshowsdb.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_item.view.*
import ru.countermeasure.moviestvshowsdb.BuildConfig
import ru.countermeasure.moviestvshowsdb.R
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

    override fun onBindViewHolder(holder: MoviesAdapter.ViewHolder, position: Int) {
        val item = movies[position]

        holder.apply {
            tvTitle.text = item.title
            tvOverview.text = item.overview
            Glide.with(ivPoster.context)
                .load(BuildConfig.BASE_IMAGE_API_URL + "w185" + item.poster_path + "?api_key=1a680bee1c010ab832b442cf27840c79")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivPoster)

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.tv_title
        val ivPoster: ImageView = itemView.iv_poster
        val tvOverview: TextView = itemView.tv_overview
    }
}