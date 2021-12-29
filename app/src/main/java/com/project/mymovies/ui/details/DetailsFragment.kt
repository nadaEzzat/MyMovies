package com.project.mymovies.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.project.mymovies.R
import com.project.mymovies.adapters.GenreAdapter
import com.project.mymovies.databinding.FragmentDetailsBinding
import com.project.mymovies.model.MovieDetails
import com.project.mymovies.utilites.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var circularProgressDrawable: CircularProgressDrawable
    private lateinit var genreAdapter: GenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        setUpComponents();

        binding.apply {
            genreList.apply {
                adapter = genreAdapter
            }
            viewModel.movie.observe(viewLifecycleOwner) { results ->
                results.data?.let { showData(it) }
                progressBar.isVisible =
                    results is Resource.Loading<*> && results.data == null
                if (results.error?.localizedMessage?.isNotEmpty() == true) {
                    Toast.makeText(
                        this@DetailsFragment.requireContext(),
                        results.error?.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getLong("movie_id")?.let { viewModel.start(it) }

    }

    private fun setUpComponents() {

        binding.parentView.isInvisible = true
        circularProgressDrawable = CircularProgressDrawable(this.requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(this)
            .load(R.drawable.ic_baseline_access_time_24)
            .into(binding.movieTime.image)
        genreAdapter = GenreAdapter()

    }

    private fun showData(movieDetails: MovieDetails) {
        binding.parentView.isVisible = true
        genreAdapter.submitList(movieDetails.genres)
        binding.movieTime.text.text = convertLonToString(movieDetails.runtime)
        binding.movieInfo.movieName.text = movieDetails.original_title
        binding.movieInfo.date.text = movieDetails.release_date
        binding.movieInfo.status.text = movieDetails.status
        binding.voteAverage.text.text = "${movieDetails.vote_average}/10"
        binding.voteAverage.text2.text = "${movieDetails.vote_count}"
        binding.overview.text = "${movieDetails.overview}"



        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movieDetails.backdrop_path}")
            .placeholder(circularProgressDrawable)
            .into(binding.cover)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movieDetails.poster_path}")
            .placeholder(circularProgressDrawable)
            .into(binding.movieInfo.movieImage)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun convertLonToString(mins: Long): String {
        val h = mins / 60
        val m = mins % 60

        val hours = if (h < 10)
            "0${h}"
        else
            "${h}"

        val minutes = if (m < 10)
            "0${m}"
        else
            "${m}"

        return "${hours}:${minutes}:00"
    }
}