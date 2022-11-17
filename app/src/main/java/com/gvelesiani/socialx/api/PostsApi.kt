package com.gvelesiani.socialx.api

import com.gvelesiani.socialx.data.model.posts.PostRequestDto
import com.gvelesiani.socialx.data.model.posts.PostResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostsApi {
    @GET("post")
    suspend fun getPosts(): Response<List<PostResponseDto>>

    @POST("post/create")
    suspend fun createPost(@Body post: PostRequestDto): Response<PostResponseDto>

    @PUT("post/like/{key}")
    suspend fun likeOrDislikePost(@Path("key") postKey: String): Response<PostResponseDto>
}