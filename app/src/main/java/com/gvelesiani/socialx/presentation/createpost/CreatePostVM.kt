package com.gvelesiani.socialx.presentation.createpost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.socialx.api.NetworkApi
import com.gvelesiani.socialx.api.PostRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostVM @Inject constructor(
    private val networkApi: NetworkApi
) : ViewModel() {
    val something: MutableLiveData<String> = MutableLiveData()
    val som: LiveData<String> = something
    fun createPost(postRequest: PostRequest){
        viewModelScope.launch {
            val g = networkApi.createPost(postRequest)
            if(g.isSuccessful){
                something.value = "Created"
            }
        }
    }
}