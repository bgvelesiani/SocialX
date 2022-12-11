package com.gvelesiani.socialx.api

import com.gvelesiani.socialx.data.model.comment.CommentRequestDto
import com.gvelesiani.socialx.data.model.comment.CommentResponseDto
import retrofit2.Response
import retrofit2.http.*

interface CommentsApi {
    @POST("post/{post_id}/add_comment")
    suspend fun addComment(
        @Path("post_id") postId: String, @Body request: CommentRequestDto
    ): Response<CommentResponseDto>

    @GET("post/{post_id}/comments")
    suspend fun getPostComments(@Path("post_id") postId: String): Response<List<CommentResponseDto>>


    @PUT("comments/{key}/like")
    suspend fun likeOrDislikeComment(@Path("key") postKey: String): Response<CommentResponseDto>
}