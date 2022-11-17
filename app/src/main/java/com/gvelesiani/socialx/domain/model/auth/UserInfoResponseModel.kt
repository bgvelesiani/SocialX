package com.gvelesiani.socialx.domain.model.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoResponseModel(
    val key: String = "",
    val password: String = "",
    val name: String = "",
    val email: String = "",
    val avatar: String = ""
): Parcelable