package com.gvelesiani.socialx.data.model.posts

import com.google.gson.annotations.SerializedName

data class PostResponseDto(
    @SerializedName("key")
    val key: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("likes")
    val likes: List<String> = listOf(),
    @SerializedName("user_key")
    val userKey: String = "",
    @SerializedName("comments")
    val comments: List<String> = listOf(),
    @SerializedName("description")
    val description: String = "",
    @SerializedName("user_name")
    val userName: String = "",
    @SerializedName("image")
    val image: String = ""
)