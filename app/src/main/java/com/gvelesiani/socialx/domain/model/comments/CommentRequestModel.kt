package com.gvelesiani.socialx.domain.model.comments

data class CommentRequestModel(
    val text: String,
    val userAvatar: String,
    val userName: String
)
