package com.example.moviestestapp.data.api

import com.example.moviestestapp.data.vo.MovieDetails
import com.example.moviestestapp.data.vo.PopularMoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// GET INFO https://api.themoviedb.org/3/movie/590706?api_key=bf0e5839befef3f376381037280d3408&language=en-US
// GET POPULAR LIST https://api.themoviedb.org/3/movie/popular?api_key=bf0e5839befef3f376381037280d3408&language=en-US&page=1


interface TheMovieDBInterface {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<PopularMoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

}