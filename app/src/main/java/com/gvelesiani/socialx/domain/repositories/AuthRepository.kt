package com.gvelesiani.socialx.domain.repositories

import com.gvelesiani.socialx.api.LoginRequest

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest)
}