package com.zek.cryptotracker.core.data.networking

import com.zek.cryptotracker.core.domain.util.NetworkError
import com.zek.cryptotracker.core.domain.util.Result
import retrofit2.Response


fun <T> responseToResult(
    response: Response<T>
): Result<T, NetworkError> {
    return when (response.code()) {
        in 200..299 -> {
            response.body()?.let {
                Result.Success(it)
            } ?: Result.Error(NetworkError.UNKNOWN)
        }
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> {
            Result.Error(NetworkError.UNKNOWN)
        }
    }
}