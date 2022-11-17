package com.gvelesiani.socialx.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImagesApi {
    @Headers(
        "Accept:application/json"
    )
    @Multipart
    @POST("upload_avatar")
    suspend fun uploadAvatar(
        @Part file: MultipartBody.Part,
    ): Response<ResponseBody>


    @Multipart
    @POST("upload_post_image")
    suspend fun uploadPostImage(
        @Part file: MultipartBody.Part,
        @Part key: MultipartBody.Part
    ): Response<ResponseBody>
}