package com.gvelesiani.socialx.domain.useCase.auth

import com.gvelesiani.socialx.base.BaseUseCase
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.model.auth.RegisterModel
import com.gvelesiani.socialx.domain.model.auth.RegisterResponseModel
import com.gvelesiani.socialx.domain.model.transformers.transformToDto
import com.gvelesiani.socialx.domain.model.transformers.transformToModel
import com.gvelesiani.socialx.domain.repositories.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: AuthRepository) :
    BaseUseCase<RegisterModel, RegisterResponseModel> {
    override suspend fun invoke(params: RegisterModel): ResultModel<RegisterResponseModel, String> =
        getResult(repository.register(params.transformToDto())) { it.transformToModel() }

}
