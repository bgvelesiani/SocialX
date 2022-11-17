package com.gvelesiani.socialx.domain.useCase.auth

import com.gvelesiani.socialx.api.response.ApiError
import com.gvelesiani.socialx.api.response.ApiException
import com.gvelesiani.socialx.api.response.ApiSuccess
import com.gvelesiani.socialx.data.model.auth.RegisterRequestDto
import com.gvelesiani.socialx.domain.ResultFace
import com.gvelesiani.socialx.domain.model.auth.RegisterModel
import com.gvelesiani.socialx.domain.model.auth.UserInfoResponseModel
import com.gvelesiani.socialx.domain.repositories.AuthRepository
import com.gvelesiani.socialx.domain.useCase.userinfo.transformToModel
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun invoke(registerModel: RegisterModel): ResultFace<UserInfoResponseModel, String> {
        return when (val result = repository.register(registerModel.transformToDto())) {
            is ApiError -> {
                ResultFace.Failure("Error")
            }

            is ApiException -> {
                ResultFace.Failure("Error")
            }

            is ApiSuccess -> {
                ResultFace.Success(result.data.transformToModel())
            }
        }
    }
}

fun RegisterModel.transformToDto() = RegisterRequestDto(
    name, email, password
)