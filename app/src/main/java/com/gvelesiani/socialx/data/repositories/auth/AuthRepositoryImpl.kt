package com.gvelesiani.socialx.data.repositories.auth

import com.gvelesiani.socialx.api.LoginRequest
import com.gvelesiani.socialx.api.NetworkApi
import com.gvelesiani.socialx.domain.repositories.AuthRepository
import com.gvelesiani.socialx.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: NetworkApi,
    private val tokenRepository: AuthTokenRepository,
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest) {
        val loginResponse = apiService.login(loginRequest)
        if (loginResponse.isSuccessful) {
            loginResponse.body()?.let {
                tokenRepository.saveToken(it.authToken)
            }
        }
    }
}