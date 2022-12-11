package com.gvelesiani.socialx.domain.useCase.posts

import com.gvelesiani.socialx.base.BaseUseCase
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.model.posts.PostModel
import com.gvelesiani.socialx.domain.model.transformers.transformToModel
import com.gvelesiani.socialx.domain.repositories.PostRepository
import javax.inject.Inject

class LikeOrDislikePostUseCase @Inject constructor(private val repository: PostRepository) :
    BaseUseCase<String, PostModel> {
    override suspend fun invoke(params: String): ResultModel<PostModel, String> =
        getResult(repository.likeOrDislikePost(params)) { it.transformToModel("") }
}
