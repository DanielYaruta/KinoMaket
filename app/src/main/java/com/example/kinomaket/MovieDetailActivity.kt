package com.example.kinomaket

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textview.MaterialTextView

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE_INDEX = "MOVIE_INDEX"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movieIndex = intent.getIntExtra(EXTRA_MOVIE_INDEX, 0)
        val movie = MovieRepository.movies.getOrNull(movieIndex) ?: return

        bindMovieData(movie)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }
    }

    private fun bindMovieData(movie: Movie) {
        findViewById<ImageView>(R.id.moviePoster).setImageResource(movie.posterRes)
        findViewById<TextView>(R.id.tvAgeBadge).text = movie.ageRating
        findViewById<TextView>(R.id.tvTitle).text = movie.title
        findViewById<RatingBar>(R.id.ratingBar).rating = movie.rating
        findViewById<TextView>(R.id.tvYearGenres).text =
            "${movie.year}  |  ${movie.genres.joinToString(", ")}"
        findViewById<TextView>(R.id.tvStoryline).text = movie.storyline

        addGenreChips(movie.genres)
        addCastMembers(movie.cast)
    }

    // Жанры добавляются программно с использованием Material Design Chip
    private fun addGenreChips(genres: List<String>) {
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupGenres)
        chipGroup.removeAllViews()
        genres.forEach { genre ->
            val chip = Chip(this).apply {
                text = genre
                isClickable = false
                chipBackgroundColor = ColorStateList.valueOf(
                    ContextCompat.getColor(this@MovieDetailActivity, R.color.colorSurface)
                )
                setTextColor(
                    ContextCompat.getColor(this@MovieDetailActivity, R.color.colorTextPrimary)
                )
                chipStrokeColor = ColorStateList.valueOf(
                    ContextCompat.getColor(this@MovieDetailActivity, R.color.colorPrimary)
                )
                chipStrokeWidth = 1.5f.dp
            }
            chipGroup.addView(chip)
        }
    }

    // Актёры добавляются программно целиком из кода
    private fun addCastMembers(cast: List<CastMember>) {
        val container = findViewById<LinearLayout>(R.id.castContainer)
        container.removeAllViews()
        cast.forEach { member ->
            container.addView(buildCastView(member))
        }
    }

    private fun buildCastView(member: CastMember): LinearLayout {
        val photoSize = 80.dp
        val containerWidth = 96.dp

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                containerWidth, LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { marginEnd = 12.dp }
        }

        val photo = ShapeableImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(photoSize, photoSize)
            shapeAppearanceModel = ShapeAppearanceModel.builder()
                .setAllCornerSizes(RelativeCornerSize(0.5f))
                .build()
            setBackgroundColor(member.photoColor)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        val nameView = MaterialTextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                containerWidth, LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = 6.dp }
            text = member.name
            setTextColor(ContextCompat.getColor(this@MovieDetailActivity, R.color.colorTextPrimary))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
            gravity = Gravity.CENTER
            maxLines = 2
        }

        container.addView(photo)
        container.addView(nameView)
        return container
    }

    private val Int.dp: Int
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics
        ).toInt()

    private val Float.dp: Float
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this, resources.displayMetrics
        )
}
