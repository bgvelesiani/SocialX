package com.gvelesiani.socialx.domain.repositories

import com.gvelesiani.socialx.api.response.ApiResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody

interface ImageRepository {
    suspend fun uploadUserAvatar(file: MultipartBody.Part): ApiResult<ResponseBody>
}