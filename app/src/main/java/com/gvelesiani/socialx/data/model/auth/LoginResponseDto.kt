package com.gvelesiani.socialx.data.model.auth

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("access_token")
    val token: String,
    @SerializedName("message")
    val message: String
)