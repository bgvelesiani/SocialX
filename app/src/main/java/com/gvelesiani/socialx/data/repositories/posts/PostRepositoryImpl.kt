package com.gvelesiani.socialx.data.repositories.posts

import com.gvelesiani.socialx.api.PostsApi
import com.gvelesiani.socialx.api.response.ApiResult
import com.gvelesiani.socialx.api.response.handleApi
import com.gvelesiani.socialx.data.model.posts.PostRequestDto
import com.gvelesiani.socialx.data.model.posts.PostResponseDto
import com.gvelesiani.socialx.domain.repositories.PostRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val api: PostsApi) : PostRepository {
    override suspend fun getPosts(): ApiResult<List<PostResponseDto>> = handleApi { api.getPosts() }

    override suspend fun createPost(post: PostRequestDto): ApiResult<ResponseBody> =
        handleApi { api.createPost(post.image, post.description) }

    override suspend fun likeOrDislikePost(postKey: String): ApiResult<PostResponseDto> =
        handleApi { api.likeOrDislikePost(postKey) }
}