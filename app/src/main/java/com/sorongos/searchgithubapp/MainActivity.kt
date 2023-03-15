package com.sorongos.searchgithubapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.sorongos.searchgithubapp.adapter.UserAdapter
import com.sorongos.searchgithubapp.databinding.ActivityMainBinding
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

    private val handler = Handler(Looper.getMainLooper())
    private var searchFor: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter{
            val intent = Intent(this@MainActivity, RepoActivity::class.java)
            intent.putExtra("username", it.username)
            startActivity(intent)
        }
        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,)
            adapter = userAdapter
        }

        val runnable = Runnable{
            searchUser()
        }

        binding.searchEditText.addTextChangedListener {
            searchFor = it.toString()
            /**시간을 멈췄을 때, 무한 루퍼가 돌지 않게, 대기 작업을 지움*/
            handler.removeCallbacks(runnable)
            handler.postDelayed(
                runnable,
                1000,
            )
        }
    }

    private fun searchUser(){
        val githubService = retrofit.create(GithubService::class.java) // 구현체
        //api call
        githubService.searchUsers(searchFor).enqueue(object: Callback<UserDto> {
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