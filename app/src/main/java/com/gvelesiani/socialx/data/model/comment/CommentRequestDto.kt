package com.gvelesiani.socialx.data.model.comment

import com.google.gson.annotations.SerializedName

data class CommentRequestDto(
    val text: String,
    @SerializedName("user_avatar")
    val userAvatar: String,
    @SerializedName("user_name")
    val userName: String
)
