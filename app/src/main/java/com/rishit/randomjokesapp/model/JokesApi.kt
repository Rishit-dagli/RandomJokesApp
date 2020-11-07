package com.rishit.randomjokesapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface JokesApi {
    @Headers("Accept: application/json")
    @GET("/")
    fun getJokes(): Call<Jokes>
}