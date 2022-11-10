package com.gvelesiani.socialx.domain.repositories

interface AuthTokenRepository {
    fun saveToken(token: String)
    fun getToken(): String
}