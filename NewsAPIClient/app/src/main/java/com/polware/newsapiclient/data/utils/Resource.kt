package com.polware.newsapiclient.data.utils

// Monitor states for APIResponse model (loading, success, fail)
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(errorMessage: String) : Resource<T>(message = errorMessage)
}
