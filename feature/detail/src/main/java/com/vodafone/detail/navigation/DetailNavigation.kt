package com.vodafone.detail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vodafone.detail.DetailScreen


private const val DETAIL_SCREEN_ROUTE = "detail"

fun NavGraphBuilder.detailScreen() {
    composable(
        route = DETAIL_SCREEN_ROUTE
    ) {
        DetailScreen()
    }
}