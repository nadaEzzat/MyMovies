package com.project.mymovies.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.project.mymovies.R
import com.project.mymovies.adapters.MoviesAdapter
import com.project.mymovies.databinding.FragmentHomeBinding
import com.project.mymovies.utilites.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), MoviesAdapter.MoviesItemListener {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: MoviesAdapter

    private lateinit var circularProgressDrawable: CircularProgressDrawable
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpComponents()
        binding.apply {
            moviesRv.apply {
                adapter = homeAdapter
                // layoutManager = LinearLayoutManager(this@HomeFragment.requireContext())
                layoutManager = GridLayoutManager(this@HomeFragment.requireContext(), 2)
            }
            homeViewModel.movies.observe(viewLifecycleOwner) { results ->
                homeAdapter.submitList(results.data)
                progressBar.isVisible =
                    results is Resource.Loading<*> && results.data.isNullOrEmpty()
                if (results.error?.localizedMessage?.isNotEmpty() == true) {
                    Toast.makeText(
                        this@HomeFragment.requireContext(),
                        results.error?.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
        return binding.root
    }

    private fun setUpComponents() {
        circularProgressDrawable = CircularProgressDrawable(this.requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        homeAdapter = MoviesAdapter(circularProgressDrawable, this)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickedMovie(movieId: Long) {
        findNavController().navigate(
            R.id.action_homeFragment_to_detailsFragment,
            bundleOf("movie_id" to movieId)
        )
    }

}