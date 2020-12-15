package com.example.moviestestapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviestestapp.data.api.TheMovieDBInterface
import com.example.moviestestapp.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class PopularMoviesDataSourceFactory(private val apiService: TheMovieDBInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>(){

    val moviesLiveDataSource = MutableLiveData<PopularMoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = PopularMoviesDataSource(apiService, compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}