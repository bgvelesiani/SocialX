package com.gvelesiani.socialx.domain.useCase.posts

import android.content.Context
import com.gvelesiani.socialx.base.BaseUseCase
import com.gvelesiani.socialx.data.model.posts.PostRequestDto
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.repositories.PostRepository
import com.gvelesiani.socialx.presentation.createpost.createPartFromString
import dagger.hilt.android.qualifiers.ApplicationContext
import me.echodev.resizer.Resizer
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
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
): BaseUseCase<Pair<File?,String>,Unit> {
    override suspend fun invoke(params: Pair<File?, String>): ResultModel<Unit, String> =
    getResult(repository.createPost(postRequest(params.first,params.second))){}

    private fun postRequest(image: File?, description: String): PostRequestDto {
        val descriptionPart = createPartFromString(description)

        val attachmentEmpty = "".toRequestBody("text/plain".toMediaTypeOrNull())
        val emptyFile = createFormData("file", "", attachmentEmpty)

        return PostRequestDto(
            imagePart(image) ?: emptyFile,
            descriptionPart
        )
    }

    private fun imagePart(image: File?): MultipartBody.Part? {
        val imagePart = resizedImage(image)?.let {
            val requestFile: RequestBody =
                it.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            createFormData(
                "file",
                it.name,
                requestFile
            )
        }
        return imagePart
    }

    private fun resizedImage(image: File?): File? {
        val resizedImage = image?.let {
            Resizer(context)
                .setTargetLength(1080)
                .setQuality(80)
                .setOutputFormat("JPEG")
                .setSourceImage(it)
                .resizedFile
        }
        return resizedImage
    }
}