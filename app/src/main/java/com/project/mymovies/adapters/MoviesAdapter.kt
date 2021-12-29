package com.project.mymovies.adapters;


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.project.mymovies.databinding.MovieCardBinding
import com.project.mymovies.model.MoviesResult

class MoviesAdapter(
    private val circularProgressDrawable: CircularProgressDrawable,
    private val listener: MoviesItemListener
) : ListAdapter<MoviesResult, MoviesAdapter.ViewHolder>(MoviesComparator()) {


    interface MoviesItemListener {
        fun onClickedMovie(movieId: Long)
    }

    class ViewHolder(
        private val binding: MovieCardBinding,
        private val listener: MoviesAdapter.MoviesItemListener,
        private val circularProgressDrawable: CircularProgressDrawable
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var movieItem: MoviesResult
        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(movie: MoviesResult) {
            this.movieItem = movie
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .placeholder(this.circularProgressDrawable)
                .into(binding.movieImage)

            binding.apply {

                movieName.text = movie.original_title
                movieRate.text = "${movie.vote_average}"


            }
        }

        override fun onClick(v: View?) {
            listener.onClickedMovie(movieItem.id)
        }
    }

    class MoviesComparator : DiffUtil.ItemCallback<MoviesResult>() {
        override fun areItemsTheSame(oldItem: MoviesResult, newItem: MoviesResult) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MoviesResult, newItem: MoviesResult) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener, circularProgressDrawable)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

}
