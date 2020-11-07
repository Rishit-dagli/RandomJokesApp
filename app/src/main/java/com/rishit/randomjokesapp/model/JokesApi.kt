package com.rishit.randomjokesapp.model

import retrofit2.Call
import retrofit2.http.GET

interface JokesApi {
    @GET("/")
    fun getJokes(): Call<List<Jokes>>
}