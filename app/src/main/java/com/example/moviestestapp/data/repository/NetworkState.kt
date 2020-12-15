package com.example.moviestestapp.data.repository

enum class Status{
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status, val msg: String) {

    companion object{

        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Success")
        val LOADING: NetworkState = NetworkState(Status.SUCCESS, "Success")
        val ERROR: NetworkState = NetworkState(Status.SUCCESS, "Success")
        val ENDOFLIST: NetworkState = NetworkState(Status.FAILED, "You have reached the end")
    }
}