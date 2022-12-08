package com.gvelesiani.socialx.domain.useCase.posts

import android.content.Context
import com.gvelesiani.socialx.api.response.ApiError
import com.gvelesiani.socialx.api.response.ApiException
import com.gvelesiani.socialx.api.response.ApiSuccess
import com.gvelesiani.socialx.data.model.posts.PostRequestDto
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.repositories.PostRepository
import com.gvelesiani.socialx.presentation.createpost.createPartFromString
import dagger.hilt.android.qualifiers.ApplicationContext
import me.echodev.resizer.Resizer
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


//enum class UseCaseErrors {
//    General, EmptyError
//}

class CreatePostUseCase @Inject constructor(
    private val repository: PostRepository,
    @ApplicationContext val context: Context
) {
    suspend fun invoke(image: File?, description: String): ResultModel<Unit, String> {
        val resizedImage = image?.let {
            Resizer(context)
                .setTargetLength(1080)
                .setQuality(80)
                .setOutputFormat("JPEG")
                .setSourceImage(it)
                .resizedFile
        }

        val imagePart = resizedImage?.let {
            val requestFile: RequestBody =
                it.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            createFormData(
                "file",
                it.name,
                requestFile
            )
        }
        val descriptionPart = createPartFromString(description)

        val attachmentEmpty = "".toRequestBody("text/plain".toMediaTypeOrNull())
        val emptyFile = createFormData("file", "", attachmentEmpty)

        val postRequest = PostRequestDto(
            imagePart ?: emptyFile,
            descriptionPart
        )

        return when (val result = repository.createPost(postRequest)) {
            is ApiError -> {
                ResultModel.Failure(result.message)
            }

            is ApiException -> {
                ResultModel.Failure(result.e.message)
            }

            is ApiSuccess -> {
                ResultModel.Success(Unit)
            }
        }
    }
}