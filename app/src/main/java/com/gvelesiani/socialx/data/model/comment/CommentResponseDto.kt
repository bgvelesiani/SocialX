package com.gvelesiani.socialx.data.model.comment

import com.google.gson.annotations.SerializedName

data class CommentResponseDto(
    @SerializedName("key")
    val key: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("user_key")
    val userKey: String? = "",
    @SerializedName("text")
    val text: String,
    @SerializedName("post_id")
    val postId: String,
    @SerializedName("user_avatar")
    val userAvatar: String? = "",
    @SerializedName("user_name")
    val userName: String? = ""
)
