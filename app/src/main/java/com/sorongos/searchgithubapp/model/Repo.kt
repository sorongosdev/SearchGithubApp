package com.sorongos.searchgithubapp.model

import com.google.gson.annotations.SerializedName
import org.intellij.lang.annotations.Language

data class Repo(
    //mapping as key 'id'
    //val id가 변수이름이 다른 것이어도, 'id'로 키가 설정
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("language")
    val language: String?,

    @SerializedName("stargazers_count")
    val starCount: Int,

    @SerializedName("forks_count")
    val forkCount: Int,

    @SerializedName("html_url")
    val htmlUrl: String,
)