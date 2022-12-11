package com.gvelesiani.socialx.domain.useCase.images

import com.gvelesiani.socialx.base.BaseUseCase
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.model.images.AvatarModel
import com.gvelesiani.socialx.domain.repositories.ImageRepository
import javax.inject.Inject

class UploadUserAvatarUseCase @Inject constructor(private val imageRepository: ImageRepository):
BaseUseCase<AvatarModel,Unit>{
    override suspend fun invoke(params: AvatarModel): ResultModel<Unit, String> =
        getResult(imageRepository.uploadUserAvatar(params.image)){}
}
