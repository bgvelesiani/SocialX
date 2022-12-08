package com.gvelesiani.socialx.data.model.posts

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class PostRequestDto(
    val image: MultipartBody.Part,
    val description: RequestBody
)