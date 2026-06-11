package com.example.kinomaket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinomaket.databinding.ItemMovieBinding

class MovieAdapter(
    private val onClick: (movie: Movie, posterView: View) -> Unit
) : ListAdapter<Movie, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
        }
    }

    private var selectedId: Int = -1

    fun setSelectedId(newId: Int) {
        val oldPos = currentList.indexOfFirst { it.id == selectedId }
        val newPos = currentList.indexOfFirst { it.id == newId }
        selectedId = newId
        if (oldPos >= 0) notifyItemChanged(oldPos)
        if (newPos >= 0) notifyItemChanged(newPos)
    }

    class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        with(holder.binding) {
            moviePoster.transitionName = "movie_poster_${movie.id}"
            moviePoster.setImageResource(movie.posterRes)
            moviePoster.contentDescription = movie.title
            tvTitle.text = movie.title
            tvGenres.text = movie.genres.joinToString(", ")
            tvAgeBadge.text = movie.ageRating
            ratingBar.rating = movie.rating

            val isSelected = movie.id == selectedId
            root.strokeWidth = if (isSelected) 3.dp else 0
            root.strokeColor = ContextCompat.getColor(root.context, R.color.colorPrimary)

            root.setOnClickListener { onClick(movie, moviePoster) }
        }
    }
}
