package com.example.moviestestapp.ui.single_movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviestestapp.data.repository.MovieDetailsRepository
import com.example.moviestestapp.data.repository.NetworkState
import com.example.moviestestapp.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

    class SingleMovieViewModel(private val movieDetailsRepository: MovieDetailsRepository, movieId: Int) : ViewModel(){

    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetails> by lazy {
        movieDetailsRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieDetailsRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}