package com.example.moviestestapp.ui.single_movie_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.moviestestapp.data.api.POSTER_BASE_URL
import com.example.moviestestapp.data.api.TheMovieDBClient
import com.example.moviestestapp.data.api.TheMovieDBInterface
import com.example.moviestestapp.data.repository.MovieDetailsRepository
import com.example.moviestestapp.data.repository.NetworkState
import com.example.moviestestapp.data.vo.MovieDetails
import com.example.moviestestapp.databinding.ActivitySingleMovieBinding
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository
    private lateinit var binding: ActivitySingleMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val movieId: Int = intent.getIntExtra("id", 1)

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieDetailsRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUi(it)
        })

        viewModel.networkState.observe(this, Observer {
            binding.progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.txtError.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    private fun bindUi(it: MovieDetails?) {
        with(binding){
            movieTitle.text = it?.title
            movieTagline.text = it?.tagline
            movieReleaseDate.text = it?.releaseDate
            movieRating.text = it?.rating.toString()
            movieRuntime.text = it?.runtime.toString() + " minutes"
            movieOverview.text = it?.overview

            val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
            movieBudget.text = formatCurrency.format(it?.budget)
            movieRevenue.text = formatCurrency.format(it?.revenue)

            val moviePosterURL = POSTER_BASE_URL + it?.posterPath
            Glide.with(this@SingleMovie)
                    .load(moviePosterURL)
                    .into(ivMoviePoster)
        }
    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SingleMovieViewModel(movieDetailsRepository, movieId) as T
            }

        })[SingleMovieViewModel::class.java]
    }
}