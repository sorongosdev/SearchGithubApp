package com.sorongos.searchgithubapp.network

import com.sorongos.searchgithubapp.model.Repo
import com.sorongos.searchgithubapp.model.UserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    /**헤더에 깃허브 토큰 추가*/
    @Headers("Authorization: Bearer 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")

    @GET("users/{username}/repos")
    fun listRepos(@Path("username") username: String): Call<List<Repo>>

    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<UserDto>
}