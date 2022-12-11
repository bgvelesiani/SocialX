package com.gvelesiani.socialx.domain.model.transformers

import com.gvelesiani.socialx.data.model.posts.PostResponseDto
import com.gvelesiani.socialx.domain.model.posts.PostModel

fun PostResponseDto.transformToModel(currentUserKey: String) = PostModel(
    key, createdAt, likes, userKey, comments, description, userName, image, userImage,
    likedByCurrentUser = likes.contains(currentUserKey)
)