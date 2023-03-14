package com.sorongos.searchgithubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sorongos.searchgithubapp.databinding.ActivityMainBinding
import com.sorongos.searchgithubapp.model.Repo
import com.sorongos.searchgithubapp.network.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrofit 객체 생성
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val githubService = retrofit.create(GithubService::class.java) // 구현체
        githubService.listRepos("sorongosdev").enqueue(object: Callback<List<Repo>>{
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                Log.e("MainActivity",response.body().toString())
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}