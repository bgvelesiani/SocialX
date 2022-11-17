package com.gvelesiani.socialx.data.model.auth

import com.google.gson.annotations.SerializedName

data class RegisterRequestDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)