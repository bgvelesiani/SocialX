package com.gvelesiani.socialx.presentation.createpost

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.socialx.api.NetworkApi
import com.gvelesiani.socialx.domain.helpers.uriPath.URIPathHelper
import com.gvelesiani.socialx.domain.model.posts.PostModel
import com.gvelesiani.socialx.domain.model.posts.PostRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class CreatePostVM @Inject constructor(
    private val uriPathHelper: URIPathHelper
) : ViewModel() {
    val _something: MutableLiveData<String> = MutableLiveData()
    val som: LiveData<String> = _something
    fun createPost(postRequest: PostRequestModel) {
        viewModelScope.launch {
//            when (val result = networkApi.createPost(postRequest)) {

//            }
        }
    }

    fun uploadImage(file: MultipartBody.Part) {
        viewModelScope.launch {
//            val result = networkApi.upload(
//                file
//            )
//            if (result.isSuccessful) {
//            }
        }
    }

    fun getURIPathHelper(): URIPathHelper = uriPathHelper
}