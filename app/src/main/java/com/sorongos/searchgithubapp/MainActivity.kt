package com.sorongos.searchgithubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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
    private lateinit var userAdapter: UserAdapter
    //retrofit 객체 생성
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



//        githubService.listRepos("sorongosdev").enqueue(object: Callback<List<Repo>>{
//            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
//                Log.e("MainActivity","search mine : ${response.body().toString()}")
//            }
//
//            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })

        userAdapter = UserAdapter()
        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,)
            adapter = userAdapter
        }

        binding.searchEditText.addTextChangedListener {
            searchUser(it.toString())
        }
    }

    private fun searchUser(query: String){
        val githubService = retrofit.create(GithubService::class.java) // 구현체
        //api call
        githubService.searchUsers(query).enqueue(object: Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                Log.e("MainActivity","search squar : ${response.body().toString()}")
                userAdapter.submitList(response.body()?.items) //userlist)
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()
                t.printStackTrace() //error 표시
            }

        })
    }
}