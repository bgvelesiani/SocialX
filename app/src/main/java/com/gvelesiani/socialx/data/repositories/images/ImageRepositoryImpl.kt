package com.gvelesiani.socialx.data.repositories.images

import com.gvelesiani.socialx.api.ImagesApi
import com.gvelesiani.socialx.api.response.ApiResult
import com.gvelesiani.socialx.api.response.handleApi
import com.gvelesiani.socialx.domain.repositories.ImageRepository
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(val api: ImagesApi) : ImageRepository {
    override suspend fun uploadUserAvatar(file: MultipartBody.Part): ApiResult<ResponseBody> =
        handleApi { api.uploadAvatar(file) }
}