package com.gvelesiani.socialx.interceptors

import com.gvelesiani.socialx.domain.repositories.AuthTokenRepository
import okhttp3.Interceptor

class AuthInterceptor(private val authTokenRepository: AuthTokenRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val req = chain.request()

        val authToken = authTokenRepository.getToken()
        val url = req.newBuilder().addHeader("Authorization", "Bearer $authToken").build()
        return chain.proceed(url)
    }
}