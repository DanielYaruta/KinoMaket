package com.example.kinomaket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kinomaket.databinding.ItemMovieBinding

class MovieAdapter(
    private val onClick: (movie: Movie, posterView: View) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private val movies = MovieRepository.movies

    class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        with(holder.binding) {
            moviePoster.transitionName = "movie_poster_${movie.id}"
            moviePoster.setImageResource(movie.posterRes)
            tvTitle.text = movie.title
            tvGenres.text = movie.genres.joinToString(", ")
            tvAgeBadge.text = movie.ageRating
            ratingBar.rating = movie.rating
            root.setOnClickListener { onClick(movie, moviePoster) }
        }
    }

    override fun getItemCount() = movies.size
}
