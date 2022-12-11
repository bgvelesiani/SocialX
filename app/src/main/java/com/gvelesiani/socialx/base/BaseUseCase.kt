package com.gvelesiani.socialx.base

import com.gvelesiani.socialx.api.response.ApiError
import com.gvelesiani.socialx.api.response.ApiException
import com.gvelesiani.socialx.api.response.ApiResult
import com.gvelesiani.socialx.api.response.ApiSuccess
import com.gvelesiani.socialx.domain.model.ResultModel

interface BaseUseCase<Params, Result : Any> {
    suspend operator fun invoke(params: Params): ResultModel<Result, String>

    fun <Dto : Any, Model : Any> getResult(
        result: ApiResult<Dto>, mapSuccessToModel: (Dto) -> Model
    ): ResultModel<Model, String> {
        return when (result) {
            is ApiSuccess -> ResultModel.Success(mapSuccessToModel(result.data))
            is ApiError -> ResultModel.Failure(result.message)
            is ApiException -> ResultModel.Failure(result.e.message)
        }
    }
}