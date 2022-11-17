package com.gvelesiani.socialx.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkApi {

//    @GET("story")
//    suspend fun getStories(): Response<List<Story>>

    // call with ApiResult


//    @GET("post/{post_id}/comment")
//    suspend fun getCommentsByPost(@Path("post_id") postId: Int): Response<List<Comment>>

    @POST("comment")
    suspend fun addComment(@Body request: CommentRequest): Response<Comment>
}

data class CommentRequest(
    val text: String,
    val postId: Int
)

data class FileUploadRequest(
    val file: MultipartBody.Part?
)

@Parcelize
data class Story(
    val created_at: Long = 0L,
    val user_Id: Int = 0,
    val userImage: Image? = null,
    val userName: String = ""
) : Parcelable

@Parcelize
data class Image(
    val path: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class UserImage(
    val path: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class Comment(
    val id: Int,
    val created_at: Long,
    val text: String,
    val post_id: Int,
    val userAvatar: String,
    val userName: String,
    val user_id: Int
) : Parcelable