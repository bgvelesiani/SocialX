package com.gvelesiani.socialx.domain.repositories

import com.gvelesiani.socialx.api.response.ApiResult
import okhttp3.MultipartBody
import okhttp3.ResponseBody

interface ImageRepository {
    suspend fun uploadUserAvatar(file: MultipartBody.Part): ApiResult<ResponseBody>
    suspend fun uploadPostImage(file: MultipartBody.Part, key: MultipartBody.Part): ApiResult<ResponseBody>
}