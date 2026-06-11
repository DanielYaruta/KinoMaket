package com.example.kinomaket

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    val isTwoPane: Boolean get() = findViewById<View>(R.id.detailContainer) != null

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isTwoPane && savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.listContainer, MovieListFragment())
                replace(R.id.detailContainer, MovieDetailFragment.newInstance(MovieRepository.movies[0].id))
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
