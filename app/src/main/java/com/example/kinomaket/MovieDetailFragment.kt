package com.example.kinomaket

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.example.kinomaket.databinding.FragmentMovieDetailBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textview.MaterialTextView

class MovieDetailFragment : Fragment() {

    companion object {
        const val ARG_MOVIE_ID = "movieId"

        fun newInstance(movieId: Int) = MovieDetailFragment().apply {
            arguments = Bundle().apply { putInt(ARG_MOVIE_ID, movieId) }
        }
    }

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val movieId: Int get() = requireArguments().getInt(ARG_MOVIE_ID, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isTwoPane = (activity as? MovieNavigator)?.isTwoPane ?: false
        if (!isTwoPane) {
            postponeEnterTransition()
            sharedElementEnterTransition = TransitionInflater.from(requireContext())
                .inflateTransition(android.R.transition.move)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = MovieRepository.movies.getOrNull(movieId) ?: run {
            startPostponedEnterTransition()
            return
        }

        val isTwoPane = (requireActivity() as? MovieNavigator)?.isTwoPane ?: false
        binding.moviePoster.transitionName = "movie_poster_$movieId"
        bindMovieData(movie)
        setupBackButton(isTwoPane)

        if (!isTwoPane) startPostponedEnterTransition()
    }

    private fun setupBackButton(isTwoPane: Boolean) {
        if (isTwoPane) {
            binding.btnBack.visibility = View.GONE
        } else {
            binding.btnBack.setOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun bindMovieData(movie: Movie) {
        binding.moviePoster.setImageResource(movie.posterRes)
        binding.moviePoster.contentDescription = movie.title
        binding.tvAgeBadge.text = movie.ageRating
        binding.tvTitle.text = movie.title
        binding.ratingBar.rating = movie.rating
        binding.tvYearGenres.text = "${movie.year}  |  ${movie.genres.joinToString(", ")}"
        binding.tvStoryline.text = movie.storyline
        addGenreChips(movie.genres, binding.chipGroupGenres)
        addCastMembers(movie.cast, binding.castContainer)
    }

    private fun addGenreChips(genres: List<String>, chipGroup: ChipGroup) {
        chipGroup.removeAllViews()
        genres.forEach { genre ->
            val chip = Chip(requireContext()).apply {
                text = genre
                isClickable = false
                chipBackgroundColor = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.colorSurface)
                )
                setTextColor(ContextCompat.getColor(requireContext(), R.color.colorTextPrimary))
                chipStrokeColor = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                )
                chipStrokeWidth = 1.5f.dp
            }
            chipGroup.addView(chip)
        }
    }

    private fun addCastMembers(cast: List<CastMember>, container: LinearLayout) {
        container.removeAllViews()
        cast.forEach { member -> container.addView(buildCastView(member)) }
    }

    private fun buildCastView(member: CastMember): LinearLayout {
        val photoSize = 80.dp
        val containerWidth = 96.dp

        val container = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                containerWidth, LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { marginEnd = 12.dp }
        }

        val photo = ShapeableImageView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(photoSize, photoSize)
            shapeAppearanceModel = ShapeAppearanceModel.builder()
                .setAllCornerSizes(RelativeCornerSize(0.5f))
                .build()
            setBackgroundColor(member.photoColor)
            scaleType = ImageView.ScaleType.CENTER_CROP
            contentDescription = member.name
        }

        val nameView = MaterialTextView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                containerWidth, LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = 6.dp }
            text = member.name
            setTextColor(ContextCompat.getColor(requireContext(), R.color.colorTextPrimary))
            textSize = 11f
            gravity = Gravity.CENTER
            maxLines = 2
        }

        container.addView(photo)
        container.addView(nameView)
        return container
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
