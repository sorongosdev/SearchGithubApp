package com.sorongos.searchgithubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.sorongos.searchgithubapp.adapter.UserAdapter
import com.sorongos.searchgithubapp.databinding.ActivityMainBinding
import com.sorongos.searchgithubapp.model.Repo
import com.sorongos.searchgithubapp.model.UserDto
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
                Log.e("MainActivity","search mine : ${response.body().toString()}")
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        val userAdapter = UserAdapter()
        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,)
            adapter = userAdapter
        }

        githubService.searchUsers("squar").enqueue(object: Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                Log.e("MainActivity","search squar : ${response.body().toString()}")
                userAdapter.submitList(response.body()?.items) //userlist)
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}