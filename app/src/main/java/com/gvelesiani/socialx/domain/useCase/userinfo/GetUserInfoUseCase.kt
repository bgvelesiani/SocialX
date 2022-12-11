package com.gvelesiani.socialx.domain.useCase.userinfo

import com.gvelesiani.socialx.base.BaseUseCase
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.model.auth.UserInfoResponseModel
import com.gvelesiani.socialx.domain.model.transformers.transformToModel
import com.gvelesiani.socialx.domain.repositories.AuthRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val repository: AuthRepository) :
    BaseUseCase<Unit, UserInfoResponseModel> {
    override suspend fun invoke(params: Unit): ResultModel<UserInfoResponseModel, String> =
        getResult(repository.getUserInfo()) { it.transformToModel() }
}
