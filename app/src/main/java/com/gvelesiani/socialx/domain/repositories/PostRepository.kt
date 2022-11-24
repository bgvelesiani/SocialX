package com.gvelesiani.socialx.domain.repositories

import com.gvelesiani.socialx.api.response.ApiResult
import com.gvelesiani.socialx.data.model.posts.PostRequestDto
import com.gvelesiani.socialx.data.model.posts.PostResponseDto
import okhttp3.ResponseBody

interface PostRepository {
    suspend fun getPosts(): ApiResult<List<PostResponseDto>>
    suspend fun createPost(post: PostRequestDto): ApiResult<ResponseBody>
    suspend fun likeOrDislikePost(postKey: String): ApiResult<PostResponseDto>
}