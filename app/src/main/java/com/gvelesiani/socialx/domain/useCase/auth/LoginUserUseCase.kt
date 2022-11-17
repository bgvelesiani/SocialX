package com.gvelesiani.socialx.domain.useCase.auth

import com.gvelesiani.socialx.api.response.ApiError
import com.gvelesiani.socialx.api.response.ApiException
import com.gvelesiani.socialx.api.response.ApiSuccess
import com.gvelesiani.socialx.data.model.auth.LoginRequestDto
import com.gvelesiani.socialx.data.model.auth.LoginResponseDto
import com.gvelesiani.socialx.domain.ResultFace
import com.gvelesiani.socialx.domain.model.auth.LoginModel
import com.gvelesiani.socialx.domain.model.auth.LoginResponseModel
import com.gvelesiani.socialx.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun invoke(loginModel: LoginModel): ResultFace<LoginResponseModel, String> {
        return when (val result = repository.login(loginModel.transformToDto())) {
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

fun LoginModel.transformToDto(): LoginRequestDto = LoginRequestDto(
    email, password
)

fun LoginResponseDto.transformToModel(): LoginResponseModel = LoginResponseModel(
    token, message
)