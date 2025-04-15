package com.zek.cryptotracker.core.data.networking

import com.zek.cryptotracker.core.domain.util.NetworkError
import com.zek.cryptotracker.core.domain.util.Result

import kotlinx.coroutines.ensureActive
import java.io.IOException
import kotlin.coroutines.coroutineContext

//suspend fun <T> safeCall(
//    execute: () -> Response<T>
//): Result<T, NetworkError> {
//    val response = try {
//        execute()
//    } catch (e: IOException) {
//        return Result.Error(NetworkError.NO_INTERNET)
//    } catch (e: Exception) {
//        coroutineContext.ensureActive()
//        return Result.Error(NetworkError.UNKNOWN)
//    }
//
//    return responseToResult(response)
//}