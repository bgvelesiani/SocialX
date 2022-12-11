package com.gvelesiani.socialx.domain.useCase.posts

import com.gvelesiani.socialx.base.BaseUseCase
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.model.posts.PostModel
import com.gvelesiani.socialx.domain.model.transformers.transformToModel
import com.gvelesiani.socialx.domain.repositories.PostRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postRepository: PostRepository) :
    BaseUseCase<String, List<PostModel>> {
    override suspend fun invoke(params: String): ResultModel<List<PostModel>, String> =
        getResult(postRepository.getPosts()){it.map { dto -> dto.transformToModel(params) }}
}