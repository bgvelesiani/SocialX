package com.gvelesiani.socialx.domain

import java.io.Serializable

sealed class ResultModel<out T : Any, out E : Any> : Serializable {
    data class Success<out T : Any>(val value: T) :
        ResultModel<T, Nothing>()

    data class Failure<out E : Any>(
        val error: E? = null,
        val ex: Throwable? = null
    ) : ResultModel<Nothing, E>()
}