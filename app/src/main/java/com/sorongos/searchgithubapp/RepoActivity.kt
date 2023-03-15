package com.sorongos.searchgithubapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.sorongos.searchgithubapp.adapter.RepoAdapter
import com.sorongos.searchgithubapp.databinding.ActivityRepoBinding
import com.sorongos.searchgithubapp.model.Repo
import com.sorongos.searchgithubapp.network.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**조회한 user을 클릭하면 실행되는 액티비티*/
class RepoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRepoBinding
    private lateinit var repoAdapter: RepoAdapter
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //from MainActivity
        val username = intent.getStringExtra("username") ?: ""

        repoAdapter = RepoAdapter()

        binding.repoRecyclerView.apply{
            layoutManager = LinearLayoutManager(this@RepoActivity)
            adapter = repoAdapter
        }

        listRepo(username)
    }

    /**해당 유저 레포 리스트를 보여줌*/
    private fun listRepo(username: String){
        val githubService = retrofit.create(GithubService::class.java)
            githubService.listRepos(username).enqueue(object: Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                Log.e("RepoActivity","search mine : ${response.body().toString()}")

                repoAdapter.submitList(response.body())
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}