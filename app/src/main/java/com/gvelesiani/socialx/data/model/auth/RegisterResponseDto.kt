package com.gvelesiani.socialx.data.model.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponseDto(
    @SerializedName("token")
    val token: String,
    @SerializedName("user_key")
    val userKey: String
)