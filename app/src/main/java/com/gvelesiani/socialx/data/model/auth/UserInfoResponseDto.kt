package com.gvelesiani.socialx.data.model.auth

import com.google.gson.annotations.SerializedName

data class UserInfoResponseDto(
    @SerializedName("key")
    val key: String="",
    @SerializedName("password")
    val password: String="",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("avatar")
    val avatar: String = ""
)