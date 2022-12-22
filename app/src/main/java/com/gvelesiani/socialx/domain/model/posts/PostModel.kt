package com.gvelesiani.socialx.domain.model.posts

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostModel(
    val key: String = "",
    val createdAt: String = "",
    val likes: List<String> = listOf(),
    val userKey: String = "",
    val comments: List<String> = listOf(),
    val description: String = "",
    val userName: String = "",
    val image: String = "",
    val userImage: String = "",
    val likedByCurrentUser: Boolean = false
): Parcelable