package com.gvelesiani.socialx.domain.useCase.posts

import com.gvelesiani.socialx.api.response.ApiError
import com.gvelesiani.socialx.api.response.ApiException
import com.gvelesiani.socialx.api.response.ApiSuccess
import com.gvelesiani.socialx.domain.ResultModel
import com.gvelesiani.socialx.domain.model.posts.PostModel
import com.gvelesiani.socialx.domain.repositories.PostRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend fun invoke(): ResultModel<List<PostModel>, String> {
        return when (val result = postRepository.getPosts()) {
            is ApiError -> {
                ResultModel.Failure(result.message)
            }

            is ApiException -> {
                ResultModel.Failure(result.e.message)
            }

            is ApiSuccess -> {
                ResultModel.Success(result.data.map { it.transformToModel() })
            }
        }
    }
}