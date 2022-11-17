package com.gvelesiani.socialx.domain.model.posts

data class PostModel(
    val key: String = "",
    val createdAt: String = "",
    val likes: List<String> = listOf(),
    val userKey: String = "",
    val comments: List<String> = listOf(),
    val description: String = "",
    val userName: String = "",
    val image: String = ""
)