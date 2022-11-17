package com.gvelesiani.socialx.domain.model.auth

data class RegisterModel(
    val name: String, val email: String, val password: String
)