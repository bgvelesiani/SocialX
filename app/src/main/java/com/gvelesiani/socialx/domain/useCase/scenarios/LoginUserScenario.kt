package com.gvelesiani.socialx.domain.useCase.scenarios

import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.model.auth.LoginModel
import com.gvelesiani.socialx.domain.useCase.auth.LoginUserUseCase
import com.gvelesiani.socialx.domain.useCase.auth.SaveTokenUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoginUserScenario @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) {
    suspend fun invoke(loginRequest: LoginModel): ResultModel<String, String> {
        return when (val result = loginUserUseCase.invoke(loginRequest)) {
            is ResultModel.Failure -> {
                ResultModel.Failure(result.error)
            }

            is ResultModel.Success -> {
                saveTokenUseCase.invoke(result.value.token)
                delay(1000)
                ResultModel.Success(result.value.token)
            }
        }
    }
}
