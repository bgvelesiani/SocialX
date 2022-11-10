package com.gvelesiani.socialx.domain.useCase

import android.content.SharedPreferences
import com.gvelesiani.socialx.api.LoginRequest
import com.gvelesiani.socialx.api.NetworkApi
import com.gvelesiani.socialx.api.response.NetworkResponse
import com.gvelesiani.socialx.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class LoginUserScenario @Inject constructor(
    private val preferences: SharedPreferences,
    private val authRepository: AuthTokenRepository,
    val api: NetworkApi
) {
    suspend fun invoke(loginRequest: LoginRequest, login: () -> Unit) {
        val loginResponse = api.login(loginRequest)
        if (loginResponse.isSuccessful) {
            loginResponse.body()?.authToken?.let { authRepository.saveToken(it) }
            when (val userInfo = api.getUserInfo()) {
                is NetworkResponse.ApiError -> {}
                is NetworkResponse.NetworkError -> {}
                is NetworkResponse.Success -> {
                    preferences.edit().putInt("userId", userInfo.body.id).apply()
                    login.invoke()
                }

                is NetworkResponse.UnknownError -> {}
            }
        }
    }
}
