package com.example.kinomaket

import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.LayerDrawable
import android.util.TypedValue
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayout = findViewById<GridLayout>(R.id.moviesGrid)
        val columns = resources.getInteger(R.integer.movie_grid_columns)
        gridLayout.columnCount = columns

        MovieRepository.movies.forEachIndexed { index, movie ->
            val card = buildMovieCard(movie, index)
            val col = index % columns
            val row = index / columns
            val params = GridLayout.LayoutParams(
                GridLayout.spec(row),
                GridLayout.spec(col, 1f)
            ).apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                val m = 6.dp
                setMargins(m, m, m, m)
            }
            card.layoutParams = params
            gridLayout.addView(card)
        }
    }

    private fun buildMovieCard(movie: Movie, index: Int): MaterialCardView {
        val card = MaterialCardView(this).apply {
            radius = 16f.dp
            cardElevation = 0f
            setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorSurface))
            isClickable = true
            isFocusable = true
        }

        val frame = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val poster = ImageView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                240.dp
            )
            setImageResource(movie.posterRes)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        val gradientOverlay = android.view.View(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            background = ContextCompat.getDrawable(context, R.drawable.bg_bottom_gradient)
        }

        val bottomContent = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            val lp = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            lp.gravity = Gravity.BOTTOM
            layoutParams = lp
            setPadding(12.dp, 8.dp, 12.dp, 12.dp)
        }

        val ratingBar = RatingBar(this, null, android.R.attr.ratingBarStyleSmall).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            numStars = 5
            stepSize = 0.5f
            rating = movie.rating
            setIsIndicator(true)
        }
        (ratingBar.progressDrawable as? LayerDrawable)
            ?.getDrawable(2)
            ?.setTint(ContextCompat.getColor(this, R.color.colorPrimary))

        val genresText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = 2.dp }
            text = movie.genres.joinToString(", ")
            setTextColor(ContextCompat.getColor(context, R.color.colorTextSecondary))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
        }

        val titleText = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = 4.dp }
            text = movie.title
            setTextColor(ContextCompat.getColor(context, R.color.colorTextPrimary))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            setTypeface(null, Typeface.BOLD)
        }

        bottomContent.addView(ratingBar)
        bottomContent.addView(genresText)
        bottomContent.addView(titleText)

        val ageBadge = TextView(this).apply {
            val lp = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            lp.gravity = Gravity.TOP or Gravity.START
            lp.setMargins(8.dp, 8.dp, 0, 0)
            layoutParams = lp
            text = movie.ageRating
            setTextColor(ContextCompat.getColor(context, R.color.colorTextPrimary))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
            setTypeface(null, Typeface.BOLD)
            background = ContextCompat.getDrawable(context, R.drawable.bg_age_badge)
            setPadding(6.dp, 3.dp, 6.dp, 3.dp)
        }

        frame.addView(poster)
        frame.addView(gradientOverlay)
        frame.addView(bottomContent)
        frame.addView(ageBadge)
        card.addView(frame)

        card.setOnClickListener {
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_INDEX, index)
            startActivity(intent)
        }

        return card
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
