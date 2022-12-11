package com.gvelesiani.socialx.domain.model.comments

data class CommentModel(
    val key: String,
    val createdAt: String,
    var userKey: String,
    val text: String,
    val postId: String,
    val userAvatar: String = "",
    val userName: String = "",
    val likes: List<String> = listOf(),
    val likedByCurrentUser: Boolean = false
)