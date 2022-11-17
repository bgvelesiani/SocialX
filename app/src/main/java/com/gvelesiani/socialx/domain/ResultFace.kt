package com.gvelesiani.socialx.domain

import java.io.Serializable

sealed class ResultFace<out T : Any, out E : Any> : Serializable {
    data class Success<out T : Any>(val value: T) :
        ResultFace<T, Nothing>()

    data class Failure<out E : Any>(
        val error: E? = null,
        val ex: Throwable? = null
    ) : ResultFace<Nothing, E>()
}