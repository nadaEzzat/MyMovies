package com.project.mymovies.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.project.mymovies.R
import com.project.mymovies.databinding.FragmentDetailsBinding
import com.project.mymovies.model.MovieDetails
import com.project.mymovies.network.RetrofitManager
import com.project.mymovies.network.RetrofitManager.API_KEY

class DetailsFragment : Fragment() {

    lateinit var viewModel: DetailsViewModel
    private val retrofitService = RetrofitManager.getInstance()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var movieId: Long = 0
    private lateinit var circularProgressDrawable: CircularProgressDrawable
    private val args : DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        //  viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        viewModel = ViewModelProvider(this, ViewModelFactory(DetailsRepo(retrofitService))).get(
            DetailsViewModel::class.java
        )
        movieId = args.movieId
        setUpComponents();
        viewModel.getMovieDetails(API_KEY, movieId)
        Log.i("movieDetails", "API_KEY: ${API_KEY}")

        viewModel.getMovieGenre(API_KEY)
        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            // Log.i("movieDetails", "movieList: https://image.tmdb.org/t/p/w500${it.poster_path}")
            showData(it)

        })
        viewModel.movieGenre.observe(viewLifecycleOwner, Observer {
            Log.i("movieDetails", "movieGenre: ${it.genres.size}")

        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Log.i("movieDetails", "errorMessage: $it")
        })

        return binding.root
    }

    private fun setUpComponents() {

        circularProgressDrawable = CircularProgressDrawable(this.requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(this)
            .load(R.drawable.ic_baseline_access_time_24)
            .into(binding.movieTime.image)

    }

    private fun showData(movieDetails: MovieDetails) {
        binding.movieTime.text.text = convert(movieDetails.runtime)
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

    fun convert(mins: Long): kotlin.String {
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