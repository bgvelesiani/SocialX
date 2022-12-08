package com.gvelesiani.socialx.domain.model.comments

data class CommentModel(
    val key: String,
    val createdAt: String,
    val userKey: String,
    val text: String,
    val postId: String,
    val userAvatar: String = "",
    val userName: String = ""
)
