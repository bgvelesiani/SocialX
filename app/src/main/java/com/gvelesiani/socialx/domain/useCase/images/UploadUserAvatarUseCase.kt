package com.gvelesiani.socialx.domain.useCase.images

import com.gvelesiani.socialx.api.response.ApiError
import com.gvelesiani.socialx.api.response.ApiException
import com.gvelesiani.socialx.api.response.ApiSuccess
import com.gvelesiani.socialx.domain.ResultFace
import com.gvelesiani.socialx.domain.model.images.AvatarModel
import com.gvelesiani.socialx.domain.repositories.ImageRepository
import javax.inject.Inject

class UploadUserAvatarUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    suspend fun invoke(avatar: AvatarModel): ResultFace<Unit, String> {
        return when (val result = imageRepository.uploadUserAvatar(avatar.image)) {
            is ApiError -> {
                ResultFace.Failure(result.message)
            }

            is ApiException -> {
                ResultFace.Failure(result.e.message)
            }

            is ApiSuccess -> {
                ResultFace.Success(Unit)
            }
        }
    }
}