package com.example.moviestestapp.ui.popular_movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviestestapp.data.api.TheMovieDBClient
import com.example.moviestestapp.data.repository.NetworkState
import com.example.moviestestapp.data.repository.PopularMoviesRepository
import com.example.moviestestapp.databinding.ActivityMainBinding
import com.example.moviestestapp.ui.single_movie_detail.SingleMovie

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainActivityViewModel

    lateinit var popularMoviesRepository: PopularMoviesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val apiService = TheMovieDBClient.getClient()

        popularMoviesRepository = PopularMoviesRepository(apiService)

        viewModel = getViewModel()

        val movieAdapter = PopularMoviesPageListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3
            }
        }

        with(binding.rvMovieList){
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = movieAdapter
        }

        with(viewModel){
            popularMoviesPagedList.observe(this@MainActivity, Observer {
                movieAdapter.submitList(it)
            })

            networkState.observe(this@MainActivity, Observer {
                binding.progressBarPopular.visibility = if (listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
                binding.txtErrorPopular.visibility = if (listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

                if (!listIsEmpty()){
                    movieAdapter.setNetworkState(it)
                }
            })
        }

    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainActivityViewModel(popularMoviesRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}