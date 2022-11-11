package com.gvelesiani.socialx.data.repositories.auth

import android.content.SharedPreferences
import com.gvelesiani.socialx.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class AuthTokenRepositoryImpl @Inject constructor(private val preferences: SharedPreferences) :
    AuthTokenRepository {
    override fun saveToken(token: String) {
        preferences.edit().putString("token", token).apply()
    }

    override fun getToken(): String {
        return preferences.getString("token", "empty").toString()
    }
}