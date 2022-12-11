package com.gvelesiani.socialx.domain.model.transformers

import com.gvelesiani.socialx.data.model.comment.CommentRequestDto
import com.gvelesiani.socialx.data.model.comment.CommentResponseDto
import com.gvelesiani.socialx.domain.model.comments.CommentModel
import com.gvelesiani.socialx.domain.model.comments.CommentRequestModel


fun CommentRequestModel.transformToDto() = CommentRequestDto(text, userAvatar, userName)

fun CommentResponseDto.transformToModel() = CommentModel(
    key, createdAt, userKey ?: "", text, postId, userAvatar ?: "",
    userName ?: "", likes, likedByCurrentUser = likes.contains(userKey)
)