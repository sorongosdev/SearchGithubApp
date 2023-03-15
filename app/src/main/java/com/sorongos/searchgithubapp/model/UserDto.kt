package com.sorongos.searchgithubapp.model

import com.google.gson.annotations.SerializedName

/**data transfer obejct*/
data class UserDto (
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("items")
    val items: List<User>
)