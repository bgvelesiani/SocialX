package com.gvelesiani.socialx.domain.repositories

import com.gvelesiani.socialx.api.response.ApiResult
import com.gvelesiani.socialx.data.model.auth.*

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequestDto): ApiResult<LoginResponseDto>
    suspend fun register(registerRequest: RegisterRequestDto): ApiResult<RegisterResponseDto>
    suspend fun getUserInfo(): ApiResult<UserInfoResponseDto>
}