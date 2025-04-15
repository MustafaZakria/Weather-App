package com.zek.cryptotracker.core.domain.util

typealias DomainError = Error

sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: DomainError>(val error: E): Result<Nothing, E>
}

inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}


//sealed interface Result<out D> {
//    data class Success<out D>(val data: D): Result<D>
//    data class Error(val error: Throwable? = null): Result<Nothing>
//}
//
//inline fun <T, R> Result<T>.map(map: (T) -> R): Result<R> {
//    return when(this) {
//        is Result.Error -> Result.Error(error)
//        is Result.Success -> Result.Success(map(data))
//    }
//}
//
//inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
//    return when(this) {
//        is Result.Error -> this
//        is Result.Success -> {
//            action(data)
//            this
//        }
//    }
//}
//
//inline fun <T> Result<T>.onError(action: (Throwable?) -> Unit): Result<T> {
//    return when(this) {
//        is Result.Error -> {
//            action(error)
//            this
//        }
//        is Result.Success -> this
//    }
//}