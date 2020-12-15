package com.example.moviestestapp.ui.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.moviestestapp.data.repository.NetworkState
import com.example.moviestestapp.data.repository.PopularMoviesRepository
import com.example.moviestestapp.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val popularMoviesRepository: PopularMoviesRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val popularMoviesPagedList: LiveData<PagedList<Movie>> by lazy {
        popularMoviesRepository.fetchLivePopularsMoviesPagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        popularMoviesRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean{
        return popularMoviesPagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}