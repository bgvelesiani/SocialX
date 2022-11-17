package com.gvelesiani.socialx.domain.useCase.auth

import com.gvelesiani.socialx.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(private val authTokenRepository: AuthTokenRepository) {
    fun invoke(token: String) {
        authTokenRepository.saveToken(token)
    }
}