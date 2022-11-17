package com.gvelesiani.socialx.domain.useCase.scenarios

import com.gvelesiani.socialx.domain.ResultFace
import com.gvelesiani.socialx.domain.model.auth.LoginModel
import com.gvelesiani.socialx.domain.useCase.auth.LoginUserUseCase
import com.gvelesiani.socialx.domain.useCase.auth.SaveTokenUseCase
import javax.inject.Inject

class LoginUserScenario @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) {
    suspend fun invoke(loginRequest: LoginModel): ResultFace<String, String> {
        return when (val result = loginUserUseCase.invoke(loginRequest)) {
            is ResultFace.Failure -> {
                ResultFace.Failure(result.error)
            }

            is ResultFace.Success -> {
                saveTokenUseCase.invoke(result.value.token)
                ResultFace.Success(result.value.token)
            }
        }
    }
}
