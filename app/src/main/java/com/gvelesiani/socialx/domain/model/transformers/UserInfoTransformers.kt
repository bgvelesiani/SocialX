package com.gvelesiani.socialx.domain.model.transformers

import com.gvelesiani.socialx.data.model.auth.UserInfoResponseDto
import com.gvelesiani.socialx.domain.model.auth.UserInfoResponseModel


fun UserInfoResponseDto.transformToModel() = UserInfoResponseModel(
    key, password, name, email, avatar
)