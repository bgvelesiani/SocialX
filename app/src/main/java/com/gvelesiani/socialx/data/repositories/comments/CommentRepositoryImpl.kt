package com.gvelesiani.socialx.data.repositories.comments

import com.gvelesiani.socialx.api.CommentsApi
import com.gvelesiani.socialx.api.response.ApiResult
import com.gvelesiani.socialx.api.response.handleApi
import com.gvelesiani.socialx.data.model.comment.CommentRequestDto
import com.gvelesiani.socialx.data.model.comment.CommentResponseDto
import com.gvelesiani.socialx.domain.repositories.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(private val api: CommentsApi) : CommentRepository {
    override suspend fun addComment(
        postKey: String, request: CommentRequestDto
    ): ApiResult<CommentResponseDto> = handleApi { api.addComment(postKey, request) }

    override suspend fun getPostComments(postId: String): ApiResult<List<CommentResponseDto>> =
        handleApi { api.getPostComments(postId) }
}