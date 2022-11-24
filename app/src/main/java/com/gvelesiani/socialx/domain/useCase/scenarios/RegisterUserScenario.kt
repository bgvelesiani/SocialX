package com.gvelesiani.socialx.domain.useCase.scenarios

import com.gvelesiani.socialx.domain.ResultModel
import com.gvelesiani.socialx.domain.model.auth.RegisterModel
import com.gvelesiani.socialx.domain.model.auth.RegisterResponseModel
import com.gvelesiani.socialx.domain.useCase.auth.RegisterUserUseCase
import com.gvelesiani.socialx.domain.useCase.auth.SaveTokenUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

class RegisterUserScenario @Inject constructor(
    private val saveTokenUseCase: SaveTokenUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) {
    suspend fun invoke(registerModel: RegisterModel): ResultModel<RegisterResponseModel, String> {
        return when (val result = registerUserUseCase.invoke(registerModel)) {
            is ResultModel.Failure -> {
                ResultModel.Failure(result.ex?.message)
            }

            is ResultModel.Success -> {
                saveTokenUseCase.invoke(result.value.token)
                delay(1000)
                ResultModel.Success(result.value)
            }
        }
    }
}