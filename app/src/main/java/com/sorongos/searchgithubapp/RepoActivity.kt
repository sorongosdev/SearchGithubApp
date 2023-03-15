package com.sorongos.searchgithubapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
    private var page = 0
    private var hasMore = true // page 더 넘겨야할까?
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

        binding.usernameTextView.text = username

        repoAdapter = RepoAdapter{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.htmlUrl))
            startActivity(intent)
        }
        val linearLayoutManager = LinearLayoutManager(this@RepoActivity)

        binding.repoRecyclerView.apply{
            layoutManager = linearLayoutManager
            adapter = repoAdapter
        }

        binding.repoRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //마지막 아이템 판별 : 두 개를 비교해서 같거나 lastVisible이 크면 마지막 아이템
                val totalCount = linearLayoutManager.itemCount
                val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()

                //next page
                if(lastVisiblePosition >= (totalCount - 1) && hasMore){
                    listRepo(username, ++page)
                }
            }
        })

        listRepo(username, 0)
    }

    /**해당 유저 레포 리스트를 보여줌*/
    private fun listRepo(username: String, page: Int){
        val githubService = retrofit.create(GithubService::class.java)
            githubService.listRepos(username, page).enqueue(object: Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                Log.e("RepoActivity","search mine : ${response.body().toString()}")
                // 한번에 30개의 아이템만 보여줄 수 있음
                hasMore = response.body()?.count() == 30
                // 원래 것을 지우고 뷰를 아예 다시 그리는 문제 해결을 위해 받아온 것을 추가함
                repoAdapter.submitList(repoAdapter.currentList + response.body().orEmpty())
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}