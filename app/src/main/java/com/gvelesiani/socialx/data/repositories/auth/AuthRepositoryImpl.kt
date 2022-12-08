package com.gvelesiani.socialx.data.repositories.auth

import com.gvelesiani.socialx.api.AuthorizationApi
import com.gvelesiani.socialx.api.response.ApiResult
import com.gvelesiani.socialx.api.response.handleApi
import com.gvelesiani.socialx.data.model.auth.*
import com.gvelesiani.socialx.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthorizationApi
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequestDto): ApiResult<LoginResponseDto> =
        handleApi { apiService.login(loginRequest) }

    override suspend fun register(registerRequest: RegisterRequestDto): ApiResult<RegisterResponseDto> =
        handleApi { apiService.register(registerRequest) }

    override suspend fun getUserInfo(): ApiResult<UserInfoResponseDto> =
        handleApi { apiService.getUserInfo() }
}