package com.gvelesiani.socialx.api

import com.gvelesiani.socialx.data.model.auth.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthorizationApi {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequestDto): Response<LoginResponseDto>

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequestDto): Response<RegisterResponseDto>

    @GET("me")
    suspend fun getUserInfo(): Response<UserInfoResponseDto>
}