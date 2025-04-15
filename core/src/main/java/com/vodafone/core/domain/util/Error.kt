package com.zek.cryptotracker.core.domain.util

import com.zek.remotedatasource.core.presentation.util.UiText

interface Error {
    fun asUiText(): UiText
}