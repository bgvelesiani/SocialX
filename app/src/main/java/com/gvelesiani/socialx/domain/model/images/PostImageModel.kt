package com.gvelesiani.socialx.domain.model.images

import okhttp3.MultipartBody

data class PostImageModel(
    val image: MultipartBody.Part,
    val key: MultipartBody.Part
)