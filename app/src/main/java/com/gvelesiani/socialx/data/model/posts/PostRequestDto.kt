package com.gvelesiani.socialx.data.model.posts

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody

data class PostRequestDto(
    val image: MultipartBody.Part,
    val description: RequestBody
)