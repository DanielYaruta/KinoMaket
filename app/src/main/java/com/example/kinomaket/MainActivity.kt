package com.example.kinomaket

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity(), MovieNavigator {

    private val viewModel: MoviesViewModel by viewModels()

    private var _isTwoPane = false
    override val isTwoPane: Boolean get() = _isTwoPane

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _isTwoPane = findViewById<View>(R.id.detailContainer) != null

        if (isTwoPane) {
            if (savedInstanceState == null) {
                supportFragmentManager.commit {
                    replace(R.id.listContainer, MovieListFragment())
                }
            }
            viewModel.selectedMovieId.observe(this) { movieId ->
                supportFragmentManager.commit {
                    replace(R.id.detailContainer, MovieDetailFragment.newInstance(movieId))
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_container)
        return if (navHostFragment is NavHostFragment) {
            navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
        } else {
            super.onSupportNavigateUp()
        }
    }
}
