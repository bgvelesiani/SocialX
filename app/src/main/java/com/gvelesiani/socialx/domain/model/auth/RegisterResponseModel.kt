package com.gvelesiani.socialx.domain.model.auth

data class RegisterResponseModel(
    val token: String,
    val userKey: String
)