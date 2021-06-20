package com.ranatalal.tmdbexample.helper

import com.ranatalal.tmdbexample.views.models.ErrorModel


data class Resource<out T>(val status: Status, val data: T?, val error: ErrorModel?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(error: ErrorModel? = null): Resource<T> {
            var newError = error
            if (newError == null) {
                newError =
                    ErrorModel()
            }
            return Resource(
                Status.ERROR,
                null,
                newError
            )
        }

        fun <T> loading(): Resource<T> {
            return Resource(
                Status.LOADING,
                null,
                null
            )
        }

        fun <T, G> mapToTransformedObject(
            resource: Resource<T>,
            mapFunction: (T) -> G?
        ): Resource<G> {
            return when (resource.status) {
                Status.LOADING -> loading()
                Status.ERROR -> error(resource.error)
                Status.SUCCESS -> {
                    if (resource.data == null) {
                        error()
                    } else {
                        val newData = mapFunction(resource.data)
                        if (newData != null)
                            success(newData)
                        else
                            error()
                    }
                }
            }
        }

    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}