package com.gvelesiani.socialx.domain.useCase.auth

import com.gvelesiani.socialx.base.BaseUseCase
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.model.auth.LoginModel
import com.gvelesiani.socialx.domain.model.auth.LoginResponseModel
import com.gvelesiani.socialx.domain.model.transformers.transformToDto
import com.gvelesiani.socialx.domain.model.transformers.transformToModel
import com.gvelesiani.socialx.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val repository: AuthRepository) :
    BaseUseCase<LoginModel, LoginResponseModel> {
    override suspend fun invoke(params: LoginModel): ResultModel<LoginResponseModel, String> =
        getResult(repository.login(params.transformToDto())) { it.transformToModel() }
}
