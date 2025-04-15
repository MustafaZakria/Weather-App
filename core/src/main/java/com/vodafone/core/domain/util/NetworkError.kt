package com.zek.cryptotracker.core.domain.util

import com.zek.remotedatasource.core.presentation.util.UiText

enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN;

    override fun asUiText(): UiText {
        return when (this) {
            REQUEST_TIMEOUT -> UiText.DynamicString("REQUEST_TIMEOUT")
            TOO_MANY_REQUESTS -> UiText.DynamicString("TOO_MANY_REQUESTS")
            NO_INTERNET -> UiText.DynamicString("NO_INTERNET")
            SERVER_ERROR -> UiText.DynamicString("SERVER_ERROR")
            SERIALIZATION -> UiText.DynamicString("SERIALIZATION")
            else -> UiText.DynamicString("UNKNOWN")
        }
    }
}