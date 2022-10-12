package com.nmt.stockcheck.model

import com.nmt.stockcheck.view.exception.AppException
import com.google.gson.annotations.SerializedName


class Resource<T> private constructor(val status: Resource.Status, val data: T?, val exception: AppException?) {
    enum class Status {
        SUCCESS, ERROR,UNAUTHORIZED
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(exception: AppException?): Resource<T> {
            return Resource(Status.ERROR, null, exception)
        }

    }
}

data class ErrorResponse (
        @SerializedName("Message")
        var message: String? =null
)


interface BaseApiResponse {

}

