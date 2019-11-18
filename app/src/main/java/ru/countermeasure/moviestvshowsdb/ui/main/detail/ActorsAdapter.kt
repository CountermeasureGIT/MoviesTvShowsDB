package ru.countermeasure.moviestvshowsdb.ui.main.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.actor_item.view.*
import ru.countermeasure.moviestvshowsdb.R
import ru.countermeasure.moviestvshowsdb.data.db.entity.Cast

class ActorsAdapter :
    ListAdapter<Cast, ActorsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.actor_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cast: Cast) {
            itemView.apply {
                tv_role.text = cast.character
                tv_actor.text = cast.name

                Glide.with(iv_photo.context)
                    .load(cast.getProfilePathUrl())
                    .thumbnail(Glide.with(iv_photo.context).load(R.drawable.placeholder_6x9))
                    .centerCrop()
                    .into(iv_photo)
            }
        }
    }
}


private class DiffCallback : DiffUtil.ItemCallback<Cast>() {

    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.order == newItem.order
    }

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem == newItem
    }
}