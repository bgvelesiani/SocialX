package com.gvelesiani.socialx.domain.model.transformers

import com.gvelesiani.socialx.data.model.auth.LoginRequestDto
import com.gvelesiani.socialx.data.model.auth.LoginResponseDto
import com.gvelesiani.socialx.data.model.auth.RegisterRequestDto
import com.gvelesiani.socialx.data.model.auth.RegisterResponseDto
import com.gvelesiani.socialx.domain.model.auth.LoginModel
import com.gvelesiani.socialx.domain.model.auth.LoginResponseModel
import com.gvelesiani.socialx.domain.model.auth.RegisterModel
import com.gvelesiani.socialx.domain.model.auth.RegisterResponseModel

fun LoginModel.transformToDto(): LoginRequestDto = LoginRequestDto(
    email, password
)

fun LoginResponseDto.transformToModel(): LoginResponseModel = LoginResponseModel(
    token, message
)

fun RegisterModel.transformToDto() = RegisterRequestDto(
    name, email, password
)

fun RegisterResponseDto.transformToModel() = RegisterResponseModel(
    token, userKey
)