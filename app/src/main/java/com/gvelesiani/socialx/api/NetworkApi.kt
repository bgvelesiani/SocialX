package com.gvelesiani.socialx.api

import android.os.Parcelable
import com.gvelesiani.socialx.api.response.NetworkResponse
import kotlinx.android.parcel.Parcelize
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NetworkApi {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("post")
    suspend fun getPosts(): Response<List<Post>>

    @GET("story")
    suspend fun getStories(): Response<List<Story>>

    @POST("post")
    suspend fun createPost(@Body post: PostRequest): Response<Post>

    @POST("auth/signup")
    suspend fun signUp(@Body registerRequest: RegisterRequest): Response<LoginResponse>

    @GET("auth/me")
    suspend fun getUserInfo(): NetworkResponse<UserInfoResponse,Error>

    @POST("post/{post_id}/like")
    suspend fun likeOrDislikePost(@Path("post_id") postId: Int): Response<Post>
}


data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

@Parcelize
data class UserInfoResponse(
    val id: Int = 0,
    val created_at: Long = 0L,
    val name: String = "",
    val email: String = "",
    val avatar: UserImage? = null
): Parcelable

data class LoginRequest(
    val email: String = "",
    val password: String = ""
)

data class LoginResponse(
    val authToken: String,
    val id: Int
)

data class Success(
    val message: String
)
data class PostRe(
    val postId: Int
)

data class PostRequest(
    val description: String = ""
)

data class Post(
    val id: Int = 0,
    val created_at: Long = 0L,
    val likes: Int = 0,
    val userLikes: List<Int> = listOf(),
    val user_Id: Int = 0,
    val title: String = "",
    val description: String = "",
    val userName: String = "",
    val userImage: UserImage? = null
)

@Parcelize
data class Story(
    val created_at: Long = 0L,
    val user_Id: Int = 0,
    val userImage: Image? = null,
    val userName: String = ""
): Parcelable

@Parcelize
data class Image(
    val path: String,
    val name: String,
    val url: String
): Parcelable

@Parcelize
data class UserImage(
    val path: String,
    val name: String,
    val url: String
): Parcelable