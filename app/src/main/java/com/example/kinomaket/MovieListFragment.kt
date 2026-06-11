package com.example.kinomaket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kinomaket.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        (view.parent as? ViewGroup)?.doOnPreDraw { startPostponedEnterTransition() }

        val columns = resources.getInteger(R.integer.movie_grid_columns)
        val adapter = MovieAdapter { movie, posterView ->
            navigateToDetail(movie, posterView)
        }

        binding.moviesRecyclerView.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(requireContext(), columns)
        }
        adapter.submitList(MovieRepository.movies)
    }

    private fun navigateToDetail(movie: Movie, posterView: View) {
        val navigator = requireActivity() as? MovieNavigator
        if (navigator?.isTwoPane == true) {
            viewModel.selectMovie(movie.id)
        } else {
            val extras = FragmentNavigatorExtras(
                posterView to "movie_poster_${movie.id}"
            )
            val args = bundleOf(MovieDetailFragment.ARG_MOVIE_ID to movie.id)
            findNavController().navigate(R.id.action_list_to_detail, args, null, extras)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
