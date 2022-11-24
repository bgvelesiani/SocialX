package com.gvelesiani.socialx.api

import com.gvelesiani.socialx.data.model.posts.PostResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface PostsApi {
    @GET("post")
    suspend fun getPosts(): Response<List<PostResponseDto>>

    @Headers(
        "Accept:application/json"
    )
    @Multipart
    @POST("post/create")
    suspend fun createPost(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Response<ResponseBody>

    @PUT("post/like/{key}")
    suspend fun likeOrDislikePost(@Path("key") postKey: String): Response<PostResponseDto>
}