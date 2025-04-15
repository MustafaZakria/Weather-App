package com.vodafone.home.domain.util

import com.zek.cryptotracker.core.domain.util.Error
import com.zek.remotedatasource.core.presentation.util.UiText


enum class RecentCityError : Error {
    NO_RECENT_CITY;

    override fun asUiText(): UiText {
        return when (this) {
            NO_RECENT_CITY -> UiText.DynamicString("NO_RECENT_CITY")
        }
    }
}