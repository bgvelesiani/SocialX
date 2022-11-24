package com.gvelesiani.socialx.domain.useCase.auth

import com.gvelesiani.socialx.api.response.ApiError
import com.gvelesiani.socialx.api.response.ApiException
import com.gvelesiani.socialx.api.response.ApiSuccess
import com.gvelesiani.socialx.data.model.auth.RegisterRequestDto
import com.gvelesiani.socialx.data.model.auth.RegisterResponseDto
import com.gvelesiani.socialx.domain.ResultModel
import com.gvelesiani.socialx.domain.model.auth.RegisterModel
import com.gvelesiani.socialx.domain.model.auth.RegisterResponseModel
import com.gvelesiani.socialx.domain.repositories.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun invoke(registerModel: RegisterModel): ResultModel<RegisterResponseModel, String> {
        return when (val result = repository.register(registerModel.transformToDto())) {
            is ApiError -> {
                ResultModel.Failure("Error")
            }

            is ApiException -> {
                ResultModel.Failure("Error")
            }

            is ApiSuccess -> {
                ResultModel.Success(result.data.transformToModel())
            }
        }
    }
}

fun RegisterModel.transformToDto() = RegisterRequestDto(
    name, email, password
)

fun RegisterResponseDto.transformToModel() = RegisterResponseModel(
    token, userKey
)