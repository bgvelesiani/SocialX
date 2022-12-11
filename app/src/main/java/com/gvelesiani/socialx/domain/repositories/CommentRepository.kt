package com.gvelesiani.socialx.domain.repositories

import com.gvelesiani.socialx.api.response.ApiResult
import com.gvelesiani.socialx.data.model.comment.CommentRequestDto
import com.gvelesiani.socialx.data.model.comment.CommentResponseDto

interface CommentRepository {
    suspend fun addComment(postKey:String, request: CommentRequestDto): ApiResult<CommentResponseDto>
    suspend fun getPostComments(postId: String): ApiResult<List<CommentResponseDto>>
    suspend fun likeOrDislikeComment(commentKey: String): ApiResult<CommentResponseDto>
}