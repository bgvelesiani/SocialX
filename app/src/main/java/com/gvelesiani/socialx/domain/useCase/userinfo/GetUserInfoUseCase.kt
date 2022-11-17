package com.gvelesiani.socialx.domain.useCase.userinfo

import com.gvelesiani.socialx.api.response.ApiError
import com.gvelesiani.socialx.api.response.ApiException
import com.gvelesiani.socialx.api.response.ApiSuccess
import com.gvelesiani.socialx.data.model.auth.UserInfoResponseDto
import com.gvelesiani.socialx.domain.ResultFace
import com.gvelesiani.socialx.domain.model.auth.UserInfoResponseModel
import com.gvelesiani.socialx.domain.repositories.AuthRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun invoke(): ResultFace<UserInfoResponseModel, String> {
        return when (val result = repository.getUserInfo()) {
            is ApiError -> {
                ResultFace.Failure(result.message)
            }

            is ApiException -> {
                ResultFace.Failure(result.e.message)
            }

            is ApiSuccess -> {
                ResultFace.Success(result.data.transformToModel())
            }
        }
    }
}

fun UserInfoResponseDto.transformToModel() = UserInfoResponseModel(
    key, password, name, email, avatar
)