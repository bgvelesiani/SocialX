package com.gvelesiani.socialx.data.model.posts

import com.google.gson.annotations.SerializedName

data class PostRequestDto(
    @SerializedName("description")
    val description: String = ""
)