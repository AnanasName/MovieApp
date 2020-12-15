package com.example.moviestestapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviestestapp.data.api.POST_PER_PAGE
import com.example.moviestestapp.data.api.TheMovieDBInterface
import com.example.moviestestapp.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class PopularMoviesRepository(private val apiService: TheMovieDBInterface) {

    lateinit var moviesPagedList: LiveData<PagedList<Movie>>
    lateinit var popularMovieDataSourceFactory: PopularMoviesDataSourceFactory

    fun fetchLivePopularsMoviesPagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>>{
        popularMovieDataSourceFactory = PopularMoviesDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(POST_PER_PAGE)
                .build()

        moviesPagedList = LivePagedListBuilder(popularMovieDataSourceFactory, config).build()

        return moviesPagedList
    }

    fun getNetworkState(): LiveData<NetworkState>{
        return Transformations.switchMap<PopularMoviesDataSource, NetworkState>(
                popularMovieDataSourceFactory.moviesLiveDataSource, PopularMoviesDataSource::networkState
        )
    }
}