package com.gvelesiani.socialx.domain.model.auth

data class LoginResponseModel(
    val token: String,
    val message: String
)