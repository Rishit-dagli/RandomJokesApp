package com.rishit.randomjokesapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.rishit.randomjokesapp.model.Jokes
import com.rishit.randomjokesapp.model.JokesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BaseUrl = "https://icanhazdadjoke.com/"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getJokeButton = findViewById<Button>(R.id.Generate)
        getJokeButton.setOnClickListener{ getData() }
    }

    private fun getData() {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        //    Retrofit Builder
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val jokesApi = retrofit.create(JokesApi::class.java)

        val call: Call<List<Jokes>> = jokesApi.getJokes()

        call.enqueue(object : Callback<List<Jokes>> {
            override fun onFailure(call: Call<List<Jokes>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<List<Jokes>>, response: Response<List<Jokes>>) {
                if (response.code() == 200){
                    val Jokes = response.body()!!
                    for(joke in Jokes){
                        println(joke.toString())
                    }
                }
            }
        })

        fun isNetworkConnected(): Boolean {
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            return networkCapabilities != null &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
    }
}