package com.sorongos.searchgithubapp.network

import com.sorongos.searchgithubapp.model.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("username") username: String): Call<List<Repo>>
}